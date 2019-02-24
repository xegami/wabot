package com.xegami.wabot.persistance;

import com.xegami.wabot.pojo.domain.apex.ApexPlayer;
import com.xegami.wabot.core.Constants;
import org.dizitart.no2.IndexOptions;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;

public class ApexCrud {

    private Nitrite db;
    private ObjectRepository<ApexPlayer> repository;

    public ApexCrud() {
        db = Nitrite.builder()
                .compressed()
                .filePath(Constants.APEX_PLAYERS_DB_PATH)
                .openOrCreate();
        repository = db.getRepository(ApexPlayer.class);

        if (!repository.hasIndex("username")) {
            repository.createIndex("username", IndexOptions.indexOptions(IndexType.Unique));
        }
    }

    private void create(ApexPlayer apexPlayer) {
        repository.insert(apexPlayer);
    }

    public void update(ApexPlayer apexPlayer) {
        ApexPlayer apexPlayerDb = findByUsername(apexPlayer.getUsername());

        if (apexPlayerDb != null) {
            repository.update(apexPlayer);
        } else {
            create(apexPlayer);
        }

    }

    public List<ApexPlayer> findAll() {
        return repository.find().toList();
    }

    public ApexPlayer findByUsername(String username) {
        return repository.find(ObjectFilters.eq("username", username)).firstOrDefault();
    }

}
