package com.xegami.wabot.core;

import com.xegami.wabot.pojo.fortnite.UserStats;
import jdk.nashorn.internal.ir.annotations.Ignore;
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

    public String win(UserStats userStats, Integer kills) {
        String message = "";

        message += "*" + userStats.getUsername() + "* ganó con *" + kills + "* kills.";

        return message;
    }

    public String killer(UserStats userStats, Integer kills) {
        String message = "";

        message += "*" + userStats.getUsername() + "* perdió pero hizo *" + kills + "* kills.";

        return message;
    }

    public String camper(UserStats userStats, Integer kills) {
        String message = "";

        message += "*" + userStats.getUsername() + "* acaba de ganar una partida con *" + kills + "* kills." + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + Keys.chord(Keys.SHIFT, Keys.ENTER)
                + "_Campea un poco más rataaa._";

        return message;
    }

    public String trash(UserStats userStats) {
        String message = "";

        message += "*" + userStats.getUsername() + "* perdió y *ni una* kill se hizo.";

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
