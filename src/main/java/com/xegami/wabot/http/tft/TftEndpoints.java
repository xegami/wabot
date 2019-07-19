package com.xegami.wabot.http.tft;

import com.xegami.wabot.Main;

public final class TftEndpoints {

    /**
     * @apiNote TFT API
     * https://developer.riotgames.com
     */

    private static final String TFT_API_BASE_URL = "https://euw1.api.riotgames.com";
    public static final String TFT_API_GET_SUMMONER_ENTRIES_URL = TFT_API_BASE_URL + "/lol/league/v4/entries/by-summoner/";
    public static final String TFT_API_GET_SUMMONER_ID_URL = TFT_API_BASE_URL +  "/lol/summoner/v4/summoners/by-name/";

    public static final String TFT_API_KEY = "";
    public static final String TFT_API_HEADER_VALUE = "";


    private TftEndpoints() {

    }

}

