package com.xegami.core;

import com.xegami.http.Controller;
import com.xegami.persistance.NitriteCrud;
import com.xegami.pojo.fortnite.UserId;
import com.xegami.pojo.fortnite.UserStats;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Bot {

    private WebDriver browser;
    private Commands commands;
    //private NitriteCrud crud;
    private Controller controller;

    public Bot() {
        System.setProperty("webdriver.chrome.driver", "C:\\Desarrollo\\chromedriver\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("https://web.whatsapp.com");
        commands = new Commands();
        //crud = new NitriteCrud();
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

    private void getCommand() {
        List<WebElement> messages = browser.findElements(By.xpath("//span[contains(@class, 'copyable-text')]"));
        String commandLine = messages.get(messages.size() - 1).getText().toLowerCase();

        try {
            if (commandLine.startsWith("/")) {
                String command = commandLine.split(" ")[0];

                switch (command) {
                    case "/stats":
                        String username = getUsernameEncoded(commandLine);
                        UserId userId = controller.getUserIdRequest(username);
                        UserStats userStats = controller.getUserStatsRequest(userId.getUid(), userId.getPlatforms()[0]);
                        sendMessage(commands.stats(userStats), false);
                        break;

                    case "/xegami":
                        sendMessage(commands.xegami(), true);
                        break;

                    case "/pedro":
                        sendMessage(commands.pedro(), true);
                        break;

                    case "/españa":
                        sendMessage(commands.spain(), true);
                        break;

                    default:
                        sendMessage("Ese comando no existe gilipollas xdd.", true);
                }
            }
        } catch (Exception e) {
            sendMessage("Error.", true);
        }
    }

    /* todo mockup
    private void getWinner() {
        Controller controller = new Controller();

        try {
            List<Player> players = crud.getPlayers();


            FortniteStats fortniteStats = controller.fortniteStatsCall(wins.getEpicNickname(), wins.getPlatform());
            if (wins.getStats().getP2().getTop1().getValueInt() < fortniteStats.getStats().getP2().getTop1().getValueInt()) {
                sendMessage("¡*Xegami* acaba de ganar una win en solitario!", true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */

    private String getUsernameEncoded(String commandLine) {
        String username = commandLine.split(" ", 2)[1];
        String usernameEncoded = username.replace(" ", "%20");

        return usernameEncoded;
    }

    public void run() {
        //crud.createEntries();

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

        while (true) {
            try {
                getCommand();
                //getWinner();

            } catch (Exception e) {

            } finally {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
