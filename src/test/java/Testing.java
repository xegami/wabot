import com.xegami.pojo.bot.Player;
import com.xegami.pojo.trn.*;
import org.dizitart.no2.*;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;
import org.junit.Test;

public class Testing {

    @Test
    public void run() {
        Stats stats = new Stats();
        P2 p2 = new P2();
        P10 p10 = new P10();
        P9 p9 = new P9();
        Top1 tp2 = new Top1();
        Top1 tp10 = new Top1();
        Top1 tp9 = new Top1();
        tp2.setValueInt(101);
        tp10.setValueInt(74);
        tp9.setValueInt(114);
        p2.setTop1(tp2);
        p10.setTop1(tp10);
        p9.setTop1(tp9);
        stats.setP2(p2);
        stats.setP10(p10);
        stats.setP2(p2);

        Player player = new Player("Xegami", "pc", stats);
        //end mockup

        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("C:/Desarrollo/nitrite/fortnut.db")
                .openOrCreate();

        ObjectRepository<Player> repository = db.getRepository(Player.class);

        repository.insert(player);

        Player playerFound = repository.find(ObjectFilters.eq("epicNickname", "Xegami")).firstOrDefault();

        System.out.println(playerFound.getStats().getP2().getTop1().getValueInt());

    }
}
