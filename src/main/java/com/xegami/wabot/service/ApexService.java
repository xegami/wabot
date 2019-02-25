package com.xegami.wabot.service;

import com.xegami.wabot.core.Bot;
import com.xegami.wabot.core.Constants;
import com.xegami.wabot.http.apex.ApexController;
import com.xegami.wabot.persistance.ApexCrud;
import com.xegami.wabot.pojo.domain.apex.ApexPlayer;
import com.xegami.wabot.message.ApexMessages;
import com.xegami.wabot.util.Utils;
import org.joda.time.LocalTime;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class ApexService implements ServiceInterface {

    private ApexCrud apexCrud;
    private ApexController apexController;

    public ApexService() {
        apexCrud = new ApexCrud();
        apexController = new ApexController();
    }

    @Override
    public String commandAction(String commandLine) {
        String message = null;

        try {
            if (commandLine.startsWith("/")) {
                String command = commandLine.split(" ")[0];

                switch (command) {
                    case "/stats":
                        message = cmdStats(commandLine);
                        break;

                    case "/today":
                        message = cmdToday(commandLine);
                        break;

                    case "/ranking":
                        message = cmdRanking();
                        break;

                    case "/info":
                        message = cmdInfo();
                        break;

                    default:
                        message = "_Ese comando no existe._";
                }
            }

        } catch (IllegalStateException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = "_Error, ¿has puesto bien el usuario?_";
        }

        return message;
    }

    @Override
    public void eventAction() {
        try {
            List<ApexPlayer> apexPlayers = apexCrud.findAll();

            if (apexPlayers.size() == 0) {
                throw new IllegalStateException("No hay jugadores que trackear.");
            }

            for (ApexPlayer a : apexPlayers) {
                ApexPlayer apexPlayer = apexPlayerDataAction(a.getUsername(), a.getPlatform());
                int kills = apexPlayer.getKills() - a.getKills();

                System.out.println(LocalTime.now() + " Tracking ==> " + a.getUsername() + " (" + a.getPlatform() + ") ");

                if (kills >= 7) {
                    System.out.println(LocalTime.now() + " killer!" + " (" + kills + ") ");
                    Bot.getInstance().sendMessage(ApexMessages.killer(apexPlayer.getUsernameHandle(), kills));
                }

                if (apexPlayer.getStartingKills() == null || isResetTime()) {
                    apexPlayer.setStartingKills(apexPlayer.getKills());
                }

                apexCrud.update(apexPlayer);

                Utils.sleep(Constants.EVENT_TRACKER_SLEEP_TIME);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private ApexPlayer apexPlayerDataAction(String username, String platform) throws IOException {
        return apexController.getApexPlayerData(username, platform);
    }

    private String parseUsername(String commandLine) {
        String[] splittedCommandLine = commandLine.split(" ");

        return splittedCommandLine[1];
    }

    private String parsePlatform(String commandLine) {
        String platform;
        String[] splittedCommandLine = commandLine.split(" ", 3);

        if (splittedCommandLine.length < 3) {
            ApexPlayer apexPlayerDb = apexCrud.findByUsername(parseUsername(commandLine));
            if (apexPlayerDb != null) {
                platform = apexPlayerDb.getPlatform();
                return platform;

            } else {
                throw new IllegalStateException("_Plataforma no especificada (pc, ps4 o xbox)._");
            }
        }

        switch (splittedCommandLine[2]) {
            case "pc":
            case "origin":
                platform = "5";
                break;
            case "psn":
            case "ps4":
                platform = "2";
                break;
            case "xbox":
            case "xb1":
                platform = "1";
                break;
            default:
                throw new IllegalStateException("_Esa plataforma no existe (debe ser pc, ps4 o xbox)._");
        }

        return platform;
    }

    private boolean isResetTime() {
        int hour = LocalTime.now().getHourOfDay();
        int minute = LocalTime.now().getMinuteOfHour();

        return hour == 9 && minute < 5;
    }

    private String cmdStats(String commandLine) throws Exception {
        String username = parseUsername(commandLine);
        String platform = parsePlatform(commandLine);
        ApexPlayer apexPlayer = apexPlayerDataAction(username, platform);

        return ApexMessages.stats(apexPlayer);
    }

    private String cmdToday(String commandLine) throws Exception {
        String username = parseUsername(commandLine);
        ApexPlayer apexPlayerDb = apexCrud.findByUsername(username);

        if (apexPlayerDb != null) {
            ApexPlayer apexPlayer = apexPlayerDataAction(username, apexPlayerDb.getPlatform());
            int kills = apexPlayer.getKills() - apexPlayerDb.getStartingKills();
            return ApexMessages.today(apexPlayer.getUsernameHandle(), kills);

        } else {
            throw new IllegalStateException("_Este jugador no se está trackeando._");
        }

    }

    private String cmdInfo() {
        return ApexMessages.info();
    }

    private String cmdRanking() {
        List<ApexPlayer> apexPlayers = apexCrud.findAll();

        apexPlayers.sort(new Comparator<ApexPlayer>() {
            @Override
            public int compare(ApexPlayer o1, ApexPlayer o2) {
                if (o1.getKills() > o2.getKills()) {
                    return -1;
                }
                if (o1.getKills() < o2.getKills()) {
                    return 1;
                }
                return 0;
            }
        });

        return ApexMessages.ranking(apexPlayers);
    }

}
