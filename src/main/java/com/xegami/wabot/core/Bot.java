package com.xegami.wabot.core;

import com.xegami.wabot.service.FortniteService;
import com.xegami.wabot.utils.AppConstants;
import org.joda.time.LocalTime;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bot {

    private WebDriver browser;
    private FortniteService fortniteService;

    public Bot() {
        System.setProperty("webdriver.chrome.driver", AppConstants.CHROMEDRIVER_PATH);
        browser = new ChromeDriver();
        browser.get("https://web.whatsapp.com");
        fortniteService = new FortniteService();
    }

    private void joinChatGroup() {
        WebElement chats = ((ChromeDriver) browser).findElementById("pane-side");

        chats.findElement(By.xpath("//span[contains(@title, 'Fortnut')]")).click();

        sendMessage("He vuelto, bitches.");
    }

    private void sendMessage(String message) {
        WebElement input = browser.findElement(By.xpath("//div[contains(@spellcheck, 'true')]"));
        input.click();

        ChromeDriver driver = (ChromeDriver) browser;
        driver.getKeyboard().sendKeys(message);
        driver.getKeyboard().pressKey(Keys.ENTER);
    }

    private void commandTracker() {
        List<WebElement> messages = browser.findElements(By.xpath("//span[contains(@class, 'copyable-text')]"));
        String commandLine = messages.get(messages.size() - 1).getText().toLowerCase();

        String newMessage = fortniteService.commandAction(commandLine);
        //String newMessage = apexService.commandAction(commandLine);

        if (newMessage != null) sendMessage(newMessage);
    }

    private void eventTracker() {
        String newMessage = fortniteService.eventAction();
        //String newMessage = apexService.eventTracker();

        if (newMessage != null) sendMessage(newMessage);
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
                            Thread.sleep(AppConstants.COMMANDS_SLEEP_TIME);
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
