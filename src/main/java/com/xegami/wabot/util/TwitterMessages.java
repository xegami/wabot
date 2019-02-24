package com.xegami.wabot.util;

import twitter4j.Status;

public class TwitterMessages extends BaseMessages {

    public static String tuit(Status status) {
        String message = "";
        String url = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();

        message += "*@PlayApex hace unos segundos:*" + n()
                + n()
                + "\"" + status.getText() + "\"" + n()
                + n()
                + url;

        return message;
    }
}
