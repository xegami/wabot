package com.xegami.wabot.service;

import com.xegami.wabot.core.Bot;
import com.xegami.wabot.core.MessageBuilder;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService implements StatusListener {

    private static final String CONSUMER_KEY = "i8AJV3qw9rRD3179ZyZyPCO7h";
    private static final String COMSUMER_SECRET = "SGQS9dXG0S8CYcs8XOytJ4t3WAuGl3OZNhdF2iXcZ7WNuhmkFC";
    private static final String ACCESS_TOKEN = "3187348179-t8NMgY6rVgLz0Xv7KXlL4Qe1hAZDFoj7vPcUWuo";
    private static final String ACCESS_TOKEN_SECRET = "btnvLerCLxt3zgHjUx0qq5h5bf2lrEsXL0RP4uBhXPLv4";
    private static final long XEGAMI_ID = 3187348179L;
    private static final long PLAYAPEX_ID = 1048018930785083392L;

    private MessageBuilder messageBuilder;
    private TwitterStream stream;

    public TwitterService() {
        messageBuilder = new MessageBuilder();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(COMSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        stream = new TwitterStreamFactory(cb.build()).getInstance();
        stream.addListener(this);
        stream.filter(new FilterQuery().follow(PLAYAPEX_ID));
    }

    @Override
    public void onException(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice arg) {
    }

    public void onScrubGeo(long userId, long upToStatusId) {
    }

    @Override
    public void onStallWarning(StallWarning warning) {
    }

    @Override
    public void onStatus(Status status) {
        Bot.getInstance().sendMessage(messageBuilder.tuit(status));
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    }

}
