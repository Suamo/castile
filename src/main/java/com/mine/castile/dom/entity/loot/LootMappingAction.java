package com.mine.castile.dom.entity.loot;

import java.util.List;

public class LootMappingAction {
    private List<Integer> affectedActions;
    private List<LootMappingDrop> loot;

    public List<Integer> getAffectedActions() {
        return affectedActions;
    }

    public void setAffectedActions(List<Integer> affectedActions) {
        this.affectedActions = affectedActions;
    }

    public List<LootMappingDrop> getLoot() {
        return loot;
    }

    public void setLoot(List<LootMappingDrop> loot) {
        this.loot = loot;
    }
}
