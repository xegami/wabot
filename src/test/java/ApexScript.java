import com.xegami.wabot.http.apex.ApexController;
import com.xegami.wabot.http.fortnite.FortniteController;
import com.xegami.wabot.persistance.ApexCrud;
import com.xegami.wabot.persistance.FortniteCrud;
import com.xegami.wabot.pojo.domain.apex.ApexPlayer;
import com.xegami.wabot.pojo.domain.fortnite.FortnitePlayer;
import com.xegami.wabot.pojo.dto.fortnite.UserIdDto;
import com.xegami.wabot.pojo.dto.fortnite.UserStatsDto;
import org.junit.Before;
import org.junit.Test;

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
            apexCrud.update(apexController.getApexPlayerData("xegami", "5"));
            apexCrud.update(apexController.getApexPlayerData("theraclos", "5"));
            apexCrud.update(apexController.getApexPlayerData("cheerot", "5"));
            apexCrud.update(apexController.getApexPlayerData("colabo96", "5"));
            apexCrud.update(apexController.getApexPlayerData("dsanabria8", "5"));
            apexCrud.update(apexController.getApexPlayerData("furioeloy", "5"));
            apexCrud.update(apexController.getApexPlayerData("metanax", "5"));
            apexCrud.update(apexController.getApexPlayerData("pedropxx7", "5"));
            apexCrud.update(apexController.getApexPlayerData("poisonzz98", "5"));
            apexCrud.update(apexController.getApexPlayerData("saitramasama", "5"));
            apexCrud.update(apexController.getApexPlayerData("solerpro", "5"));
            apexCrud.update(apexController.getApexPlayerData("thegodkaiser", "5"));
            apexCrud.update(apexController.getApexPlayerData("x27amb27x", "5"));
            apexCrud.update(apexController.getApexPlayerData("xxmakeanoxx", "5"));
            apexCrud.update(apexController.getApexPlayerData("xxperroenllamaxx", "5"));
            apexCrud.update(apexController.getApexPlayerData("xxxsaluespxxx", "5"));

            for (ApexPlayer a : apexCrud.findAll()) {
                System.out.println(a.getUsername() + " :: " + a.getKills() + " :: " + a.getPlatform());
            }

            System.out.println("Database updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
