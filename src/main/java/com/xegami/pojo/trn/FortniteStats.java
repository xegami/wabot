package com.xegami.pojo.trn;

public class FortniteStats {

    private String accountId;
    private Stats stats;
    private LifeTimeStats[] lifeTimeStats;
    private String epicUserHandle;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public LifeTimeStats[] getLifeTimeStats() {
        return lifeTimeStats;
    }

    public void setLifeTimeStats(LifeTimeStats[] lifeTimeStats) {
        this.lifeTimeStats = lifeTimeStats;
    }

    public String getEpicUserHandle() {
        return epicUserHandle;
    }

    public void setEpicUserHandle(String epicUserHandle) {
        this.epicUserHandle = epicUserHandle;
    }

    public String getEpicUserHandleFormatted() {
        return epicUserHandle.replace("_", "");
    }
}
