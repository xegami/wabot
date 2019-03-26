package com.xegami.wabot.pojo.dto.apex;

public class DataDto {

    private PlayerMetadataDto metadata;
    private StatsDto[] stats;
    private ChildrenDto[] children;

    public PlayerMetadataDto getMetadata() {
        return metadata;
    }

    public StatsDto[] getStats() {
        return stats;
    }

    public ChildrenDto[] getChildren() {
        return children;
    }
}
