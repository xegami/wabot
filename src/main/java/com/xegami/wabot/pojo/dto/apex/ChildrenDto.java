package com.xegami.wabot.pojo.dto.apex;

public class ChildrenDto {

    private LegendMetadataDto metadata;
    private StatsDto[] stats;

    public LegendMetadataDto getMetadata() {
        return metadata;
    }

    public StatsDto[] getStats() {
        return stats;
    }
}
