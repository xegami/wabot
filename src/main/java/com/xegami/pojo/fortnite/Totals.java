package com.xegami.pojo.fortnite;

public class Totals {

    private String kills;
    private String wins;
    private String matchesplayed;
    private String winrate;
    private String kd;

    public String getKills() {
        return kills;
    }

    public Integer getKillsInt() {
        return Integer.parseInt(kills);
    }

    public String getWins() {
        return wins;
    }

    public Integer getWinsInt() {
        return Integer.parseInt(wins);
    }

    public String getMatchesplayed() {
        return matchesplayed;
    }

    public String getWinrate() {
        return winrate;
    }

    public String getKd() {
        return kd;
    }
}
