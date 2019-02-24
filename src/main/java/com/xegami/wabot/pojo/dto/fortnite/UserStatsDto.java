package com.xegami.wabot.pojo.dto.fortnite;

public class UserStatsDto {

    private String uid;
    private String username;
    private String platform;
    private TotalsDto totals;

    public UserStatsDto() {

    }

    public UserStatsDto(String username, TotalsDto totals, String platform) {
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

    public TotalsDto getTotals() {
        return totals;
    }

}
