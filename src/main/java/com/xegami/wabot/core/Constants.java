package com.xegami.wabot.core;

public final class Constants {

    public static final String CHAT_NAME = "Los Mozambiques";
    //public static final String CHAT_NAME = "wabot debug";

    private static final String BASE_PATH = "C:\\wabot_ammo\\";

    public static final String APEX_PLAYERS_DB_PATH = BASE_PATH + "nitrite\\apex_players.db";
    //public static final String APEX_PLAYERS_DB_PATH = BASE_PATH + "nitrite\\apex_players_test.db";
    public static final String CHROMEDRIVER_PATH = BASE_PATH + "chromedriver\\chromedriver.exe";
    public static final String FORTNITE_PLAYERS_DB_PATH = BASE_PATH + "nitrite\\fortnite_players.db";
    public static final String MOZAMBIQUES_DATA_PATH = BASE_PATH + "json\\mozambiques.json";

    public static final int EVENT_TRACKER_SLEEP_TIME = 2000;
    public static final int COMMAND_TRACKER_SLEEP_TIME = 500;

    private Constants() {

    }

}
