package com.mine.castile.action;

import com.mine.castile.dom.dto.loot.LootMappingActions;
import com.mine.castile.dom.dto.loot.LootMappingDropDto;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.model.IModel;
import com.mine.castile.persistence.MongoRepository;

import java.util.List;
import java.util.Map;

public class HitAction extends InteractAction {

    public HitAction(IModel model, MongoRepository repository) {
        super(model, repository);
    }

    @Override
    public GameObjectActionType getActionType() {
        return GameObjectActionType.hits;
    }

    @Override
    public Map<Integer, List<LootMappingDropDto>> getActions(LootMappingActions mappingDto) {
        return mappingDto.getHits();
    }

}
