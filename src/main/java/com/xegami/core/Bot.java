package com.xegami.core;

import com.xegami.http.Controller;
import com.xegami.persistance.FortnuteroCrud;
import com.xegami.pojo.fortnite.UserId;
import com.xegami.pojo.fortnite.UserStats;
import com.xegami.pojo.nitrite.Fortnutero;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {

    private WebDriver browser;
    private MessageBuilder messageBuilder;
    private FortnuteroCrud crud;
    private Controller controller;

    public Bot() {
        System.setProperty("webdriver.chrome.driver", "C:\\Desarrollo\\chromedriver\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("https://web.whatsapp.com");
        messageBuilder = new MessageBuilder();
        crud = new FortnuteroCrud();
        controller = new Controller();
    }

    private void joinChatGroup() {
        WebElement chats = ((ChromeDriver) browser).findElementById("pane-side");

        chats.findElement(By.xpath("//span[contains(@title, 'FortNut OGs')]")).click();
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
                        sendMessage(messageBuilder.stats(getUserStatsFromCommandLine(commandLine)), false);
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

    private void winnerTracker() {
        try {
            List<Fortnutero> fortnuteros = crud.findAll();

            for (Fortnutero f : fortnuteros) {
                UserStats userStats = getUserStats(f.getUsername());

                if (userStats.getTotals().getWinsInt() > f.getWinsInt()) {
                    sendMessage(messageBuilder.win(userStats, f.getKillsInt()), false);
                    crud.updateEntry(userStats);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserStats getUserStats(String username) throws IOException {
        String usernameEncoded = username.replace(" ", "%20");
        UserId userId = controller.getUserIdRequest(usernameEncoded);

        return controller.getUserStatsRequest(userId.getUid(), userId.getPlatforms()[0]);
    }

    private UserStats getUserStatsFromCommandLine(String commandLine) throws IOException {
        String username = commandLine.split(" ", 2)[1];
        String usernameEncoded = username.replace(" ", "%20");

        UserId userId = controller.getUserIdRequest(usernameEncoded);

        return controller.getUserStatsRequest(userId.getUid(), userId.getPlatforms()[0]);
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

        Runnable winnerTrackerThread = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println(getTime() + " Comprobando nuevas wins...");
                        winnerTracker();

                    } catch (Exception e) {
                        System.err.println("Error al comprobar las wins.");
                        e.printStackTrace();

                    } finally {
                        try {
                            Thread.sleep(30000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        // to background threads
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(commandTrackerThread);
        executor.submit(winnerTrackerThread);
    }

    private String getTime() {
        Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
    }
}
