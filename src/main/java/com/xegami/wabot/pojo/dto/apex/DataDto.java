package com.xegami.wabot.pojo.dto.apex;

public class DataDto {

    private PlayerMetadataDto metadata;
    private StatsDto[] stats;

    public PlayerMetadataDto getMetadata() {
        return metadata;
    }

    public StatsDto[] getStats() {
        return stats;
    }
}
