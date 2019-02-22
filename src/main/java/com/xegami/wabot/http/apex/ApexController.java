package com.xegami.wabot.http.apex;

import com.google.gson.Gson;
import com.xegami.wabot.pojo.apex.ApexPlayerData;
import com.xegami.wabot.pojo.apex.MyApexPlayerData;
import com.xegami.wabot.pojo.apex.Stats;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApexController {

    private OkHttpClient client;

    public ApexController() {
        client = new OkHttpClient();
    }

    public MyApexPlayerData getApexPlayerData(String username, String platform) throws IOException {
        Integer level, kills, damage, headshots, matchesPlayed;
        level = kills = damage = headshots = matchesPlayed = 0;
        final String url = ApexEndpoints.APEX_TRN_API_PLAYER_DATA_URL + "/" + platform + "/" + username;

        Request request = new Request.Builder()
                .url(url)
                .addHeader(ApexEndpoints.APEX_TRN_API_HEADER_KEY, ApexEndpoints.APEX_TRN_API_HEADER_VALUE)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        ApexPlayerData apexPlayerData = new Gson().fromJson(jsonString, ApexPlayerData.class);

        Stats[] stats = apexPlayerData.getData().getStats();
        for (int i = 0; i < stats.length; i++) {
            String key = stats[i].getMetadata().getKey();
            switch (key) {
                case "Level":
                    level = stats[i].getValue();
                    break;
                case "Kills":
                    kills = stats[i].getValue();
                    break;
                case "Damage":
                    damage = stats[i].getValue();
                    break;
                case "Headshots":
                    headshots = stats[i].getValue();
                    break;
                case "matchesPlayed":
                    matchesPlayed = stats[i].getValue();
                    break;
            }
        }

        return new MyApexPlayerData(
                apexPlayerData.getData().getMetadata().getPlatformUserHandle(),
                level,
                kills,
                damage,
                headshots,
                matchesPlayed,
                buildSource(username, platform)
        );
    }

    private String buildSource(String username, String platformCode) {
        final String baseUrl = "https://apex.tracker.gg/profile";
        String platform = null;

        switch (platformCode) {
            case "5":
                platform = "pc";
                break;
            case "2":
                platform = "psn";
                break;
            case "1":
                platform = "xbl";
                break;
        }

        if (platform == null) {
            return "";
        }

        return baseUrl + "/" + platform + "/" + username;
    }

}