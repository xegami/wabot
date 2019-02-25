package com.xegami.wabot.message;

import org.openqa.selenium.Keys;

import java.util.List;

public class BaseMessages {

    protected static String n() {
        return Keys.chord(Keys.SHIFT, Keys.ENTER);
    }

    public static String all(List<String> contacts) {
        String message = "";

        for (String c : contacts) {
            message += "@" + c + Keys.ENTER;
        }

        return message;
    }
}
