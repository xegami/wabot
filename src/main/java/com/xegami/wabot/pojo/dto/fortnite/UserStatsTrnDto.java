package com.xegami.wabot.pojo.dto.fortnite;

public class UserStatsTrnDto {

    private String epicUserHandle;
    private LifeTimeStatsDto[] lifeTimeStats;

    public String getEpicUserHandle() {
        return epicUserHandle;
    }

    public LifeTimeStatsDto[] getLifeTimeStats() {
        return lifeTimeStats;
    }
}
