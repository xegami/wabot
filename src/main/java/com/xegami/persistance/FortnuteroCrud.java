package com.xegami.persistance;

import com.xegami.Utils.AppConstants;
import com.xegami.pojo.fortnite.UserStats;
import com.xegami.pojo.nitrite.Fortnutero;
import org.dizitart.no2.IndexOptions;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;

import java.util.List;

public class FortnuteroCrud {

    private Nitrite db;
    private ObjectRepository<Fortnutero> repository;

    public FortnuteroCrud() {
        db = Nitrite.builder()
                .compressed()
                .filePath(AppConstants.FORTNUT_DB_PATH)
                .openOrCreate();
        repository = db.getRepository(Fortnutero.class);

        if (!repository.hasIndex("username")) {
            repository.createIndex("username", IndexOptions.indexOptions(IndexType.Unique));
        }
    }

    public void create(UserStats userStats) {
        repository.insert(new Fortnutero(userStats.getUsername(),
                userStats.getTotals().getWins(),
                userStats.getTotals().getKills(),
                userStats.getTotals().getMatchesplayed(),
                userStats.getPlatform()));
    }

    public void update(UserStats userStats) {
        boolean inRepository = false;
        Cursor<Fortnutero> cursor = repository.find();

        for (Fortnutero f : cursor) {
            if (f.getUsername().equals(userStats.getUsername())) {
                inRepository = true;
            }
        }

        if (inRepository) {
            repository.update(new Fortnutero(userStats.getUsername(),
                    userStats.getTotals().getWins(),
                    userStats.getTotals().getKills(),
                    userStats.getTotals().getMatchesplayed(),
                    userStats.getPlatform()));
        } else {
            create(userStats);
        }
    }

    public List<Fortnutero> findAll() {
        return repository.find().toList();
    }

    public Fortnutero findByUsername(String username) {
        Cursor<Fortnutero> cursor = repository.find();

        for (Fortnutero f : cursor) {
            if (f.getUsername().equals(username)) {
                return f;
            }
        }

        return null;
    }

}
