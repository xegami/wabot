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

    public static String about() {
        String message = "";

        message += "*wabot* es un bot de WhatsApp (standalone) creado por Xegami." + n()
                + n()
                + "_Apuntando a → Teamfight Tactics - RIOT DEV API._" + n()
                + "_Código fuente → https://github.com/xegami/wabot_";

        return message;
    }
}
