package com.xegami.wabot.service;

import com.google.gson.Gson;
import com.xegami.wabot.core.Bot;
import com.xegami.wabot.core.Constants;
import com.xegami.wabot.http.apex.ApexController;
import com.xegami.wabot.message.ApexMessages;
import com.xegami.wabot.message.CommonMessages;
import com.xegami.wabot.persistance.ApexCrud;
import com.xegami.wabot.pojo.domain.apex.ApexPlayer;
import com.xegami.wabot.pojo.domain.apex.ApexPlayerMain;
import com.xegami.wabot.pojo.domain.apex.Mozambiques;
import com.xegami.wabot.util.ApexComparators;
import com.xegami.wabot.util.Utils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalTime;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TftService {

    private ApexCrud apexCrud;
    private ApexController apexController;
    private DateTime blockTimeStamp;
    private int resetDayStamp;

    public TftService() {
        apexCrud = new ApexCrud();
        apexController = new ApexController();
    }

    // TODO: command parser class
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

                    case "/mains":
                        message = cmdMains();
                        break;

                    case "/info":
                        message = cmdInfo();
                        break;

                    case "/all":
                        message = cmdAll();
                        break;

                    case "/about":
                        message = cmdAbout();
                        break;

                    default:
                        message = "_Ese comando no existe._";
                }
            }

        } catch (IllegalStateException e) {
            message = e.getMessage();
        } catch (Exception e) {
            message = "_Error, ¿has puesto bien el usuario?_";
            e.printStackTrace();
        }

        return message;
    }

    public void eventAction() {
        int currentDay = DateTime.now().getDayOfYear();
        List<ApexPlayer> apexPlayers = apexCrud.findAll();

        if (apexPlayers.size() == 0) {
            throw new IllegalStateException("No hay jugadores que trackear.");
        }

        if (isResetTime() && currentDay != resetDayStamp) { // 09:00 am
            apexPlayers.sort(ApexComparators.byTodayKillsDescendant());
            Bot.getInstance().sendMessage(ApexMessages.reset(apexPlayers));

            for (ApexPlayer a : apexPlayers) {
                a.setStartingKills(-1); // -1 stands for resetted
                apexCrud.update(a);
            }

            resetDayStamp = currentDay;

            return;
        }

        for (ApexPlayer a : apexPlayers) {
            System.out.println(LocalTime.now() + " Tracking ==> " + a.getUsername() + " (" + a.getPlatform() + ") ");

            try {
                ApexPlayer result = apexPlayerDataAction(a.getUsername(), a.getPlatform());

                ApexPlayer newApexPlayer = new ApexPlayer();
                newApexPlayer.setKills(result.getKills());
                newApexPlayer.setLevel(result.getLevel());
                newApexPlayer.setPlatform(result.getPlatform());
                newApexPlayer.setSource(result.getSource());
                newApexPlayer.setUsername(result.getUsername());
                newApexPlayer.setUsernameHandle(result.getUsernameHandle());
                newApexPlayer.setLegends(result.getLegends());

                if (a.getStartingKills() == null || a.getStartingKills() == -1) {
                    newApexPlayer.setStartingKills(result.getKills());
                } else {
                    newApexPlayer.setStartingKills(a.getStartingKills());
                }

                int kills = result.getKills() - a.getKills();

                if (kills >= 7) {
                    System.out.println(LocalTime.now() + " killer!" + " (" + kills + ") ");
                    Bot.getInstance().sendMessage(ApexMessages.killer(result.getUsernameHandle(), kills));
                }

                apexCrud.update(newApexPlayer);

                Utils.sleep(Constants.EVENT_TRACKER_SLEEP_TIME);

            } catch (Exception e) {
                e.printStackTrace();
            }

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

        return hour == 9 && minute == 0;
    }

    private String cmdStats(String commandLine) throws Exception {
        String username = parseUsername(commandLine);
        ApexPlayer apexPlayer = apexPlayerDataAction(username, platform);

        return ApexMessages.stats(apexPlayer);
    }

    private String cmdToday(String commandLine) throws Exception {
        if (commandLine.split(" ").length == 1) {
            List<ApexPlayer> apexPlayers = apexCrud.findAll();
            apexPlayers.sort(ApexComparators.byTodayKillsDescendant());

            return ApexMessages.todayRanking(apexPlayers);
        }

        String username = parseUsername(commandLine);
        ApexPlayer apexPlayerDb = apexCrud.findByUsername(username);

        if (apexPlayerDb != null) {
            ApexPlayer apexPlayer = apexPlayerDataAction(username, apexPlayerDb.getPlatform());

            if (apexPlayerDb.getStartingKills() == null || apexPlayerDb.getStartingKills() == -1) {
                throw new IllegalStateException("_Todavía no tiene datos, ejecuta el comando más tarde._");
            }

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

        apexPlayers.sort(ApexComparators.byKillsDescendant());

        return ApexMessages.ranking(apexPlayers);
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

        try {
            Mozambiques mozambiques = new Gson().fromJson(new FileReader(Constants.MOZAMBIQUES_DATA_PATH), Mozambiques.class);
            return CommonMessages.all(mozambiques.getContacts());

        } catch (FileNotFoundException e) {
            throw new IllegalStateException("_Archivo de datos no encontrado._");
        }
    }

    private String cmdAbout() {
        return CommonMessages.about();
    }

    private String cmdMains() {
        List<ApexPlayer> apexPlayers = apexCrud.findAll();
        List<ApexPlayerMain> mains = new ArrayList<>();
        String username = "";
        int topKills;

        // 1. bloodhound
        topKills = 0;
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getBloodhoundKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Bloodhound", topKills));

        // 2. gibraltar
        topKills = 0;
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getGibraltarKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Gibraltar", topKills));

        // 3. lifeline
        topKills = 0;
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getLifelineKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Lifeline", topKills));

        // 4. pathfinder
        topKills = 0;
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getPathfinderKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Pathfinder", topKills));

        // 5. octane
        topKills = 0;
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getOctaneKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Octane", topKills));

        // 6. wraith
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getWraithKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Wraith", topKills));

        // 7. bangalore
        topKills = 0;
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getBangaloreKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Bangalore", topKills));

        // 8. caustic
        topKills = 0;
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getCausticKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Caustic", topKills));

        // 9. mirage
        topKills = 0;
        for (ApexPlayer a : apexPlayers) {
            int kills = a.getLegends().getMirageKills();
            if (kills > topKills) {
                topKills = kills;
                username = a.getUsernameHandle();
            }
        }
        mains.add(new ApexPlayerMain(username, "Mirage", topKills));

        return ApexMessages.mains(mains);
    }

}
