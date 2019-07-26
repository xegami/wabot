package com.xegami.wabot.message;

import com.xegami.wabot.pojo.domain.tft.TftPlayer;
import com.xegami.wabot.util.enums.Ranks;
import com.xegami.wabot.util.enums.Tiers;
import okhttp3.Headers;
import okhttp3.Response;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TftMessages extends BaseMessages {

    public static String stats(TftPlayer tftPlayer) {
        String message = "";
        double winratePercent = tftPlayer.getWins() / tftPlayer.getLosses() * 100;

        message += "Stats de *" + tftPlayer.getSummonerName() + "*:" + n()
                + n()
                + "Rango: *" + tftPlayer.getTier() + " " + tftPlayer.getRank() + " - " + Math.round(tftPlayer.getLeaguePoints()) + " LP" + "*" + n()
                + "Winrate: *" + Math.round(tftPlayer.getWins()) + "W/" + Math.round(tftPlayer.getLosses()) + "L (" + omegaRound(winratePercent, 2) + "%)" + (tftPlayer.isHotStreak() ? " ¡EN RACHA!*" : "*") + n()
                + n()
                + buildTrackerLink(tftPlayer.getSummonerName());

        return message;
    }

    public static String tierUp(String summonerName, String tier, String rank) {
        String message = "";

        message += "⬆ *" + summonerName + "* ha subido a *" + tier + " " + rank + "*";

        return message;
    }

    public static String tierDrop(String summonerName, String tier, String rank) {
        String message = "";

        message += "⬇ *" + summonerName + "* ha dropeado a *" + tier + " " + rank + "*";

        return message;
    }

    public static String help() {
        String message = "";

        message += "1. *!stats (usuario)*" + n()
                + "2. *!ranking*" + n()
                + "3. *!all*" + n()
                + "4. *!about*" + n()
                + "5. *!help*" + n();

        return message;
    }

    public static String ranking(List<TftPlayer> tftPlayers) {
        String message = "", averageTier, averageRank;
        int cont = 0, tiersSum = 0, ranksSum = 0;

        message += "*RANKING DEL GRUPO*" + n()
                + n();

        for (TftPlayer tftPlayer : tftPlayers) {
            cont++;
            message += "```#" + String.format("%02d", cont) + "``` → *" + tftPlayer.getSummonerName() + "* en *" + tftPlayer.getTier() + " " + tftPlayer.getRank() + "*" + n();
            tiersSum += Tiers.valueOf(tftPlayer.getTier()).ordinal();
            ranksSum += Ranks.valueOf(tftPlayer.getRank()).ordinal();
        }

        averageTier = Tiers.values()[tiersSum / cont].name();
        averageRank = Ranks.values()[ranksSum / cont].name();

        message += n()
                + "_Promedio: " + averageTier + " " + averageRank + "_";

        return message;
    }

    public static String response(Response response) {
        String message = "";
        int code = response.code();
        Headers headers = response.headers();

        message += "Code → " + code + n();
        for (int i = 0; i < headers.size(); i++) {
            message += headers.name(i) + " → " + headers.get(headers.name(i)) + n();
        }

        return message;
    }

    private static String buildTrackerLink(String username) {
        final String TRACKER_GG_URL = "https://tracker.gg/tft/profile/riot/EUW/";

        return TRACKER_GG_URL + username.replace(" ", "%20") + "/overview";
    }

    private static double omegaRound(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }

}
