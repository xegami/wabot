package com.xegami.pojo.fortnite;

public class UserStats {

    private String uid;
    private String username;
    private String platform;
    private Totals totals;

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPlatform() {
        return platform;
    }

    public Totals getTotals() {
        return totals;
    }

    public String getUsernameFormatted() {
        return username.replace("_", "");
    }
}
