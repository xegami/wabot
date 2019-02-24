package com.xegami.wabot.pojo.domain.apex;

import org.dizitart.no2.objects.Id;

public class ApexPlayer {

    @Id
    private String username;
    private Integer level;
    private Integer kills;
    private Integer damage;
    private Integer headshots;
    private Integer matchesPlayed;
    private String source;
    private String platform;
    private TodayApexPlayer todayApexPlayer;

    public ApexPlayer() {

    }

    public ApexPlayer(String username, Integer level, Integer kills, Integer damage, Integer headshots, Integer matchesPlayed, String source, String platform, TodayApexPlayer todayApexPlayer) {
        this.username = username;
        this.level = level;
        this.kills = kills;
        this.damage = damage;
        this.headshots = headshots;
        this.matchesPlayed = matchesPlayed;
        this.source = source;
        this.platform = platform;
        this.todayApexPlayer = todayApexPlayer;
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

    public String getPlatform() {
        return platform;
    }

    public TodayApexPlayer getTodayApexPlayer() {
        return todayApexPlayer;
    }
}
