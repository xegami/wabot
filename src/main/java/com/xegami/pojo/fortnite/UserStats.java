package com.xegami.pojo.fortnite;

public class UserStats {

    private String uid;
    private String username;
    private String platform;
    private Totals totals;

    public UserStats() {

    }

    public UserStats(String username, Totals totals, String platform) {
        this.username = username;
        this.totals = totals;
        this.platform = platform;
    }

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

}
