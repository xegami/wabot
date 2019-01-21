package com.xegami;

import com.google.gson.Gson;
import com.xegami.pojo.bot.Parameters;
import com.xegami.pojo.bot.Wins;
import com.xegami.pojo.trn.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.util.List;

public class Bot {

    private WebDriver browser;
    private Commands commands;

    public Bot() {
        System.setProperty("webdriver.chrome.driver", "C:\\Desarrollo\\chromedriver\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("https://web.whatsapp.com");
        commands = new Commands();
    }

    private void joinChatGroup() {
        WebElement chats = ((ChromeDriver) browser).findElementById("pane-side");

        chats.findElement(By.xpath("//span[contains(@title, 'FortNut OGs')]")).click();
    }

    private void sendMessage(String message, boolean formatting) {
        WebElement input = browser.findElement(By.xpath("//div[contains(@spellcheck, 'true')]"));
        input.click();
        if (formatting) {
            ((ChromeDriver) browser).getKeyboard().sendKeys(Ctts.ITALIC + message + Ctts.ITALIC);
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
                Parameters parameters;

                switch (command) {
                    case "/wins":
                        parameters = buildParameters(commandLine);
                        sendMessage(commands.wins(parameters), true);
                        break;
                    case "/stats":
                        parameters = buildParameters(commandLine);
                        sendMessage(commands.lifetimeStats(parameters), false);
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
                        sendMessage("Ese comando no existe gilipollas xd.", true);
                }
            }
        } catch (Exception e) {
            sendMessage("Error.", true);
        }
    }

    // todo mockup
    private void checkWins() {
        Stats stats = new Stats();
        P2 p2 = new P2(); P10 p10 = new P10(); P9 p9 = new P9();
        Top1 tp2 = new Top1(); Top1 tp10 = new Top1(); Top1 tp9 = new Top1();
        tp2.setValueInt(101); tp10.setValueInt(74); tp9.setValueInt(114);
        p2.setTop1(tp2); p10.setTop1(tp10); p9.setTop1(tp9);
        stats.setP2(p2); stats.setP10(p10); stats.setP2(p2);

        Wins wins = new Wins("Xegami", "pc", stats);
        Controller controller = new Controller();
        try {
            FortniteStats fortniteStats = controller.fortniteStatsCall(wins.getEpicNickname(), wins.getPlatform());
            if (wins.getStats().getP2().getTop1().getValueInt() < fortniteStats.getStats().getP2().getTop1().getValueInt()) {
                sendMessage("¡*Xegami* acaba de ganar una win en solitario!", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Parameters buildParameters(String commandLine) {
        int spaceStart = commandLine.indexOf(" ");
        int spaceEnd = commandLine.lastIndexOf(" ");
        String epicNickname = commandLine.substring(spaceStart + 1, spaceEnd);
        String epicNicknameEncoded = epicNickname.replace(" ", "%20");
        String platform = commandLine.substring(spaceEnd + 1, commandLine.length());

        return new Parameters(epicNicknameEncoded, platform);
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

        while (true) {
            try {
                getCommand();
                //checkWins();

            } catch (Exception e) {

            } finally {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
