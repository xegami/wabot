package com.xegami.pojo.trn;

public class Match {

    private float id;
    private String dateCollected;
    private float kills;
    private float matches;
    private String playlist;
    private float score;
    private float top1;

    public float getId() {
        return id;
    }

    public void setId(float id) {
        this.id = id;
    }

    public String getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(String dateCollected) {
        this.dateCollected = dateCollected;
    }

    public float getKills() {
        return kills;
    }

    public void setKills(float kills) {
        this.kills = kills;
    }

    public float getMatches() {
        return matches;
    }

    public void setMatches(float matches) {
        this.matches = matches;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public float getTop1() {
        return top1;
    }

    public void setTop1(float top1) {
        this.top1 = top1;
    }
}
