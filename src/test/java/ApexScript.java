import com.google.gson.Gson;
import com.xegami.wabot.Main;
import com.xegami.wabot.core.Constants;
import com.xegami.wabot.http.apex.ApexController;
import com.xegami.wabot.http.fortnite.FortniteController;
import com.xegami.wabot.persistance.ApexCrud;
import com.xegami.wabot.persistance.FortniteCrud;
import com.xegami.wabot.pojo.domain.apex.ApexPlayer;
import com.xegami.wabot.pojo.domain.apex.Mozambiques;
import com.xegami.wabot.pojo.domain.fortnite.FortnitePlayer;
import com.xegami.wabot.pojo.dto.fortnite.UserIdDto;
import com.xegami.wabot.pojo.dto.fortnite.UserStatsDto;
import com.xegami.wabot.util.Utils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

public class ApexScript {
    ApexCrud apexCrud;
    ApexController apexController;

    @Before
    public void before() {
        Main.DEBUG = false;
        apexCrud = new ApexCrud();
        apexController = new ApexController();
    }

    @Test
    public void updateApexDb() {
        try {
            Mozambiques mozambiques = new Gson().fromJson(new FileReader(Constants.MOZAMBIQUES_DATA_PATH), Mozambiques.class);

            for (String u : mozambiques.getUsernames()) {
                String username = u.split("@")[0];
                String platform = u.split("@")[1];

                ApexPlayer result = apexController.getApexPlayerData(username, platform);
                System.out.println(result.getUsernameHandle() + " :: " + result.getKills() + " :: " + result.getPlatform());

                ApexPlayer a = apexCrud.findByUsername(username);

                ApexPlayer newApexPlayer = new ApexPlayer();
                newApexPlayer.setKills(result.getKills());
                newApexPlayer.setLevel(result.getLevel());
                newApexPlayer.setPlatform(result.getPlatform());
                newApexPlayer.setSource(result.getSource());
                newApexPlayer.setUsername(result.getUsername());
                newApexPlayer.setUsernameHandle(result.getUsernameHandle());

                if (a != null && a.getStartingKills() != null) {
                    newApexPlayer.setStartingKills(a.getStartingKills());
                } else {
                    newApexPlayer.setStartingKills(result.getKills());
                }

                apexCrud.update(newApexPlayer);
            }

            System.out.println("\nDatabase updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
