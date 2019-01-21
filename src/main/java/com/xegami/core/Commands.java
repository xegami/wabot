package com.xegami.core;

import com.xegami.http.Controller;
import com.xegami.pojo.bot.Parameters;
import com.xegami.pojo.trn.FortniteStats;
import com.xegami.pojo.trn.LifeTimeStats;
import com.xegami.pojo.trn.Match;
import com.xegami.utils.Ctts;
import org.openqa.selenium.Keys;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class Commands {

    private Controller controller;

    public Commands() {
        controller = new Controller();
    }

    public String wins(Parameters parameters) throws IOException {

        FortniteStats stats = controller.fortniteStatsCall(parameters.getEpicNicknameEncoded(), parameters.getPlatform());

        return "Número de wins en solitario de *" + stats.getEpicUserHandleFormatted() + "* --> " + stats.getStats().getP2().getTop1().getValue();
    }

    public String lifetimeStats(Parameters parameters) throws IOException {
        FortniteStats stats = controller.fortniteStatsCall(parameters.getEpicNicknameEncoded(), parameters.getPlatform());
        LifeTimeStats[] lifeTimeStats = stats.getLifeTimeStats();
        StringBuilder builder = new StringBuilder();

        builder.append(Ctts.ITALIC);
        builder.append("Stats de *");
        builder.append(stats.getEpicUserHandleFormatted());
        builder.append("*:");
        builder.append(Ctts.ITALIC);
        builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));
        builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));

        for (LifeTimeStats lts : lifeTimeStats) {
            String key = lts.getKey();
            switch (key) {
                case "Matches Played":
                    builder.append(Ctts.ITALIC);
                    builder.append("Partidas jugadas: ");
                    builder.append(lts.getValue());
                    builder.append(Ctts.ITALIC);
                    builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));
                    break;
                case "Player":
                    builder.append(Ctts.ITALIC);
                    builder.append("Player: ");
                    builder.append(lts.getValue());
                    builder.append(Ctts.ITALIC);
                    builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));
                    break;
                case "Win%":
                    builder.append(Ctts.ITALIC);
                    builder.append("% de wins: ");
                    builder.append(lts.getValue());
                    builder.append(Ctts.ITALIC);
                    builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));
                    break;
                case "Kills":
                    builder.append(Ctts.ITALIC);
                    builder.append("Kills: ");
                    builder.append(lts.getValue());
                    builder.append(Ctts.ITALIC);
                    builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));
                    break;
                case "K/d":
                    builder.append(Ctts.ITALIC);
                    builder.append("K/d: ");
                    builder.append(lts.getValue());
                    builder.append(Ctts.ITALIC);
                    builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));
                    break;
            }
        }

        return builder.toString();
    }

    @Deprecated
    public String today(Parameters parameters) throws IOException {
        float totalKills = 0;
        float totalMatches = 0;
        float totalWins = 0;

        Calendar c1 = Calendar.getInstance();
        int currentDay = c1.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c1.get(Calendar.MONTH);
        int currentYear = c1.get(Calendar.YEAR);

        FortniteStats fortniteStats = controller.fortniteStatsCall(parameters.getEpicNicknameEncoded(), parameters.getPlatform());
        List<Match> matches = controller.fortniteMatchesCall(fortniteStats.getAccountId());

        for (Match m : matches) {
            Calendar c2 = javax.xml.bind.DatatypeConverter.parseDateTime(m.getDateCollected());
            int matchDay = c2.get(Calendar.DAY_OF_MONTH);
            int matchMonth = c2.get(Calendar.MONTH);
            int matchYear = c2.get(Calendar.YEAR);

            if (currentDay == matchDay && currentMonth == matchMonth && currentYear == matchYear) {
                totalKills += m.getKills();
                totalMatches += m.getMatches();
                totalWins += m.getTop1();
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append(Ctts.ITALIC);
        builder.append("Resumen de *");
        builder.append(fortniteStats.getEpicUserHandleFormatted());
        builder.append("* de hoy:");
        builder.append(Ctts.ITALIC);
        builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));

        builder.append(Ctts.ITALIC);
        builder.append("Partidas jugadas: ");
        builder.append(Math.round(totalMatches));
        builder.append(Ctts.ITALIC);
        builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));

        builder.append(Ctts.ITALIC);
        builder.append("Player: ");
        builder.append(Math.round(totalWins));
        builder.append(Ctts.ITALIC);
        builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));

        builder.append(Ctts.ITALIC);
        builder.append("Kills: ");
        builder.append(Math.round(totalKills));
        builder.append(Ctts.ITALIC);
        builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));

        if (totalWins == 0 && totalMatches > 0) {
            builder.append(Keys.chord(Keys.SHIFT, Keys.ENTER));
            builder.append("Ni una gana el manco.");
        }

        return builder.toString();
    }

    public String info() {

        return null;
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
