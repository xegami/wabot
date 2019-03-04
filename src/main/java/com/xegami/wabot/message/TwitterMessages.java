package com.xegami.wabot.message;

import twitter4j.Status;

public class TwitterMessages extends BaseMessages {

    /*
    public static String tuit(Status status) {
        String message = "";
        String url = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
        String text = status.getText()
                .replace("\n", n())
                .replaceAll("http.*", "");

        message += "*" + status.getUser().getName() + "* @" + status.getUser().getScreenName() + n()
                + n()
                + text + n()
                + n()
                + url;

        return message;
    }
    */

    public static String tuit(Status status) {
        String url = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();

        return url;
    }
}
