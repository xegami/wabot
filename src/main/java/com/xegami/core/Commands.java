package com.xegami.core;

import com.xegami.pojo.fortnite.UserStats;
import org.openqa.selenium.Keys;

public class Commands {

    public String stats(UserStats userStats) {
        String message = "";

        message += "_Stats de *" + userStats.getUsernameFormatted() + "*:_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Partidas jugadas: " + userStats.getTotals().getMatchesplayed() + "_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Wins: " + userStats.getTotals().getWins() + "_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Winrate: " + userStats.getTotals().getWinrate() + "%_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Kills: " + userStats.getTotals().getKills() + "_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_K/D: " + userStats.getTotals().getKd() + "_";

        return message;
    }

    public String xegami() {
        return "es la polla jeje";
    }

    public String pedro() {
        return "tiene hambre jeje";
    }

    public String spain() {
        return "ARRIBA";
    }

}
