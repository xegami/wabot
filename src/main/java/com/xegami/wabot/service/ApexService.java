package com.xegami.wabot.service;

import com.xegami.wabot.core.Bot;
import com.xegami.wabot.core.Constants;
import com.xegami.wabot.http.apex.ApexController;
import com.xegami.wabot.persistance.ApexCrud;
import com.xegami.wabot.pojo.domain.apex.ApexPlayer;
import com.xegami.wabot.util.ApexMessages;
import org.joda.time.LocalTime;

import java.io.IOException;
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
                ApexPlayer apexPlayer;
                String command = commandLine.split(" ")[0];
                String username = parseUsername(commandLine);
                String platform = parsePlatform(commandLine);

                switch (command) {
                    case "/stats":
                        apexPlayer = apexPlayerDataAction(username, platform);
                        message = ApexMessages.stats(apexPlayer);
                        break;

                    case "/info":
                        message = ApexMessages.info();
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

            for (ApexPlayer a : apexPlayers) {
                ApexPlayer apexPlayer = apexPlayerDataAction(a.getUsername(), a.getPlatform());
                int kills = apexPlayer.getKills() - a.getKills();

                System.out.println(LocalTime.now() + " Tracking ==> " + a.getUsername() + " (" + a.getPlatform() + ") ");

                if (kills >= 7) {
                    System.out.println(LocalTime.now() + " killer!" + " (" + kills + ") ");
                    Bot.getInstance().sendMessage(ApexMessages.killer(apexPlayer.getUsername(), kills));
                }

                apexCrud.update(apexPlayer);

                Thread.sleep(Constants.EVENT_TRACKER_SLEEP_TIME);
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

        if (splittedCommandLine.length < 3)
            throw new IllegalStateException("_Plataforma no especificada (pc, ps4 o xbox)._");

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
}
