package com.xegami.pojo.bot;

import com.xegami.pojo.trn.Stats;

public class Wins {

    private String epicNickname;
    private String platform;
    private Stats stats;

    public Wins(String epicNickname, String platform, Stats stats) {
        this.epicNickname = epicNickname;
        this.platform = platform;
        this.stats = stats;
    }

    public String getEpicNickname() {
        return epicNickname;
    }

    public void setEpicNickname(String epicNickname) {
        this.epicNickname = epicNickname;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
