package com.xegami.wabot.service;

import com.xegami.wabot.http.fortnite.FortniteController;
import com.xegami.wabot.persistance.FortniteCrud;
import com.xegami.wabot.pojo.domain.fortnite.FortnitePlayer;
import com.xegami.wabot.pojo.dto.fortnite.UserIdDto;
import com.xegami.wabot.pojo.dto.fortnite.UserStatsDto;
import com.xegami.wabot.message.FortniteMessages;
import org.joda.time.LocalTime;

import java.io.IOException;

public class FortniteService {

    private FortniteCrud crud;
    private FortniteController fortniteController;
    private boolean resetDone = false;

    public FortniteService() {
        crud = new FortniteCrud();
        fortniteController = new FortniteController();
    }

    public String commandAction(String commandLine) {
        String message = null;

        try {
            if (commandLine.startsWith("/")) {
                UserStatsDto userStats;
                String command = commandLine.split(" ")[0];

                switch (command) {
                    case "/stats":
                        userStats = userStatsAction(getUsernameEncodedFromCommandLine(commandLine));
                        message = FortniteMessages.stats(userStats);
                        break;

                    case "/today":
                        userStats = userStatsAction(getUsernameEncodedFromCommandLine(commandLine));
                        FortnitePlayer f = crud.findByUsername(userStats.getUsername());

                        if (f.getToday() != null) {
                            message = FortniteMessages.today(userStats, f.getToday());
                        } else {
                            message = "Sin datos todavía.";
                        }
                        break;

                    default:
                        message = "Ese comando no existe gilipollas xdd.";
                }
            }
        } catch (Exception e) {
            message = "Error.";
        }

        return message;
    }

    public void eventAction() {
        /*
        String message = null;

        try {
            int wins, kills, matches;
            List<FortnitePlayer> fortnuteros = crud.findAll();

            for (FortnitePlayer f : fortnuteros) {
                System.out.println(LocalTime.now() + " Tracking ==> " + f.getUsername() + " (" + f.getPlatform() + ") ");

                UserStatsDto userStats = userStatsAction(
                        getUsernameEncoded(
                                f.getUsername()));

                wins = userStats.getTotals().getWinsInt() - f.getWinsInt();
                kills = userStats.getTotals().getKillsInt() - f.getKillsInt();
                matches = userStats.getTotals().getMatchesPlayedInt() - f.getMatchesplayedInt();

                if (wins == 1) {
                    System.out.println(LocalTime.now() + " winner!");
                    message = fortniteMessages.win(userStats, kills);
                } else if (kills >= 7) {
                    System.out.println(LocalTime.now() + " killer!");
                    message = fortniteMessages.killer(userStats, kills);
                }

                if (!resetDone && isResetTime()) {
                    System.out.println("Resetting today stats...");
                    crud.resetToday();
                    resetDone = true;
                } else if (isResetNeeded()) {
                    resetDone = false;
                }

                crud.update(userStats, wins, kills, matches);

                // 1 second sleep before each request
                Thread.sleep(Constants.EVENT_TRACKER_SLEEP_TIME);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
        */
    }

    private UserStatsDto userStatsAction(String usernameEncoded) throws IOException {
        UserIdDto userId = fortniteController.getUserIdCall(usernameEncoded);

        String platform = findPlatformPreference(userId.getUsername());

        // if not in database uses main platform (pc default)
        UserStatsDto userStats = fortniteController.getUserStatsCall(userId.getUid(), platform != null ? platform : userId.getPlatforms()[0]);

        if (userStats.getTotals().getKillsInt() == 0) {
            // backup api
            userStats = fortniteController.getUserStatsBackupCall(usernameEncoded, platform);
        }

        return userStats;
    }

    private boolean isResetTime() {
        int hour = LocalTime.now().getHourOfDay();
        int minute = LocalTime.now().getMinuteOfHour();

        return hour == 9 && minute == 0;
    }

    private boolean isResetNeeded() {
        int hour = LocalTime.now().getHourOfDay();
        int minute = LocalTime.now().getMinuteOfHour();

        return hour == 8 && minute >= 55;
    }

    private String getUsernameEncoded(String username) {
        return username.replace(" ", "%20");
    }

    private String getUsernameEncodedFromCommandLine(String commandLine) {
        String username = commandLine.split(" ", 2)[1];

        return username.replace(" ", "%20");
    }

    private String findPlatformPreference(String username) {
        FortnitePlayer f = crud.findByUsername(username);

        if (f != null) {
            return f.getPlatform();
        }

        return null;
    }
}
