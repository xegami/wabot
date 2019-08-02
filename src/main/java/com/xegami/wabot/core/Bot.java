package com.xegami.wabot.core;

import com.google.gson.Gson;
import com.xegami.wabot.pojo.values.WabotValues;
import com.xegami.wabot.service.TftService;
import com.xegami.wabot.service.TwitterService;
import com.xegami.wabot.util.Utils;
import org.joda.time.LocalTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {

    private WebDriver browser;
    private TftService tftService;
    private static Bot instance;
    private static WabotValues wabotValues;

    public Bot() {
        setupDriver();
        loadOrReloadValues();
        instance = this;
    }

    public WabotValues getValues() {
        return wabotValues;
    }

    public void loadOrReloadValues() {
        try {
            wabotValues = new Gson().fromJson(new InputStreamReader(new FileInputStream(Constants.WABOT_VALUES_PATH), StandardCharsets.UTF_8), WabotValues.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void setupDriver() {
        System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_PATH);
        ChromeOptions options = new ChromeOptions().addArguments("--user-data-dir=./user_data");
        browser = new ChromeDriver(options);
        browser.get("https://web.whatsapp.com");
    }

    private void initServices() {
        tftService = new TftService();
        new TwitterService(); // doesn't need instance
    }

    public static Bot getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new IllegalStateException("Bot.class no tiene instancia");
        }
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

    public void sendMessageDelayed(String message, int delay) {
        WebElement input = browser.findElement(By.xpath("//div[contains(@spellcheck, 'true')]"));
        input.click();

        ChromeDriver driver = (ChromeDriver) browser;
        driver.getKeyboard().sendKeys(message);
        Utils.sleep(delay);
        driver.getKeyboard().pressKey(Keys.ENTER);
    }

    private void commandTracker() {
        List<WebElement> messages = browser.findElements(By.xpath("//span[contains(@class, 'copyable-text')]"));
        String commandLine = messages.get(messages.size() - 1).getText().toLowerCase();

        String newMessage = tftService.commandAction(commandLine);

        if (newMessage != null) sendMessage(newMessage);
    }

    private void eventTracker() {
        tftService.eventAction();
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
