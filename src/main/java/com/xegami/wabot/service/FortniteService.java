package com.xegami.wabot.service;

import com.xegami.wabot.core.MessageBuilder;
import com.xegami.wabot.http.FortniteController;
import com.xegami.wabot.persistance.FortnuteroCrud;
import com.xegami.wabot.pojo.fortnite.UserId;
import com.xegami.wabot.pojo.fortnite.UserStats;
import com.xegami.wabot.pojo.nitrite.Fortnutero;
import com.xegami.wabot.utils.AppConstants;
import org.joda.time.LocalTime;

import java.io.IOException;
import java.util.List;

public class FortniteService {

    private MessageBuilder messageBuilder;
    private FortnuteroCrud crud;
    private FortniteController fortniteController;
    private boolean resetDone = false;

    public FortniteService() {
        messageBuilder = new MessageBuilder();
        crud = new FortnuteroCrud();
    }

    public String commandAction(String commandLine) {
        String message = null;

        try {
            if (commandLine.startsWith("/")) {
                UserStats userStats;
                String command = commandLine.split(" ")[0];

                switch (command) {
                    case "/stats":
                        userStats = userStatsAction(getUsernameEncodedFromCommandLine(commandLine));
                        message = messageBuilder.stats(userStats);
                        break;

                    case "/today":
                        userStats = userStatsAction(getUsernameEncodedFromCommandLine(commandLine));
                        Fortnutero f = crud.findByUsername(userStats.getUsername());

                        if (f.getToday() != null) {
                            message = messageBuilder.today(userStats, f.getToday());
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

    public String eventTracker() {
        String message = null;

        try {
            int wins, kills, matches;
            List<Fortnutero> fortnuteros = crud.findAll();

            for (Fortnutero f : fortnuteros) {
                System.out.println(LocalTime.now() + " Tracking ==> " + f.getUsername() + " (" + f.getPlatform() + ") ");

                UserStats userStats = userStatsAction(
                        getUsernameEncoded(
                                f.getUsername()));

                wins = userStats.getTotals().getWinsInt() - f.getWinsInt();
                kills = userStats.getTotals().getKillsInt() - f.getKillsInt();
                matches = userStats.getTotals().getMatchesPlayedInt() - f.getMatchesplayedInt();

                if (wins == 1) {
                    System.out.println(LocalTime.now() + " winner!");
                    message = messageBuilder.win(userStats, kills);
                } else if (kills >= 7) {
                    System.out.println(LocalTime.now() + " killer!");
                    message = messageBuilder.killer(userStats, kills);
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
                Thread.sleep(AppConstants.EVENTS_SLEEP_TIME);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    private UserStats userStatsAction(String usernameEncoded) throws IOException {
        UserId userId = fortniteController.getUserIdCall(usernameEncoded);

        String platform = findPlatformPreference(userId.getUsername());

        // if not in database uses main platform (pc default)
        UserStats userStats = fortniteController.getUserStatsCall(userId.getUid(), platform != null ? platform : userId.getPlatforms()[0]);

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
        Fortnutero f = crud.findByUsername(username);

        if (f != null) {
            return f.getPlatform();
        }

        return null;
    }
}
