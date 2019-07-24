package com.xegami.wabot.core;

import com.xegami.wabot.Main;

public final class Constants {

    private static final String CHAT_NAME_PROD = "TFT GODS";
    private static final String CHAT_NAME_TEST = "wabot debug";
    public static final String CHAT_NAME = Main.DEBUG ? CHAT_NAME_TEST : CHAT_NAME_PROD;

    private static final String BASE_PATH = "C:\\wabot_ammo\\";

    public static final String CHROMEDRIVER_PATH = BASE_PATH + "chromedriver\\chromedriver.exe";

    private static final String TFT_PLAYERS_DB_PATH_PROD = BASE_PATH + "nitrite\\tft_players.db";
    private static final String TFT_PLAYERS_DB_PATH_TEST = BASE_PATH + "nitrite\\tft_players_test.db";
    public static final String TFT_PLAYERS_DB_PATH = Main.DEBUG ? TFT_PLAYERS_DB_PATH_TEST : TFT_PLAYERS_DB_PATH_PROD;

    public static final String WABOT_VALUES_PATH = BASE_PATH + "json\\wabot_values.json";

    public static final int EVENT_TRACKER_SLEEP_TIME = 2000;
    public static final int COMMAND_TRACKER_SLEEP_TIME = 500;

    private Constants() {

    }

}
