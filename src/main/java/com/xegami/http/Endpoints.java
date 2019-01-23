package com.xegami.http;

public final class Endpoints {

    /**
     * https://fortniteapi.com/
     * Usage & limits
     * This API is free to use. We do not offer help, and there are no limits at this time. This can change at any time.
     * There would never be a limit per second or minute. All rights go to Epic Games. Do not forget to put this on your website or app.
     */
    public final static String FORTNITE_API_GET_USER_ID = "https://fortnite-public-api.theapinetwork.com/prod09/users/id";
    public final static String FORTNITE_API_GET_USER_STATS = "https://fortnite-public-api.theapinetwork.com/prod09/users/public/br_stats";

    /**
     * @apiNote Backup API
     * https://api.fortnitetracker.com
     */
    public final static String FORTNITE_TRN_API_BR_PLAYER_STATS = "https://api.fortnitetracker.com/v1/profile";
    public final static String FORTNITE_TRN_API_HEADER_KEY = "TRN-Api-Key";
    public final static String FORTNITE_TRN_API_HEADER_VALUE = "c8fd881f-ff6b-4e2a-a03b-4782986a4891";

    private Endpoints() {

    }

}
