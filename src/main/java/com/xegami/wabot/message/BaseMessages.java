package com.xegami.wabot.message;

import org.openqa.selenium.Keys;

public class BaseMessages {

    protected static String n() {
        return Keys.chord(Keys.SHIFT, Keys.ENTER);
    }

}
