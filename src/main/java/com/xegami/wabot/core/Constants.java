package com.xegami.wabot.core;

import com.xegami.wabot.Main;

public final class Constants {

    public static final String CHAT_NAME_PROD = "Los Mozambiques";
    public static final String CHAT_NAME_TEST = "wabot debug";
    public static final String CHAT_NAME = Main.DEBUG ? CHAT_NAME_TEST : CHAT_NAME_PROD;

    private static final String BASE_PATH = "C:\\wabot_ammo\\";

    public static final String APEX_PLAYERS_DB_PATH_PROD = BASE_PATH + "nitrite\\apex_players.db";
    public static final String APEX_PLAYERS_DB_PATH_TEST = BASE_PATH + "nitrite\\apex_players_test.db";
    public static final String APEX_PLAYERS_DB_PATH = Main.DEBUG ? APEX_PLAYERS_DB_PATH_TEST : APEX_PLAYERS_DB_PATH_PROD;

    public static final String CHROMEDRIVER_PATH = BASE_PATH + "chromedriver\\chromedriver.exe";

    public static final String FORTNITE_PLAYERS_DB_PATH = BASE_PATH + "nitrite\\fortnite_players.db";

    public static final String MOZAMBIQUES_DATA_PATH = BASE_PATH + "json\\mozambiques.json";

    public static final int EVENT_TRACKER_SLEEP_TIME = 2000;
    public static final int COMMAND_TRACKER_SLEEP_TIME = 500;

    private Constants() {

    }

}
