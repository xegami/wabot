package com.xegami.wabot.pojo.domain.apex;

import org.dizitart.no2.objects.Id;

public class ApexPlayer {

    @Id
    private String username;
    private String platform;
    private Integer level;
    private Integer kills;
    private Integer startingKills;
    private String source;

    public ApexPlayer(String username, String platform, Integer level, Integer kills, String source) {
        this.username = username;
        this.platform = platform;
        this.level = level;
        this.kills = kills;
        this.source = source;
    }

    public String getUsername() {
        return username;
    }

    public String getPlatform() {
        return platform;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getKills() {
        return kills;
    }

    public Integer getStartingKills() {
        return startingKills;
    }

    public String getSource() {
        return source;
    }

    public void setStartingKills(Integer startingKills) {
        this.startingKills = startingKills;
    }

    public ApexPlayer() {

    }

}
