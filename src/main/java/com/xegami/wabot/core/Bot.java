package com.xegami.wabot.core;

import com.xegami.wabot.http.Controller;
import com.xegami.wabot.persistance.FortnuteroCrud;
import com.xegami.wabot.pojo.fortnite.UserId;
import com.xegami.wabot.pojo.fortnite.UserStats;
import com.xegami.wabot.pojo.nitrite.Fortnutero;
import com.xegami.wabot.utils.AppConstants;
import org.joda.time.LocalTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {

    private WebDriver browser;
    private MessageBuilder messageBuilder;
    private FortnuteroCrud crud;
    private Controller controller;

    public Bot() {
        System.setProperty("webdriver.chrome.driver", AppConstants.CHROMEDRIVER_PATH);
        browser = new ChromeDriver();
        browser.get("https://web.whatsapp.com");
        messageBuilder = new MessageBuilder();
        crud = new FortnuteroCrud();
        controller = new Controller();
    }

    private void joinChatGroup() {
        WebElement chats = ((ChromeDriver) browser).findElementById("pane-side");

        chats.findElement(By.xpath("//span[contains(@title, 'Fortnut')]")).click();
    }

    private void sendMessage(String message, boolean formatting) {
        WebElement input = browser.findElement(By.xpath("//div[contains(@spellcheck, 'true')]"));
        input.click();
        if (formatting) {
            ((ChromeDriver) browser).getKeyboard().sendKeys("_" + message + "_");
        } else {
            ((ChromeDriver) browser).getKeyboard().sendKeys(message);
        }
        ((ChromeDriver) browser).getKeyboard().pressKey(Keys.ENTER);
    }

    private void commandTracker() {
        List<WebElement> messages = browser.findElements(By.xpath("//span[contains(@class, 'copyable-text')]"));
        String commandLine = messages.get(messages.size() - 1).getText().toLowerCase();

        try {
            if (commandLine.startsWith("/")) {
                String command = commandLine.split(" ")[0];

                switch (command) {
                    case "/stats":
                        UserStats userStats = userStatsAction(getUsernameEncodedFromCommandLine(commandLine));
                        sendMessage(messageBuilder.stats(userStats), false);
                        break;

                    case "/xegami":
                        sendMessage(messageBuilder.xegami(), true);
                        break;

                    case "/pedro":
                        sendMessage(messageBuilder.pedro(), true);
                        break;

                    case "/españa":
                        sendMessage(messageBuilder.spain(), true);
                        break;

                    default:
                        sendMessage("Ese comando no existe gilipollas xdd.", true);
                }
            }
        } catch (Exception e) {
            sendMessage("Error.", true);
        }
    }

    private void eventTracker() {
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
                    sendMessage(messageBuilder.win(userStats, kills), false);

                } else if (kills >= 10) {
                    sendMessage(messageBuilder.killer(userStats, kills), false);
                    System.out.println(LocalTime.now() + " killer!");

                } else if (matches == 1 && kills == 0) {
                    sendMessage(messageBuilder.trash(userStats), false);
                    System.out.println(LocalTime.now() + " trash!");
                }

                crud.update(userStats);

                // 1 second sleep before each request
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getUsernameEncoded(String username) {
        return username.replace(" ", "%20");
    }

    private String getUsernameEncodedFromCommandLine(String commandLine) {
        String username = commandLine.split(" ", 2)[1];

        return username.replace(" ", "%20");
    }

    private UserStats userStatsAction(String usernameEncoded) throws IOException {
        UserId userId = controller.getUserIdCall(usernameEncoded);

        String platform = findPlatformPreference(userId.getUsername());

        // if not in database uses main platform (pc default)
        UserStats userStats = controller.getUserStatsCall(userId.getUid(), platform != null ? platform : userId.getPlatforms()[0]);

        if (userStats.getTotals().getKillsInt() == 0) {
            // backup api
            userStats = controller.getUserStatsBackupCall(usernameEncoded, platform);
        }

        return userStats;
    }

    private String findPlatformPreference(String username) {
        Fortnutero f = crud.findByUsername(username);

        if (f != null) {
            return f.getPlatform();
        }

        return null;
    }

    public void run() {
        boolean notInChatGroup = true;

        while (notInChatGroup) {
            try {
                joinChatGroup();
                notInChatGroup = false;
                System.out.println("Sesión iniciada.");

            } catch (NoSuchElementException e) {
                System.out.println("Esperando inicio de sesión...");

            } finally {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Runnable commandTrackerThread = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        commandTracker();

                    } catch (Exception e) {
                        System.err.println("Error al obtener el comando.");

                    } finally {
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        Runnable eventTrackerThread = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println(LocalTime.now() + " Buscando eventos...");
                        eventTracker();

                    } catch (Exception e) {
                        System.err.println("Error al analizar a los jugadores.");
                        e.printStackTrace();

                    }
                }
            }
        };

        // to background threads
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(commandTrackerThread);
        executor.submit(eventTrackerThread);
    }

}
