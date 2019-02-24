import com.xegami.wabot.http.fortnite.FortniteController;
import com.xegami.wabot.persistance.FortniteCrud;
import com.xegami.wabot.pojo.dto.fortnite.UserIdDto;
import com.xegami.wabot.pojo.dto.fortnite.UserStatsDto;
import com.xegami.wabot.pojo.domain.fortnite.FortnitePlayer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class FortniteScript {
    FortniteCrud crud;
    FortniteController fortniteController;

    @Before
    public void before() {
        crud = new FortniteCrud();
        fortniteController = new FortniteController();
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
            crud.update(userStatsAction(getUsernameEncoded("sr.burger.king"), "pc")); // uses backup api
            crud.update(userStatsAction(getUsernameEncoded("lacocanoesmala"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("danir10"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("danalyn96"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("twitch saitrama"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("awashon"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("freaking_lukas"), "ps4")); // uses backup api
            crud.update(userStatsAction(getUsernameEncoded("im rana gustavo"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("samsalokuras"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("tulacid2000"), "xb1"));
            crud.update(userStatsAction(getUsernameEncoded("kingabdeel"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("kakemi"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("fuchiniano"), "pc")); // uses backup api
            crud.update(userStatsAction(getUsernameEncoded("HulioElGrande"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("xxsaluespxx"), "ps4"));
            crud.update(userStatsAction(getUsernameEncoded("viola niños 69"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("x27amb27x"), "pc"));
            crud.update(userStatsAction(getUsernameEncoded("m a r v e l"), "pc"));

            for (FortnitePlayer f : crud.findAll()) {
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

    private UserStatsDto userStatsAction(String usernameEncoded, String platform) throws IOException {
        UserIdDto userId = fortniteController.getUserIdCall(usernameEncoded);

        UserStatsDto userStats = fortniteController.getUserStatsCall(userId.getUid(), platform);

        if (userStats.getTotals().getKillsInt() == 0) {
            return fortniteController.getUserStatsBackupCall(usernameEncoded, platform);
        } else {
            return userStats;
        }
    }
}
