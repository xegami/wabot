package com.xegami.wabot.message;

import com.xegami.wabot.pojo.domain.apex.ApexPlayer;

import java.util.List;

public class ApexMessages extends BaseMessages {

    public static String stats(ApexPlayer apexPlayer) {
        String message = "";

        message += "Stats de *" + apexPlayer.getUsernameHandle() + "*:" + n()
                + n()
                + "Nivel: *" + apexPlayer.getLevel() + "*" + n()
                + "Kills: *" + apexPlayer.getKills() + "*" + n()
                + n()
                + apexPlayer.getSource();

        return message;
    }

    public static String killer(String username, Integer kills) {
        String message = "";

        message += "*" + username + "*, ¡SE ACABA DE HACER *" + kills + "* KILLS!";

        return message;
    }

    public static String info() {
        String message = "";

        message += "*1. /stats (usuario) (pc, ps4 o xbox)*" + n()
                + "*2. /today (usuario)*" + n()
                + "*3. /ranking*" + n()
                + "*4. /all*" + n()
                + n()
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
            message += "```#" + String.format("%02d", cont) + "``` → *" + a.getUsernameHandle() + "* con *" + a.getKills() + (a.getKills() == 1 ? "* kill." : "* kills.") + n();
            cont++;
        }

        return message;
    }

    public static String reset(List<ApexPlayer> apexPlayers) {
        String message = "";

        message += "*ATENCIÓN*" + n()
                + n()
                + "Los stats diarios se han reseteado, este ha sido el resumen de hoy:" + n()
                + n();

        int cont = 1;
        for (ApexPlayer a : apexPlayers) {
            int todayKills = a.getKills() - a.getStartingKills();
            message += "```#" + String.format("%02d", cont) + "``` → *" + a.getUsernameHandle() + "* hizo *" + todayKills + (todayKills == 1 ? "* kill." : "* kills.") + n();
            cont++;
        }

        return message;
    }

}
