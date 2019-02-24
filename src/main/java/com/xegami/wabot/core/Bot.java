package com.xegami.wabot.core;

import com.xegami.wabot.service.ApexService;
import com.xegami.wabot.service.TwitterService;
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
    private TwitterService twitterService;
    //private String chatName = "Los Mozambiques";
    private String chatName = "wabot debug";
    private static Bot bot;

    public Bot() {
        setupDriver();
        bot = this;
    }

    private void setupDriver() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_PATH);
        ChromeOptions options = new ChromeOptions().addArguments("--user-data-dir=./USER-DATA");
        browser = new ChromeDriver(options);
        browser.get("https://web.whatsapp.com");
    }

    private void initServices() {
        apexService = new ApexService();
        twitterService = new TwitterService();
    }

    public static Bot getInstance() {
        if (bot != null) {
            return bot;
        }

        return null;
    }

    private void joinChatGroup() {
        WebElement chats = ((ChromeDriver) browser).findElementById("pane-side");
        chats.findElement(By.xpath("//span[contains(@title, '" + chatName + "')]")).click();
        //sendMessage("He vuelto, bitches.");
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
                            Thread.sleep(Constants.COMMAND_TRACKER_SLEEP_TIME);
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
