package com.xegami.wabot.http.apex;

import com.xegami.wabot.Main;

public final class ApexEndpoints {

    /**
     * @apiNote Main API
     * https://apex.tracker.gg
     */
    public final static String APEX_TRN_API_PLAYER_DATA_URL = "https://public-api.tracker.gg/apex/v1/standard/profile";

    // auth
    public final static String APEX_TRN_API_HEADER_KEY = "TRN-Api-Key";

    public final static String APEX_TRN_API_HEADER_VALUE_PROD = "0cb989cf-68fe-4ee4-9d6a-5a2267eb0716";
    public final static String APEX_TRN_API_HEADER_VALUE_TEST = "279f287b-f4b7-47e0-b758-f197c62653e7";
    public final static String APEX_TRN_API_HEADER_VALUE = Main.DEBUG ? APEX_TRN_API_HEADER_VALUE_TEST : APEX_TRN_API_HEADER_VALUE_PROD;

    private ApexEndpoints() {

    }

}

