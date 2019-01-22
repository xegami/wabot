package com.xegami.pojo.nitrite;

import org.dizitart.no2.objects.Id;

public class Fortnutero {

    @Id
    private String username;
    private String wins;
    private String kills;

    public Fortnutero() {

    }

    public Fortnutero(String username, String wins, String kills) {
        this.username = username;
        this.wins = wins;
        this.kills = kills;
    }

    public String getUsername() {
        return username;
    }

    public String getWins() {
        return wins;
    }

    public Integer getWinsInt() {
        return Integer.parseInt(wins);
    }

    public String getKills() {
        return kills;
    }

    public Integer getKillsInt() {
        return Integer.parseInt(kills);
    }
}
