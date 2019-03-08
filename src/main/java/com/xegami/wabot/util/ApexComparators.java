package com.xegami.wabot.util;

import com.xegami.wabot.pojo.domain.apex.ApexPlayer;

import java.util.Comparator;

public class ApexComparators {

    private ApexComparators() {

    }

    public static Comparator<? super ApexPlayer> byKillsDescendant() {
        return new Comparator<ApexPlayer>() {
            @Override
            public int compare(ApexPlayer o1, ApexPlayer o2) {
                if (o1.getKills() > o2.getKills()) {
                    return -1;
                }
                if (o1.getKills() < o2.getKills()) {
                    return 1;
                }
                return 0;
            }
        };
    }

    public static Comparator<? super ApexPlayer> byTodayKillsDescendant() {
        return new Comparator<ApexPlayer>() {
            @Override
            public int compare(ApexPlayer o1, ApexPlayer o2) {
                int o1TodayKills = o1.getKills() - o1.getStartingKills();
                int o2TodayKills = o2.getKills() - o2.getStartingKills();

                if (o1TodayKills > o2TodayKills) {
                    return -1;
                }
                if (o1TodayKills < o2TodayKills) {
                    return 1;
                }
                return 0;
            }
        };
    }
}
