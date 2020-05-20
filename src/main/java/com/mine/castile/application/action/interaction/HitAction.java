package com.mine.castile.application.action.interaction;

import com.mine.castile.application.dom.loot.LootMappingActionsDto;
import com.mine.castile.application.dom.loot.LootMappingDropDto;
import com.mine.castile.application.model.Model;
import com.mine.castile.data.dom.enums.GameObjectActionType;
import com.mine.castile.data.persistence.MongoRepository;

import java.util.List;
import java.util.Map;

public class HitAction extends InteractAction {

    public HitAction(Model model, MongoRepository repository) {
        super(model, repository);
    }

    @Override
    public GameObjectActionType getActionType() {
        return GameObjectActionType.hits;
    }

    @Override
    public Map<Integer, List<LootMappingDropDto>> getActions(LootMappingActionsDto mappingDto) {
        return mappingDto.getHits();
    }

}
