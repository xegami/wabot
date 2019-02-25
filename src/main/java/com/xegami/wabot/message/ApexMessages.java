package com.xegami.wabot.message;

import com.xegami.wabot.pojo.domain.apex.ApexPlayer;

import java.util.List;

public class ApexMessages extends BaseMessages {

    public static String stats(ApexPlayer apexPlayer) {
        String message = "";

        message += "Stats de *" + apexPlayer.getUsernameHandle() + "*:" + n()
                + n()
                + "_Nivel: " + apexPlayer.getLevel() + "_" + n()
                + "_Kills: " + apexPlayer.getKills() + "_" + n()
                + n()
                + apexPlayer.getSource();

        return message;
    }

    public static String killer(String username, Integer kills) {
        String message = "";

        message += "¡*" + username + "* se acaba de hacer *" + kills + "* kills!";

        return message;
    }

    public static String info() {
        String message = "";

        message += "*1. /stats (usuario) (pc, ps4 o xbox)*" + n()
                + "*2. /today (usuario)*" +n()
                + "*3. /ranking*" + n()
                +n()
                + "El bot solo registra los datos de las leyendas que tengas en https://apex.tracker.gg (mira la descripción del grupo para más información).";

        return message;
    }

    public static String today(String username, Integer kills) {
        String message = "";

        message += "*" + username + "* lleva hoy un total de *" + kills + "* kills.";

        return message;
    }

    public static String ranking(List<ApexPlayer> apexPlayers) {
        String message = "";

        int cont = 1;
        for (ApexPlayer a : apexPlayers) {
            message += "#" + cont + " => *" + a.getUsernameHandle() + "* con *" + a.getKills() + "* kills." + n();
            cont++;
        }

        return message;
    }

}
