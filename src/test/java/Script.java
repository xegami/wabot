import com.xegami.http.Controller;
import com.xegami.persistance.FortnuteroCrud;
import com.xegami.pojo.fortnite.UserId;
import com.xegami.pojo.fortnite.UserStats;
import com.xegami.pojo.nitrite.Fortnutero;
import org.junit.Test;

import java.io.IOException;

public class Script {

    @Test
    public void run() {
        try {
            FortnuteroCrud crud = new FortnuteroCrud();
            crud.updateEntry(getUserStats("xegami"));
            crud.updateEntry(getUserStats("oh soradekun"));
            crud.updateEntry(getUserStats("hydroxide cherot"));
            crud.updateEntry(getUserStats("hydroxide poison"));
            crud.updateEntry(getUserStats("pedrop_monkaaaas"));
            crud.updateEntry(getUserStats("makeano"));
            crud.updateEntry(getUserStats("lacocanoesmala"));
            crud.updateEntry(getUserStats("chocofreik_"));
            crud.updateEntry(getUserStats("danir10"));
            crud.updateEntry(getUserStats("pedrop_monkaaaas"));
            crud.updateEntry(getUserStats("danalyn96"));
            crud.updateEntry(getUserStats("twitch saitrama"));
            crud.updateEntry(getUserStats("awashon"));
            crud.updateEntry(getUserStats("im rana gustavo"));
            crud.updateEntry(getUserStats("samsalokuras"));
            crud.updateEntry(getUserStats("tulacid2000"));
            crud.updateEntry(getUserStats("ninja"));

            for (Fortnutero f : crud.findAll()) {
                System.out.print(f.getUsername());
                System.out.print(" - " + f.getWins());
                System.out.print(" - " + f.getKillsInt());
                System.out.println(" - " + f.getMatchesplayedInt());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private UserStats getUserStats(String username) throws IOException {
        Controller controller = new Controller();

        String usernameEncoded = username.replace(" ", "%20");
        UserId userId = controller.getUserIdRequest(usernameEncoded);

        return controller.getUserStatsRequest(userId.getUid(), userId.getPlatforms()[0]);
    }
}
