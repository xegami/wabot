package com.xegami.wabot.service;

import com.xegami.wabot.core.Bot;
import com.xegami.wabot.message.TwitterMessages;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService implements StatusListener {

    // TODO: load keys from .properties
    private static final long PLAYAPEX_ID = 1048018930785083392L;
    private static final long WABOT_ID = 1098939511910875136L;
    private static final long PLAYAPEXINFO_ID = 1005019747120041984L;
    private static final long TITANFALLBLOG_ID = 1488707239L;

    private TwitterStream stream;

    public TwitterService() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(COMSUMER_SECRET)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);
        stream = new TwitterStreamFactory(cb.build()).getInstance();
        stream.addListener(this);
        stream.filter(new FilterQuery().follow(PLAYAPEXINFO_ID));
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
        if (status.getUser().getId() == PLAYAPEXINFO_ID && !status.getText().startsWith("RT")) {
            Bot.getInstance().sendMessage(TwitterMessages.tuit(status));
        }
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
    }

}
