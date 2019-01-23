package com.xegami.pojo.fortnite;

public class Totals {

    private String kills;
    private String wins;
    private String matchesplayed;
    private String winrate;
    private String kd;

    public Totals() {}

    public Totals(String kills, String wins, String matchesplayed, String winrate, String kd) {
        this.kills = kills;
        this.wins = wins;
        this.matchesplayed = matchesplayed;
        this.winrate = winrate;
        this.kd = kd;
    }

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

    public Integer getMatchesPlayedInt() {
        return Integer.parseInt(matchesplayed);
    }

    public String getWinrate() {
        return winrate;
    }

    public String getKd() {
        return kd;
    }
}
