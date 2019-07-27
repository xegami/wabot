package com.xegami.wabot.message;

import org.openqa.selenium.Keys;

import java.util.List;

public class CommonMessages extends BaseMessages {

    public static String all(List<String> contacts) {
        String message = "";

        for (String c : contacts) {
            message += "@" + c + Keys.ENTER;
        }

        return message;
    }

    public static String source() {
        return "https://github.com/xegami/wabot";
    }
}
