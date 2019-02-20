package com.xegami.wabot.service;

import com.xegami.wabot.core.MessageBuilder;
import com.xegami.wabot.http.apex.ApexController;
import com.xegami.wabot.pojo.apex.ApexPlayerData;
import com.xegami.wabot.pojo.apex.MyApexPlayerData;
import com.xegami.wabot.pojo.fortnite.UserId;
import com.xegami.wabot.pojo.fortnite.UserStats;
import com.xegami.wabot.pojo.nitrite.Fortnutero;

import java.io.IOException;

public class ApexService implements ServiceInterface {

    private MessageBuilder messageBuilder;
    private ApexController apexController;
    private String username;
    private String platform;

    public ApexService() {
        messageBuilder = new MessageBuilder();
        apexController = new ApexController();
    }

    @Override
    public String commandAction(String commandLine) {
        String message = null;

        try {
            if (commandLine.startsWith("/")) {
                MyApexPlayerData myApexPlayerData;
                String command = commandLine.split(" ")[0];

                switch (command) {
                    case "/stats":
                        parseCommandLine(commandLine);
                        myApexPlayerData = apexPlayerDataAction(username, platform);
                        message = messageBuilder.stats(myApexPlayerData);
                        break;

                    default:
                        message = "Ese comando no existe gilipollas xdd.";
                }
            }

        } catch (IllegalStateException e) {
            message = e.toString();
        } catch (Exception e) {
            message = "Error.";
        }

        return message;
    }

    @Override
    public String eventAction() {

        return null;
    }

    private MyApexPlayerData apexPlayerDataAction(String username, String platform) throws IOException {
        return apexController.getApexPlayerData(username, platform);
    }

    private void parseCommandLine(String commandLine) {
        String[] splittedCommandLine = commandLine.split(" ", 3);
        username = splittedCommandLine[1];

        switch (splittedCommandLine[2]) {
            case "pc":
                platform = "5";
                break;
            case "psn":
                platform = "2";
                break;
            case "xbox":
                platform = "1";
                break;
            default:
                throw new IllegalStateException("Plataforma no válida.");
        }
    }

}
