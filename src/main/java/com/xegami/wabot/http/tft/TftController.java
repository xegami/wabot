package com.xegami.wabot.http.tft;

import com.google.gson.Gson;
import com.xegami.wabot.core.Bot;
import com.xegami.wabot.pojo.dto.tft.LeagueEntryDTO;
import com.xegami.wabot.pojo.dto.tft.SummonerDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TftController {

    private OkHttpClient client;

    public TftController() {
        client = new OkHttpClient();
    }

    private SummonerDTO getSummoner(String username) throws IOException {
        final String url = TftEndpoints.TFT_API_GET_SUMMONER_BY_NAME_URL + username.replace(" ", "%20");

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Riot-Token", Bot.getInstance().getValues().getApiKeys().getTft().getApiKey())
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, SummonerDTO.class);
    }

    public LeagueEntryDTO getTftSummonerLeagueEntries(String username) throws IOException {
        String summonerId = getSummoner(username).getId();

        final String url = TftEndpoints.TFT_API_GET_LEAGUE_ENTRIES_BY_SUMMONER_ID + summonerId;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Riot-Token", Bot.getInstance().getValues().getApiKeys().getTft().getApiKey())
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        LeagueEntryDTO[] leagueEntryDTOs = new Gson().fromJson(jsonString, LeagueEntryDTO[].class);

        for (LeagueEntryDTO entry : leagueEntryDTOs) {
            if (entry.getQueueType().equals("RANKED_TFT")) {
                return entry;
            }
        }

        return null;
    }

}