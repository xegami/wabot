package com.xegami.wabot.core;

import com.xegami.wabot.pojo.apex.MyApexPlayerData;
import com.xegami.wabot.pojo.fortnite.UserStats;
import com.xegami.wabot.pojo.nitrite.Today;
import org.openqa.selenium.Keys;
import twitter4j.Status;

import java.util.Calendar;

public class MessageBuilder {

    // new line
    private String n() {
        return Keys.chord(Keys.SHIFT, Keys.ENTER);
    }

    public String stats(UserStats userStats) {
        String message = "";

        message += "Stats de *" + userStats.getUsername() + "*:" + n()
                + n()
                + "_Partidas jugadas: " + userStats.getTotals().getMatchesplayed() + "_" + n()
                + "_Wins: " + userStats.getTotals().getWins() + "_" + n()
                + "_Winrate: " + userStats.getTotals().getWinrate() + "%_" + n()
                + "_Kills: " + userStats.getTotals().getKills() + "_" + n()
                + "_K/D: " + userStats.getTotals().getKd() + "_";

        return message;
    }

    public String stats(MyApexPlayerData apexPlayerData) {
        String message = "";

        message += "Stats de *" + apexPlayerData.getUsername() + "*:" + n()
                + n()
                + "_Nivel: " + apexPlayerData.getLevel() + "_" + n()
                + "_Kills: " + apexPlayerData.getKills() + "_" + n()
                + "_Daño: " + apexPlayerData.getDamage() + "_" + n()
                + n()
                + apexPlayerData.getSource();

        return message;
    }

    public String today(UserStats userStats, Today today) {
        String message = "";

        message += "Resumen de *" + userStats.getUsername() + "* de hoy:" + n()
                + n()
                + "_Partidas jugadas: " + today.getMatches() + "_" + n()
                + "_Wins: " + today.getWins() + "_" + n()
                + "_Kills: " + today.getKills() + "_";

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

    public String info() {
        String message = "";

        message += "*1. /stats (usuario) (pc, ps4 o xbox)*:" + n()
                + "El bot solo registra los datos de las leyendas que tengas en https://apex.tracker.gg (mira la descripción del grupo para más información).";

        return message;
    }

    public String tuit(Status status) {
        String message = "";
        String url = "https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId();

        message += "*@PlayApex hace unos segundos:*" + n()
                + n()
                + "\"" + status.getText() + "\"" + n()
                + n()
                + url;

        return message;
    }

}
