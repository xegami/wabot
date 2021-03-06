package com.xegami.wabot.service;

import com.xegami.wabot.core.Bot;
import com.xegami.wabot.core.Constants;
import com.xegami.wabot.http.tft.TftController;
import com.xegami.wabot.message.CommonMessages;
import com.xegami.wabot.message.TftMessages;
import com.xegami.wabot.persistance.TftRepository;
import com.xegami.wabot.pojo.domain.tft.TftPlayer;
import com.xegami.wabot.pojo.dto.tft.LeagueEntryDTO;
import com.xegami.wabot.pojo.values.WabotValues;
import com.xegami.wabot.util.TftComparators;
import com.xegami.wabot.util.Utils;
import com.xegami.wabot.util.enums.Ranks;
import com.xegami.wabot.util.enums.Tiers;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.openqa.selenium.Keys;

import java.io.IOException;
import java.util.List;

public class TftService {

    private TftRepository tftRepository;
    private TftController tftController;
    private DateTime blockTimeStamp;
    private int eventActionErrorsCount = 0;

    public TftService() {
        tftRepository = new TftRepository();
        tftController = new TftController();
    }

    public String commandAction(String commandLine) {
        String message = null;

        try {
            if (commandLine.startsWith("!")) {
                String command = commandLine.split(" ")[0];

                switch (command) {
                    case "!stats":
                        message = cmdStats(commandLine);
                        break;

                    case "!ranking":
                        message = cmdRanking();
                        break;

                    case "!help":
                        message = cmdHelp();
                        break;

                    case "!all":
                        message = cmdAll();
                        break;

                    case "!source":
                        message = cmdSource();
                        break;

                    case "!reload":
                        message = cmdReload();
                        break;

                    case "!response":
                        message = cmdResponse();
                        break;

                    case "!regions":
                        message = cmdRegions();
                        break;

                    default:
                        message = "_Ese comando no existe._";
                }
            }

        } catch (IllegalStateException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = "_Nope._";
            e.printStackTrace();
        }

        return message;
    }

    public void eventAction() {
        List<TftPlayer> tftPlayers = tftRepository.findAll();

        if (tftPlayers.size() == 0) {
            throw new IllegalStateException("No hay jugadores que trackear.");
        }

        for (TftPlayer tftPlayerDDBB : tftPlayers) {
            if (eventActionErrorsCount >= 50) {
                eventActionErrorsCount = 0;
                Bot.getInstance().sendMessage("_⚠ Se han registrado más de 50 llamadas erróneas a la API_ @Xegami" + Keys.ENTER);
            }

            System.out.println(LocalTime.now() + " Tracking ==> " + tftPlayerDDBB.getSummonerName());

            try {
                TftPlayer tftPlayerNew = tftPlayerDataAction(tftPlayerDDBB.getSummonerName(), null);

                int code = getLeagueStatusCode(tftPlayerNew, tftPlayerDDBB);

                if (code == 1) {
                    System.out.println("TIER UP! " + tftPlayerNew.getSummonerName());
                    Bot.getInstance().sendMessage(TftMessages.tierUp(tftPlayerNew.getSummonerName(), tftPlayerNew.getTier(), tftPlayerNew.getRank()));
                } else if (code == -1) {
                    System.out.println("TIER DROP! " + tftPlayerNew.getSummonerName());
                    Bot.getInstance().sendMessage(TftMessages.tierDrop(tftPlayerNew.getSummonerName(), tftPlayerNew.getTier(), tftPlayerNew.getRank()));
                }

                tftRepository.update(tftPlayerNew);

            } catch (Exception e) {
                eventActionErrorsCount++;
                e.printStackTrace();
            }

            Utils.sleep(Constants.EVENT_TRACKER_SLEEP_TIME);
        }
    }

    private int getLeagueStatusCode(TftPlayer tftPlayerNew, TftPlayer tftPlayerDDBB) {
        if (Tiers.valueOf(tftPlayerNew.getTier()).ordinal() > Tiers.valueOf(tftPlayerDDBB.getTier()).ordinal()) {
            return 1;
        }

        if (Tiers.valueOf(tftPlayerNew.getTier()).ordinal() < Tiers.valueOf(tftPlayerDDBB.getTier()).ordinal()) {
            return -1;
        }

        if (Ranks.valueOf(tftPlayerNew.getRank()).ordinal() > Ranks.valueOf(tftPlayerDDBB.getRank()).ordinal()) {
            return 1;
        }

        if (Ranks.valueOf(tftPlayerNew.getRank()).ordinal() < Ranks.valueOf(tftPlayerDDBB.getRank()).ordinal()) {
            return -1;
        }

        return 0;
    }

    private TftPlayer tftPlayerDataAction(String username, String region) throws IOException {
        LeagueEntryDTO leagueEntryDTO = tftController.getTftSummonerLeagueEntries(username, region);

        return new TftPlayer(
                leagueEntryDTO.getQueueType(), leagueEntryDTO.getSummonerName(), leagueEntryDTO.isHotStreak(), leagueEntryDTO.getWins(), leagueEntryDTO.isVeteran(), leagueEntryDTO.getLosses(), leagueEntryDTO.getRank(), leagueEntryDTO.getTier(), leagueEntryDTO.isInactive(), leagueEntryDTO.isFreshBlood(), leagueEntryDTO.getLeagueId(), leagueEntryDTO.getSummonerId(), leagueEntryDTO.getLeaguePoints()
        );
    }

    private String cmdStats(String commandLine) throws Exception {
        String region = null;
        String username = commandLine.split(" ", 2)[1];

        if (username.contains("-")) {
            String[] sarray = username.split("-");
            username = sarray[0];
            region = sarray[1];
        }

        TftPlayer tftPlayer = tftPlayerDataAction(username, region);

        return TftMessages.stats(tftPlayer);
    }

    private String cmdHelp() {
        return TftMessages.help();
    }

    private String cmdRanking() {
        List<TftPlayer> tftPlayers = tftRepository.findAll();

        tftPlayers.sort(TftComparators.byTierRankWinrate());

        return TftMessages.ranking(tftPlayers);
    }

    private void checkForBlock(int blockTime) {
        if (DateTime.now().getHourOfDay() >= 0 && DateTime.now().getHourOfDay() < 9) {
            throw new IllegalStateException("_No se puede usar este comando por la noche (00:00-08:59)._");

        } else if (blockTimeStamp != null) {
            Interval interval = new Interval(blockTimeStamp, DateTime.now());
            int hours = interval.toPeriod().getHours();
            int minutes = interval.toPeriod().getMinutes();

            if (hours == 0 && minutes < blockTime) {
                int minutesLeft = blockTime - minutes;
                throw new IllegalStateException("_Comando bloqueado por " + minutesLeft + (minutesLeft == 1 ? " minuto._" : " minutos._"));
            }

        }

        blockTimeStamp = DateTime.now();
    }

    private String cmdAll() {
        checkForBlock(30);

        return CommonMessages.all(Bot.getInstance().getValues().getWhatsAppContacts().getContactNames());
    }

    private String cmdSource() {
        return CommonMessages.source();
    }

    private String cmdReload() {
        Bot.getInstance().loadOrReloadValues();
        WabotValues wabotValues = Bot.getInstance().getValues();

        tftRepository.clean();

        for (String summonerName : wabotValues.getWhatsAppContacts().getLolUsernames()) {
            try {
                tftRepository.update(tftPlayerDataAction(summonerName, null));
            } catch (Exception e) {
                // fuck it
            }
        }

        return "_Hecho._";
    }

    private String cmdResponse() {
        try {
            return TftMessages.response(tftController.getResponse());
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("_Imposible obtener respuesta._");
        }
    }

    private String cmdRegions() {
        return TftMessages.regions();
    }

}


