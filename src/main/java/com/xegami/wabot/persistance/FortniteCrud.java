package com.xegami.wabot.persistance;

import com.xegami.wabot.pojo.dto.fortnite.UserStatsDto;
import com.xegami.wabot.pojo.domain.fortnite.FortnitePlayer;
import com.xegami.wabot.pojo.domain.fortnite.Today;
import com.xegami.wabot.core.Constants;
import org.dizitart.no2.IndexOptions;
import org.dizitart.no2.IndexType;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.util.List;

public class FortniteCrud {

    private Nitrite db;
    private ObjectRepository<FortnitePlayer> repository;

    public FortniteCrud() {
        db = Nitrite.builder()
                .compressed()
                .filePath(Constants.FORTNITE_PLAYERS_DB_PATH)
                .openOrCreate();
        repository = db.getRepository(FortnitePlayer.class);

        if (!repository.hasIndex("username")) {
            repository.createIndex("username", IndexOptions.indexOptions(IndexType.Unique));
        }
    }

    private void create(UserStatsDto userStats) {
        repository.insert(new FortnitePlayer(
                userStats.getUsername(),
                userStats.getTotals().getWins(),
                userStats.getTotals().getKills(),
                userStats.getTotals().getMatchesplayed(),
                userStats.getPlatform()
        ));
    }

    public void update(UserStatsDto userStats) {
        FortnitePlayer f = findByUsername(userStats.getUsername());

        if (f != null) {
            repository.update(new FortnitePlayer(
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

    public void update(UserStatsDto userStats, Integer wins, Integer kills, Integer matches) {
        Today t;
        FortnitePlayer f = findByUsername(userStats.getUsername());

        if (f != null) {
            FortnitePlayer newF = new FortnitePlayer(
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
        Today t = new Today();
        Cursor<FortnitePlayer> cursor = repository.find();

        for (FortnitePlayer f : cursor) {
            f.setToday(t);
            repository.update(f);
        }
    }

    public List<FortnitePlayer> findAll() {
        return repository.find().toList();
    }

    public FortnitePlayer findByUsername(String username) {
        return repository.find(ObjectFilters.eq("username", username)).firstOrDefault();
    }

}
