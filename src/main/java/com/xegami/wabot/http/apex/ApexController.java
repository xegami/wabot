package com.xegami.wabot.http.apex;

import com.google.gson.Gson;
import com.xegami.wabot.pojo.domain.apex.ApexPlayer;
import com.xegami.wabot.pojo.domain.apex.Legends;
import com.xegami.wabot.pojo.dto.apex.ApexPlayerDataDto;
import com.xegami.wabot.pojo.dto.apex.ChildrenDto;
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
        String usernameHandle, source;
        Integer level, kills;
        level = kills = 0;
        final String url = ApexEndpoints.APEX_TRN_API_PLAYER_DATA_URL + "/" + platform + "/" + username;

        Request request = new Request.Builder()
                .url(url)
                .addHeader(ApexEndpoints.APEX_TRN_API_HEADER_KEY, ApexEndpoints.APEX_TRN_API_HEADER_VALUE)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        ApexPlayerDataDto apexPlayerDataDto = new Gson().fromJson(jsonString, ApexPlayerDataDto.class);
        usernameHandle = apexPlayerDataDto.getData().getMetadata().getPlatformUserHandle();

        StatsDto[] stats = apexPlayerDataDto.getData().getStats();
        for (int i = 0; i < stats.length; i++) {
            String key = stats[i].getMetadata().getKey();
            switch (key) {
                case "Level":
                    level = stats[i].getValue().intValue();
                    break;
                case "Kills":
                    kills = stats[i].getValue().intValue();
                    break;
            }
        }

        Legends legends = new Legends();
        ChildrenDto[] children = apexPlayerDataDto.getData().getChildren();
        for (int i = 0; i < children.length; i++) {
            String name = children[i].getMetadata().getLegend_name();

            StatsDto[] legendStats = children[i].getStats();
            for (int o = 0; o < legendStats.length; o++) {
                String key = legendStats[o].getMetadata().getKey();
                Integer value = legendStats[o].getValue().intValue();

                if (key.equals("Kills")) {
                    switch (name) {
                        case "Bloodhound":
                            legends.setBloodhoundKills(value);
                            break;
                        case "Gibraltar":
                            legends.setGibraltarKills(value);
                            break;
                        case "Octane":
                            legends.setOctaneKills(value);
                            break;
                        case "Wraith":
                            legends.setWraithKills(value);
                            break;
                        case "Pathfinder":
                            legends.setPathfinderKills(value);
                            break;
                        case "Bangalore":
                            legends.setBangaloreKills(value);
                            break;
                        case "Lifeline":
                            legends.setLifelineKills(value);
                            break;
                        case "Caustic":
                            legends.setCausticKills(value);
                            break;
                        case "Mirage":
                            legends.setMirageKills(value);
                            break;
                    }
                }
            }
        }

        source = buildSource(username, platform);

        return new ApexPlayer(username, usernameHandle, platform, level, kills, source, legends);
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