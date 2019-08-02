package com.xegami.wabot.persistance;

import com.xegami.wabot.core.Constants;
import com.xegami.wabot.pojo.domain.tft.TftPlayer;
import org.dizitart.no2.IndexOptions;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;

public class TftRepository {

    private ObjectRepository<TftPlayer> repository;

    public TftRepository() {
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath(Constants.TFT_PLAYERS_DB_PATH)
                .openOrCreate();
        repository = db.getRepository(TftPlayer.class);

        if (!repository.hasIndex("summonerName")) {
            repository.createIndex("summonerName", IndexOptions.indexOptions(IndexType.Unique));
        }
    }

    private void create(TftPlayer tftPlayer) {
        repository.insert(tftPlayer);
    }

    public void update(TftPlayer tftPlayer) {
        TftPlayer tftPlayerDDBB = findBySummonerName(tftPlayer.getSummonerName());

        if (tftPlayerDDBB != null) {
            repository.update(tftPlayer);
        } else {
            create(tftPlayer);
        }

    }

    public List<TftPlayer> findAll() {
        return repository.find().toList();
    }

    private TftPlayer findBySummonerName(String summonerName) {
        return repository.find(ObjectFilters.eq("summonerName", summonerName)).firstOrDefault();
    }

    public void clean() {
        repository.remove(ObjectFilters.ALL);
    }

}
