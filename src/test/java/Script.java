import com.xegami.http.Controller;
import com.xegami.persistance.FortnuteroCrud;
import com.xegami.pojo.fortnite.UserId;
import com.xegami.pojo.fortnite.UserStats;
import com.xegami.pojo.nitrite.Fortnutero;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class Script {
    FortnuteroCrud crud;
    Controller controller;

    @Before
    public void before() {
        crud = new FortnuteroCrud();
        controller = new Controller();
    }

    @Test
    public void updateFortnutDB() {

        // todo: this is dirty as poggers but who gives a poggers
        try {
            crud.update(userStatsAction(getUsernameEncoded("xegami"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("oh soradekun"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("hydroxide cherot"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("hydroxide poison"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("makeano"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("pedrop_monkaaaas"), "pc")); // uses backup api
            crud.update(userStatsAction(getUsernameEncoded("lacocanoesmala"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("danir10"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("danalyn96"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("twitch saitrama"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("awashon"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("im rana gustavo"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("freaking_lukas"), "ps4")); // uses backup api
            crud.update(userStatsAction(getUsernameEncoded("samsalokuras"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("tulacid2000"), "xb1"));
            crud.update(userStatsAction(getUsernameEncoded("kingabdeel"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("kakemi"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("fuchiniano"), "pc")); // uses backup api

            // extras
            crud.update(userStatsAction(getUsernameEncoded("highdistortion"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("ninja"), "pc"));

            for (Fortnutero f : crud.findAll()) {
                System.out.print(f.getUsername());
                System.out.print(" - " + f.getWins());
                System.out.print(" - " + f.getKillsInt());
                System.out.print(" - " + f.getMatchesplayedInt());
                System.out.println(" - " + f.getPlatform());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getUsernameEncoded(String username) {
        return username.replace(" ", "%20");
    }

    private UserStats userStatsAction(String usernameEncoded, String platform) throws IOException {
        UserId userId = controller.getUserIdCall(usernameEncoded);

        UserStats userStats = controller.getUserStatsCall(userId.getUid(), platform);

        if (userStats.getTotals().getKillsInt() == 0) {
            return controller.getUserStatsBackupCall(usernameEncoded, platform);
        } else {
            return userStats;
        }
    }
}
