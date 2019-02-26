package com.xegami.wabot.message;

import twitter4j.Status;

public class TwitterMessages extends BaseMessages {

    public static String tuit(Status status) {
        String message = "";
        String url = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();

        message += "*" + status.getUser().getName() + "* @" + status.getUser().getScreenName() + n()
                + n()
                + "\"" + status.getText().replace("\n", n()) + "\"" + n()
                + n()
                + url;

        return message;
    }
}
