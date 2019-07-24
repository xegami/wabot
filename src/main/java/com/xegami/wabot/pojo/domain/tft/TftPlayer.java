package com.xegami.wabot.pojo.domain.tft;

import org.dizitart.no2.objects.Id;

public class TftPlayer {

    private String queueType;
    @Id
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

    public TftPlayer() {

    }

    public TftPlayer(String queueType, String summonerName, boolean hotStreak, float wins, boolean veteran, float losses, String rank, String tier, boolean inactive, boolean freshBlood, String leagueId, String summonerId, float leaguePoints) {
        this.queueType = queueType;
        this.summonerName = summonerName;
        this.hotStreak = hotStreak;
        this.wins = wins;
        this.veteran = veteran;
        this.losses = losses;
        this.rank = rank;
        this.tier = tier;
        this.inactive = inactive;
        this.freshBlood = freshBlood;
        this.leagueId = leagueId;
        this.summonerId = summonerId;
        this.leaguePoints = leaguePoints;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public boolean isHotStreak() {
        return hotStreak;
    }

    public void setHotStreak(boolean hotStreak) {
        this.hotStreak = hotStreak;
    }

    public float getWins() {
        return wins;
    }

    public void setWins(float wins) {
        this.wins = wins;
    }

    public boolean isVeteran() {
        return veteran;
    }

    public void setVeteran(boolean veteran) {
        this.veteran = veteran;
    }

    public float getLosses() {
        return losses;
    }

    public void setLosses(float losses) {
        this.losses = losses;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public boolean isFreshBlood() {
        return freshBlood;
    }

    public void setFreshBlood(boolean freshBlood) {
        this.freshBlood = freshBlood;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public float getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(float leaguePoints) {
        this.leaguePoints = leaguePoints;
    }
}
