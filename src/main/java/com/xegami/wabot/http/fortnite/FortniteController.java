package com.xegami.wabot.http.fortnite;

import com.google.gson.Gson;
import com.xegami.wabot.pojo.dto.fortnite.TotalsDto;
import com.xegami.wabot.pojo.dto.fortnite.UserIdDto;
import com.xegami.wabot.pojo.dto.fortnite.UserStatsDto;
import com.xegami.wabot.pojo.dto.fortnite.LifeTimeStatsDto;
import com.xegami.wabot.pojo.dto.fortnite.UserStatsTrnDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class FortniteController {

    private OkHttpClient client;

    public FortniteController() {
        client = new OkHttpClient();
    }

    public UserIdDto getUserIdCall(String username) throws IOException {
        final String url = FortniteEndpoints.FORTNITE_API_GET_USER_ID + "?username=" + username;

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, UserIdDto.class);
    }

    public UserStatsDto getUserStatsCall(String userId, String platform) throws IOException {
        final String url = FortniteEndpoints.FORTNITE_API_GET_USER_STATS + "?user_id=" + userId + "&platform=" + platform;

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, UserStatsDto.class);
    }

    public UserStatsDto getUserStatsBackupCall(String username, String platform) throws IOException {
        String kills, wins, matchesplayed, winrate, kd;
        kills = wins = matchesplayed = winrate = kd = "";
        final String url = FortniteEndpoints.FORTNITE_TRN_API_BR_PLAYER_STATS + "/" + platform + "/" + username;

        Request request = new Request.Builder()
                .url(url)
                .addHeader(FortniteEndpoints.FORTNITE_TRN_API_HEADER_KEY, FortniteEndpoints.FORTNITE_TRN_API_HEADER_VALUE)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        UserStatsTrnDto userStatsTRN = new Gson().fromJson(jsonString, UserStatsTrnDto.class);

        LifeTimeStatsDto lifeTimeStats;
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

        return new UserStatsDto(userStatsTRN.getEpicUserHandle(), new TotalsDto(kills, wins, matchesplayed, winrate, kd), platform);
    }

}