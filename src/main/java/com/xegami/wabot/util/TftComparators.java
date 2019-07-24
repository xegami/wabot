package com.xegami.wabot.util;


import com.xegami.wabot.pojo.domain.tft.TftPlayer;
import com.xegami.wabot.util.enums.Ranks;
import com.xegami.wabot.util.enums.Tiers;

import java.util.Comparator;

public class TftComparators {

    private TftComparators() {

    }

    public static Comparator<? super TftPlayer> byTierRankWinrate() {
        return new Comparator<TftPlayer>() {
            @Override
            public int compare(TftPlayer o1, TftPlayer o2) {
                if (Tiers.valueOf(o1.getTier()).ordinal() > Tiers.valueOf(o2.getTier()).ordinal()) {
                    return -1;
                }

                if (Tiers.valueOf(o1.getTier()).ordinal() < Tiers.valueOf(o2.getTier()).ordinal()) {
                    return 1;
                }

                if (Ranks.valueOf(o1.getRank()).ordinal() > Ranks.valueOf(o2.getRank()).ordinal()) {
                    return -1;
                }

                if (Ranks.valueOf(o1.getRank()).ordinal() < Ranks.valueOf(o2.getRank()).ordinal()) {
                    return 1;
                }

                if (o1.getWins() / o1.getLosses() > o2.getWins() / o2.getLosses()) {
                    return -1;
                }

                if (o1.getWins() / o1.getLosses() < o2.getWins() / o2.getLosses()) {
                    return 1;
                }

                return 0;
            }
        };
    }

}
