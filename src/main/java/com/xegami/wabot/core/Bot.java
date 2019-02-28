package com.xegami.wabot.core;

import com.xegami.wabot.service.ApexService;
import com.xegami.wabot.service.TwitterService;
import com.xegami.wabot.util.Utils;
import org.joda.time.LocalTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {

    private WebDriver browser;
    private ApexService apexService;
    private static Bot instance;

    public Bot() {
        setupDriver();
        instance = this;
    }

    private void setupDriver() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_PATH);
        ChromeOptions options = new ChromeOptions().addArguments("--user-data-dir=./USER-DATA");
        browser = new ChromeDriver(options);
        browser.get("https://web.whatsapp.com");
    }

    private void initServices() {
        apexService = new ApexService();
        // doesn't need instance
        new TwitterService();
    }

    public static Bot getInstance() {
        if (instance != null) {
            return instance;
        }

        return null;
    }

    private void joinChatGroup() {
        WebElement chats = ((ChromeDriver) browser).findElementById("pane-side");
        chats.findElement(By.xpath("//span[contains(@title, '" + Constants.CHAT_NAME + "')]")).click();
        //sendMessage("_He vuelto, bitches._");
    }

    public void sendMessage(String message) {
        WebElement input = browser.findElement(By.xpath("//div[contains(@spellcheck, 'true')]"));
        input.click();

        ChromeDriver driver = (ChromeDriver) browser;
        driver.getKeyboard().sendKeys(message);
        driver.getKeyboard().pressKey(Keys.ENTER);
    }

    private void commandTracker() {
        List<WebElement> messages = browser.findElements(By.xpath("//span[contains(@class, 'copyable-text')]"));
        String commandLine = messages.get(messages.size() - 1).getText().toLowerCase();

        String newMessage = apexService.commandAction(commandLine);

        if (newMessage != null) sendMessage(newMessage);
    }

    private void eventTracker() {
        apexService.eventAction();
    }

    public void run() {
        boolean notInChatGroup = true;

        while (notInChatGroup) {
            try {
                joinChatGroup();
                initServices();
                notInChatGroup = false;
                System.out.println("Sesión iniciada.");

            } catch (NoSuchElementException e) {
                System.out.println("Esperando inicio de sesión...");

            } finally {
                try {
                    Utils.sleep(5000);
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
                            Utils.sleep(Constants.COMMAND_TRACKER_SLEEP_TIME);
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
                        Utils.sleep(Constants.EVENT_TRACKER_SLEEP_TIME);
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
