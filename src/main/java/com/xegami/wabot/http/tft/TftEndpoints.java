package com.xegami.wabot.http.tft;

/**
 * @apiNote TFT API
 * https://developer.riotgames.com
 */
public class TftEndpoints {

    private static final String EUW_URL = "https://euw1";
    private static final String TFT_API_GET_LEAGUE_ENTRIES_BY_SUMMONER_ID = ".api.riotgames.com/lol/league/v4/entries/by-summoner/";
    private static final String TFT_API_GET_SUMMONER_BY_NAME_URL = ".api.riotgames.com/lol/summoner/v4/summoners/by-name/";

    protected String getSummonerSearchUrl(String username, String region) {
        final String head = region == null ? EUW_URL : "https://" + region;

        return head + TFT_API_GET_SUMMONER_BY_NAME_URL + username.replace(" ", "%20").trim();
    }

    protected String getLeagueEntriesUrl(String summonerId, String region) {
        final String head = region == null ? EUW_URL : "https://" + region;

        return head + TFT_API_GET_LEAGUE_ENTRIES_BY_SUMMONER_ID + summonerId;
    }

}

