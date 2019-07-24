package com.xegami.wabot.pojo.dto.tft;

public class LeagueEntryDTO {

    private String queueType;
    private String summonerName;
    private boolean hotStreak;
    private float wins;
    private boolean veteran;
    private float losses;
    private String rank;
    private String tier;
    private boolean inactive;
    private boolean freshBlood;
    private String leagueId;
    private String summonerId;
    private float leaguePoints;

    public String getQueueType() {
        return queueType;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public float getWins() {
        return wins;
    }

    public boolean isVeteran() {
        return veteran;
    }

    public float getLosses() {
        return losses;
    }

    public String getRank() {
        return rank;
    }

    public String getTier() {
        return tier;
    }

    public boolean isInactive() {
        return inactive;
    }

    public boolean isFreshBlood() {
        return freshBlood;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public float getLeaguePoints() {
        return leaguePoints;
    }
}
