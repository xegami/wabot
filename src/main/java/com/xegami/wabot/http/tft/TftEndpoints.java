package com.xegami.wabot.http.tft;

public final class TftEndpoints {

    /**
     * @apiNote TFT API
     * https://developer.riotgames.com
     */

    private static final String TFT_API_BASE_URL = "https://euw1.api.riotgames.com";
    public static final String TFT_API_GET_LEAGUE_ENTRIES_BY_SUMMONER_ID = TFT_API_BASE_URL + "/lol/league/v4/entries/by-summoner/";
    public static final String TFT_API_GET_SUMMONER_BY_NAME_URL = TFT_API_BASE_URL + "/lol/summoner/v4/summoners/by-name/";

    private TftEndpoints() {

    }

}

