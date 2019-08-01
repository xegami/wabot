//import com.xegami.wabot.pojo.domain.apex.ApexPlayer;


import com.google.gson.Gson;
import com.xegami.wabot.Main;
import com.xegami.wabot.core.Constants;
import com.xegami.wabot.http.tft.TftController;
import com.xegami.wabot.http.tft.TftEndpoints;
import com.xegami.wabot.persistance.TftRepository;
import com.xegami.wabot.pojo.domain.tft.TftPlayer;
import com.xegami.wabot.pojo.dto.tft.LeagueEntryDTO;
import com.xegami.wabot.pojo.dto.tft.SummonerDTO;
import com.xegami.wabot.pojo.values.WabotValues;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TftDDBBLaunchScript extends TftController {

    private TftRepository tftRepository;
    private WabotValues wabotValues;
    private OkHttpClient client;

    @Before
    public void before() {
        Main.DEBUG = false;
        client = new OkHttpClient();
        tftRepository = new TftRepository();
        wabotValues = loadWabotValues();
    }

    @Test
    public void updateTftDb() {
        try {

            for (String username : wabotValues.getWhatsAppContacts().getLolUsernames()) {

                LeagueEntryDTO result = getTftSummonerLeagueEntries(username.replace(" ", "%20"));

                if (result == null) {
                    continue;
                }

                System.out.println(result.getSummonerName() + " :: " + result.getTier() + " :: " + result.getRank());

                TftPlayer tftPlayerNew = new TftPlayer(result.getQueueType(), result.getSummonerName(), result.isHotStreak(), result.getWins(), result.isVeteran(), result.getLosses(), result.getRank(), result.getTier(), result.isInactive(), result.isFreshBlood(), result.getLeagueId(), result.getSummonerId(), result.getLeaguePoints());

                tftRepository.update(tftPlayerNew);
            }

            System.out.println("\nDatabase updated.");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public WabotValues loadWabotValues() {
        try {
            return new Gson().fromJson(new FileReader(Constants.WABOT_VALUES_PATH), WabotValues.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    private SummonerDTO getSummoner(String username) throws IOException {
        final String url = getSummonerSearchUrl(username, null) ;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Riot-Token", wabotValues.getApiKeys().getTft().getApiKey())
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        return new Gson().fromJson(jsonString, SummonerDTO.class);
    }

    private LeagueEntryDTO getTftSummonerLeagueEntries(String username) throws IOException {
        LeagueEntryDTO leagueEntryDTO = null;
        String summonerId = getSummoner(username).getId();

        if (summonerId == null) {
            return null;
        }

        final String url = getLeagueEntriesUrl(summonerId, null);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-Riot-Token", wabotValues.getApiKeys().getTft().getApiKey())
                .build();
        Response response = client.newCall(request).execute();
        String jsonString = response.body().string();

        LeagueEntryDTO[] leagueEntryDTOs = new Gson().fromJson(jsonString, LeagueEntryDTO[].class);

        for (LeagueEntryDTO entry : leagueEntryDTOs) {
            if (entry.getQueueType().equals("RANKED_TFT")) {
                leagueEntryDTO = entry;
                break;
            }
        }

        return leagueEntryDTO;
    }

}
