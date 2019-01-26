package com.xegami.wabot.pojo.nitrite;

public class Today {

    private int wins;
    private int kills;
    private int matches;

    public Today() {

    }

    public Today(int wins, int kills, int matches) {
        this.wins = wins;
        this.kills = kills;
        this.matches = matches;
    }

    public int getWins() {
        return wins;
    }

    public int getKills() {
        return kills;
    }

    public int getMatches() {
        return matches;
    }
}
