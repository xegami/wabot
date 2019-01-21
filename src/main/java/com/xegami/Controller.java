package com.xegami;

import com.google.gson.*;
import com.xegami.pojo.trn.FortniteStats;
import com.xegami.pojo.trn.Match;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private OkHttpClient client;

    public Controller() {
        client = new OkHttpClient();
    }

    public FortniteStats fortniteStatsCall(String epicNickname, String platform) throws IOException {
        Request request = new Request.Builder()
                .url(Ctts.FORTNITE_BR_STATS_BASE_API_URL + platform + "/" + epicNickname)
                .addHeader(Ctts.FORTNITE_TRN_API_HEADER_KEY, Ctts.FORTNITE_TRN_API_TOKEN)
                .build();

        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        Gson gson = new Gson();

        return gson.fromJson(jsonString, FortniteStats.class);
    }

    public List<Match> fortniteMatchesCall(String accountId) throws IOException {
        List<Match> matches = new ArrayList<>();

        Request request = new Request.Builder()
                .url(Ctts.FORTNITE_MATCH_HISTORY_BASE_API_URL + accountId + "/matches")
                .addHeader(Ctts.FORTNITE_TRN_API_HEADER_KEY, Ctts.FORTNITE_TRN_API_TOKEN)
                .build();

        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(jsonString);
        for (int i = 0; i < jsonArray.size(); i++) {
            matches.add(gson.fromJson(jsonArray.get(i), Match.class));
        }

        return matches;
    }
}
