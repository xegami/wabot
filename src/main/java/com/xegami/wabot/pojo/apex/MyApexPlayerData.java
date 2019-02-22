package com.xegami.wabot.pojo.apex;

public class MyApexPlayerData {

    private String username;
    private Integer level;
    private Integer kills;
    private Integer damage;
    private Integer headshots;
    private Integer matchesPlayed;
    private String source;

    public MyApexPlayerData(String username, Integer level, Integer kills, Integer damage, Integer headshots, Integer matchesPlayed, String source) {
        this.username = username;
        this.level = level;
        this.kills = kills;
        this.damage = damage;
        this.headshots = headshots;
        this.matchesPlayed = matchesPlayed;
        this.source = source;
    }

    public String getUsername() {
        return username;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getKills() {
        return kills;
    }

    public Integer getDamage() {
        return damage;
    }

    public Integer getHeadshots() {
        return headshots;
    }

    public Integer getMatchesPlayed() {
        return matchesPlayed;
    }

    public String getSource() {
        return source;
    }
}
