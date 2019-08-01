package com.xegami.wabot.http.tft;

import com.google.gson.Gson;
import com.xegami.wabot.core.Bot;
import com.xegami.wabot.pojo.dto.tft.LeagueEntryDTO;
import com.xegami.wabot.pojo.dto.tft.SummonerDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TftController extends TftEndpoints {

    private OkHttpClient client;

    public TftController() {
        client = new OkHttpClient();
    }

    private SummonerDTO getSummoner(String username, String region) throws IOException {
        final String url = super.getSummonerSearchUrl(username, region);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Riot-Token", Bot.getInstance().getValues().getApiKeys().getTft().getApiKey())
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, SummonerDTO.class);
    }

    public LeagueEntryDTO getTftSummonerLeagueEntries(String username, String region) throws IOException {
        final String summonerId = getSummoner(username, region).getId();
        final String url = super.getLeagueEntriesUrl(summonerId, region);

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

    public Response getResponse() throws IOException {
        final String url = super.getSummonerSearchUrl("tryndamere", "na1");

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Riot-Token", Bot.getInstance().getValues().getApiKeys().getTft().getApiKey())
                .build();

        return client.newCall(request).execute();
    }

}