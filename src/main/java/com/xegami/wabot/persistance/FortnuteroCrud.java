package com.xegami.wabot.persistance;

import com.xegami.wabot.pojo.fortnite.UserStats;
import com.xegami.wabot.pojo.nitrite.Fortnutero;
import com.xegami.wabot.pojo.nitrite.Today;
import com.xegami.wabot.utils.AppConstants;
import org.dizitart.no2.IndexOptions;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import javax.validation.constraints.NotNull;
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

    private void create(UserStats userStats) {
        repository.insert(new Fortnutero(
                userStats.getUsername(),
                userStats.getTotals().getWins(),
                userStats.getTotals().getKills(),
                userStats.getTotals().getMatchesplayed(),
                userStats.getPlatform()
        ));
    }

    public void update(UserStats userStats) {
        Fortnutero f = findByUsername(userStats.getUsername());

        if (f != null) {
            repository.update(new Fortnutero(
                    userStats.getUsername(),
                    userStats.getTotals().getWins(),
                    userStats.getTotals().getKills(),
                    userStats.getTotals().getMatchesplayed(),
                    userStats.getPlatform()
            ));
        } else {
            create(userStats);
        }
    }

    public void update(UserStats userStats, Integer wins, Integer kills, Integer matches) {
        Today t;
        Fortnutero f = findByUsername(userStats.getUsername());

        if (f != null) {
            Fortnutero newF = new Fortnutero(
                    userStats.getUsername(),
                    userStats.getTotals().getWins(),
                    userStats.getTotals().getKills(),
                    userStats.getTotals().getMatchesplayed(),
                    userStats.getPlatform()
            );

            if (f.getToday() == null) {
                t = new Today(wins, kills, matches);
            } else {
                t = new Today(f.getToday().getWins() + wins,
                        f.getToday().getKills() + kills,
                        f.getToday().getMatches() + matches
                );
            }

            newF.setToday(t);

            repository.update(newF);

        } else {
            create(userStats);
        }
    }

    public void resetToday() {
        Cursor<Fortnutero> cursor = repository.find();

        for (Fortnutero f : cursor) {
            f.setToday(new Today());
            repository.update(f);
        }
    }

    public List<Fortnutero> findAll() {
        return repository.find().toList();
    }

    public Fortnutero findByUsername(String username) {
        return repository.find(ObjectFilters.eq("username", username)).firstOrDefault();
    }

}
