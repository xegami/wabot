package com.xegami.core;

import com.xegami.pojo.fortnite.UserStats;
import org.openqa.selenium.Keys;

public class MessageBuilder {

    public String stats(UserStats userStats) {
        String message = "";

        message += "Stats de *" + userStats.getUsername() + "*:" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Partidas jugadas: " + userStats.getTotals().getMatchesplayed() + "_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Wins: " + userStats.getTotals().getWins() + "_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Winrate: " + userStats.getTotals().getWinrate() + "%_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Kills: " + userStats.getTotals().getKills() + "_" + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_K/D: " + userStats.getTotals().getKd() + "_";

        return message;
    }

    public String win(UserStats userStats, Integer oldKills) {
        String message = "";

        message += "*" + userStats.getUsername() + "* acaba de ganar una win con " + (userStats.getTotals().getKillsInt() - oldKills) + " kills." + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Wins: " + userStats.getTotals().getWins() + "_";

        return message;
    }

    public String killer(UserStats userStats, Integer oldKills) {
        String message = "";

        message += "*" + userStats.getUsername() + "* acaba de perder una partida y llevaba " + (userStats.getTotals().getKillsInt() - oldKills) + " kills." + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_sadpoggers._";

        return message;
    }

    public String trash(UserStats userStats) {
        String message = "";

        message += "*" + userStats.getUsername() + "* acaba de perder una partida y no se ha hecho ni una kill." + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Desinstala el Fortnite anda._";

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
