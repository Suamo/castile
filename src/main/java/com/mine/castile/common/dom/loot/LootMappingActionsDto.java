package com.mine.castile.common.dom.loot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootMappingActionsDto {
    private Map<Integer, List<LootMappingDropDto>> gather = new HashMap<>();
    private Map<Integer, List<LootMappingDropDto>> hits = new HashMap<>();

    public Map<Integer, List<LootMappingDropDto>> getGather() {
        return gather;
    }

    public Map<Integer, List<LootMappingDropDto>> getHits() {
        return hits;
    }
}
