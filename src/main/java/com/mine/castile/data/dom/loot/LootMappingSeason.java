package com.mine.castile.data.dom.loot;

import com.mine.castile.data.dom.enums.Season;

import java.util.List;

public class LootMappingSeason {
    private List<Season> seasons;
    private List<LootMappingAction> gather;
    private List<LootMappingAction> hits;

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public List<LootMappingAction> getGather() {
        return gather;
    }

    public void setGather(List<LootMappingAction> gather) {
        this.gather = gather;
    }

    public List<LootMappingAction> getHits() {
        return hits;
    }

    public void setHits(List<LootMappingAction> hits) {
        this.hits = hits;
    }
}
