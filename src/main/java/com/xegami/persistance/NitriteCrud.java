package com.xegami.persistance;

public class NitriteCrud {

    /*
    private Nitrite db;
    private ObjectRepository<Player> repository;

    public NitriteCrud() {
        db = Nitrite.builder()
                .compressed()
                .filePath("C:/Desarrollo/nitrite/fortnut.db")
                .openOrCreate();
        repository = db.getRepository(Player.class);
    }

    public void createEntries() {
        String[] players = new String[]{
                "Xegami@pc",
                "OH SoradeKun@pc",
                "Fuchiniano@pc",
                "Hydroxide Cherot@pc",
                "KingAbdeel@pc",
                "PedroP_monkaaaaS@pc",
                "Hydroxide Poison@pc"
        };

        if (!repository.hasIndex("epicNickname")) {
            repository.createIndex("epicNickname", IndexOptions.indexOptions(IndexType.Unique));
        }

        if (repository.size() == 0) {
            for (String p : players) {
                repository.insert(new Player(p.split("@")[0], p.split("@")[1]));
            }
        }
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        Cursor<Player> cursor = repository.find();

        for (Player p : cursor) {
            players.add(p);
        }

        return players;
    }
    */
}
