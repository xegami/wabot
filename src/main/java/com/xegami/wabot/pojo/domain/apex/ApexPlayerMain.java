package com.xegami.wabot.pojo.domain.apex;

public class ApexPlayerMain {

    private String username;
    private String legend;
    private Integer kills;

    public ApexPlayerMain(String username, String legend, Integer kills) {
        this.username = username;
        this.legend = legend;
        this.kills = kills;
    }

    public String getUsername() {
        return username;
    }

    public String getLegend() {
        return legend;
    }

    public Integer getKills() {
        return kills;
    }
}
