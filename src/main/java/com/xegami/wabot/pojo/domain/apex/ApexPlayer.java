package com.xegami.wabot.pojo.domain.apex;

import org.dizitart.no2.objects.Id;

public class ApexPlayer {

    @Id
    private String username;
    private String usernameHandle;
    private String platform;
    private Integer level;
    private Integer kills;
    private Integer startingKills;
    private String source;
    private Legends legends;

    public ApexPlayer() {

    }

    public ApexPlayer(String username, String usernameHandle, String platform, Integer level, Integer kills, String source, Legends legends) {
        this.username = username;
        this.usernameHandle = usernameHandle;
        this.platform = platform;
        this.level = level;
        this.kills = kills;
        this.source = source;
        this.legends = legends;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsernameHandle() {
        return usernameHandle;
    }

    public void setUsernameHandle(String usernameHandle) {
        this.usernameHandle = usernameHandle;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public Integer getStartingKills() {
        return startingKills;
    }

    public void setStartingKills(Integer startingKills) {
        this.startingKills = startingKills;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Legends getLegends() {
        return legends;
    }

    public void setLegends(Legends legends) {
        this.legends = legends;
    }

}
