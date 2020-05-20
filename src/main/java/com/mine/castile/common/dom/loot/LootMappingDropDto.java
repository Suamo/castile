package com.mine.castile.common.dom.loot;

import com.mine.castile.data.dom.loot.LootMappingDrop;

public class LootMappingDropDto {

    private String id;
    private int maxCount;
    private int chance;

    public LootMappingDropDto(LootMappingDrop entityDrop) {
        this.id = entityDrop.getId();
        this.maxCount = entityDrop.getMaxCount();
        this.chance = entityDrop.getChance();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
