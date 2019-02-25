import com.google.gson.Gson;
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
                apexCrud.update(apexController.getApexPlayerData(username, platform));
                ApexPlayer a = apexCrud.findByUsername(username);
                System.out.println(a.getUsernameHandle() + " :: " + a.getKills() + " :: " + a.getPlatform());
            }

            System.out.println("Database updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
