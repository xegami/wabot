package com.xegami.wabot.message;

import com.xegami.wabot.pojo.domain.fortnite.Today;
import com.xegami.wabot.pojo.dto.fortnite.UserStatsDto;

public class FortniteMessages extends BaseMessages {

    public static String stats(UserStatsDto userStats) {
        String message = "";

        message += "StatsDto de *" + userStats.getUsername() + "*:" + n()
                + n()
                + "_Partidas jugadas: " + userStats.getTotals().getMatchesplayed() + "_" + n()
                + "_Wins: " + userStats.getTotals().getWins() + "_" + n()
                + "_Winrate: " + userStats.getTotals().getWinrate() + "%_" + n()
                + "_Kills: " + userStats.getTotals().getKills() + "_" + n()
                + "_K/D: " + userStats.getTotals().getKd() + "_";

        return message;
    }

    public static String today(UserStatsDto userStats, Today today) {
        String message = "";

        message += "Resumen de *" + userStats.getUsername() + "* de hoy:" + n()
                + n()
                + "_Partidas jugadas: " + today.getMatches() + "_" + n()
                + "_Wins: " + today.getWins() + "_" + n()
                + "_Kills: " + today.getKills() + "_";

        return message;
    }

    public static String win(UserStatsDto userStats, Integer kills) {
        String message = "";

        message += "*" + userStats.getUsername() + "* ganó con *" + kills + "* kills.";

        return message;
    }

    public static String killer(UserStatsDto userStats, Integer kills) {
        String message = "";

        message += "*" + userStats.getUsername() + "* perdió pero hizo *" + kills + "* kills.";

        return message;
    }

}
