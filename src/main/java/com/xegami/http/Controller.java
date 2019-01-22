package com.xegami.http;

import com.google.gson.Gson;
import com.xegami.pojo.fortnite.UserId;
import com.xegami.pojo.fortnite.UserStats;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Controller {

    private OkHttpClient client;

    public Controller() {
        client = new OkHttpClient();
    }

    public UserId getUserIdRequest(String username) throws IOException {
        final String url = Endpoints.FORTNITE_API_GET_USER_ID + "?username=" + username;

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, UserId.class);
    }

    public UserStats getUserStatsRequest(String userId, String platform) throws IOException {
        final String url = Endpoints.FORTNITE_API_GET_USER_STATS + "?user_id=" + userId + "&platform=" + platform;

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, UserStats.class);
    }
}
