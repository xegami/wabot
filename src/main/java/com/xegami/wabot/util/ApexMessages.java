package com.xegami.wabot.util;

import com.xegami.wabot.pojo.domain.apex.ApexPlayer;

public class ApexMessages extends BaseMessages {

    public static String stats(ApexPlayer apexPlayer) {
        String message = "";

        message += "Stats de *" + apexPlayer.getUsername() + "*:" + n()
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

        message += "*1. /stats (usuario) (pc, ps4 o xbox)*:" + n()
                + "El bot solo registra los datos de las leyendas que tengas en https://apex.tracker.gg (mira la descripción del grupo para más información).";

        return message;
    }

    public static String today(ApexPlayer apexPlayer) {
        String message = "";
        int kills = apexPlayer.getKills() - apexPlayer.getStartingKills();

        message += "*" + apexPlayer.getUsername() + "* lleva hoy un total de *" + kills + "* kills.";

        return message;
    }

}
