package com.xegami.wabot.service;

import com.xegami.wabot.core.Bot;
import com.xegami.wabot.message.TwitterMessages;
import com.xegami.wabot.pojo.values.ApiKeys;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService implements StatusListener {

    private static final long MORTDOG_ID = 16464000L;

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
        stream.filter(new FilterQuery().follow(MORTDOG_ID));
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
        if (status.getUser().getId() == MORTDOG_ID && !status.getText().startsWith("RT") && !status.isRetweet()) {
            Bot.getInstance().sendMessageDelayed(TwitterMessages.tuit(status), 10000); // 10 secs delay for the thumb
        }
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    }

}
