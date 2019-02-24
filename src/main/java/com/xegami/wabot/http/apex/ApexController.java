package com.xegami.wabot.http.apex;

import com.google.gson.Gson;
import com.xegami.wabot.pojo.domain.apex.TodayApexPlayer;
import com.xegami.wabot.pojo.dto.apex.ApexPlayerDataDto;
import com.xegami.wabot.pojo.domain.apex.ApexPlayer;
import com.xegami.wabot.pojo.dto.apex.StatsDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class ApexController {

    private OkHttpClient client;

    public ApexController() {
        client = new OkHttpClient();
    }

    public ApexPlayer getApexPlayerData(String username, String platform) throws IOException {
        Integer level, kills, damage, headshots, matchesPlayed;
        level = kills = damage = headshots = matchesPlayed = 0;
        final String url = ApexEndpoints.APEX_TRN_API_PLAYER_DATA_URL + "/" + platform + "/" + username;

        Request request = new Request.Builder()
                .url(url)
                .addHeader(ApexEndpoints.APEX_TRN_API_HEADER_KEY, ApexEndpoints.APEX_TRN_API_HEADER_VALUE)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        ApexPlayerDataDto apexPlayerDataDto = new Gson().fromJson(jsonString, ApexPlayerDataDto.class);

        StatsDto[] stats = apexPlayerDataDto.getData().getStats();
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

        return new ApexPlayer(
                apexPlayerDataDto.getData().getMetadata().getPlatformUserHandle(),
                level,
                kills,
                damage,
                headshots,
                matchesPlayed,
                buildSource(username, platform),
                platform,
                null
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