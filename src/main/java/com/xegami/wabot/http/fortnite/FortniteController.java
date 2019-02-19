package com.xegami.wabot.http.fortnite;

import com.google.gson.Gson;
import com.xegami.wabot.pojo.fortnite.Totals;
import com.xegami.wabot.pojo.fortnite.UserId;
import com.xegami.wabot.pojo.fortnite.UserStats;
import com.xegami.wabot.pojo.trn.LifeTimeStats;
import com.xegami.wabot.pojo.trn.UserStatsTRN;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class FortniteController {

    private OkHttpClient client;

    public FortniteController() {
        client = new OkHttpClient();
    }

    public UserId getUserIdCall(String username) throws IOException {
        final String url = FortniteEndpoints.FORTNITE_API_GET_USER_ID + "?username=" + username;

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, UserId.class);
    }

    public UserStats getUserStatsCall(String userId, String platform) throws IOException {
        final String url = FortniteEndpoints.FORTNITE_API_GET_USER_STATS + "?user_id=" + userId + "&platform=" + platform;

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, UserStats.class);
    }

    public UserStats getUserStatsBackupCall(String username, String platform) throws IOException {
        String kills, wins, matchesplayed, winrate, kd;
        kills = wins = matchesplayed = winrate = kd = "";
        final String url = FortniteEndpoints.FORTNITE_TRN_API_BR_PLAYER_STATS + "/" + platform + "/" + username;

        Request request = new Request.Builder()
                .url(url)
                .addHeader(FortniteEndpoints.FORTNITE_TRN_API_HEADER_KEY, FortniteEndpoints.FORTNITE_TRN_API_HEADER_VALUE)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        UserStatsTRN userStatsTRN = new Gson().fromJson(jsonString, UserStatsTRN.class);

        LifeTimeStats lifeTimeStats;
        for (int i = 0; i < userStatsTRN.getLifeTimeStats().length; i++) {
            lifeTimeStats = userStatsTRN.getLifeTimeStats()[i];

            switch (lifeTimeStats.getKey()) {
                case "Matches Played":
                    matchesplayed = lifeTimeStats.getValue();
                    break;
                case "Wins":
                    wins = lifeTimeStats.getValue();
                    break;
                case "Win%":
                    winrate = lifeTimeStats.getValue().replace("%", "");
                    break;
                case "Kills":
                    kills = lifeTimeStats.getValue();
                    break;
                case "K/d":
                    kd = lifeTimeStats.getValue();
                    break;
            }
        }

        return new UserStats(userStatsTRN.getEpicUserHandle(), new Totals(kills, wins, matchesplayed, winrate, kd), platform);
    }

}