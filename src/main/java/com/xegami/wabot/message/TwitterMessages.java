package com.xegami.wabot.message;

import com.vdurmont.emoji.EmojiParser;
import twitter4j.Status;

public class TwitterMessages extends BaseMessages {

    public static String tuit(Status status) {
        String message = "";
        String url = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();
        String text = EmojiParser.removeAllEmojis(status.getText()) // no emojis
                .replace("\n", n()) // using selenium new line
                .replaceAll("http.*", ""); // no links

        message += text + n()
                + n()
                + url;

        return message;
    }

}
