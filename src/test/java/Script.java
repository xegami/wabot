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
            crud.newEntry(getUserStats("xegami"));
            crud.newEntry(getUserStats("oh soradekun"));
            crud.newEntry(getUserStats("hydroxide cherot"));
            crud.newEntry(getUserStats("hydroxide poison"));
            crud.newEntry(getUserStats("pedrop_monkaaaas"));
            crud.newEntry(getUserStats("makeano"));
            crud.newEntry(getUserStats("lacocanoesmala"));
            crud.newEntry(getUserStats("chocofreik_"));
            crud.newEntry(getUserStats("danir10"));
            crud.newEntry(getUserStats("pedrop_monkaaaas"));
            crud.newEntry(getUserStats("danalyn96"));
            crud.newEntry(getUserStats("twitch saitrama"));
            crud.newEntry(getUserStats("awashon"));
            crud.newEntry(getUserStats("im rana gustavo"));
            crud.newEntry(getUserStats("samsalokuras"));
            crud.newEntry(getUserStats("ninja"));

            for (Fortnutero f : crud.findAll()) {
                System.out.print(f.getUsername());
                System.out.print(" " + f.getWins());
                System.out.println(" " + f.getKillsInt());
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
