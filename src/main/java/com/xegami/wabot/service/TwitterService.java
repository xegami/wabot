package com.xegami.wabot.service;

import com.xegami.wabot.core.Bot;
import com.xegami.wabot.message.TwitterMessages;
import com.xegami.wabot.pojo.values.ApiKeys;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService implements StatusListener {

    private static final long LOL_OFFICIAL_ID = 577401044L;

    public TwitterService() {
        ApiKeys apiKeys = Bot.getInstance().getValues().getApiKeys();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(apiKeys.getTwitter().getConsumerKey())
                .setOAuthConsumerSecret(apiKeys.getTwitter().getConsumerSecret())
                .setOAuthAccessToken(apiKeys.getTwitter().getAccessToken())
                .setOAuthAccessTokenSecret(apiKeys.getTwitter().getAccessTokenSecret());

        TwitterStream stream = new TwitterStreamFactory(cb.build()).getInstance();
        stream.addListener(this);
        stream.filter(new FilterQuery().follow(LOL_OFFICIAL_ID));
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

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onStatus(Status status) {
        if (status.getUser().getId() == LOL_OFFICIAL_ID && !status.getText().startsWith("RT") && status.getText().contains("TFT") && status.getText().contains("Patch")) {
            Bot.getInstance().sendMessage(TwitterMessages.tuit(status));
        }
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    }

}
