package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.dto.loot.LootMappingActions;
import com.mine.castile.dom.dto.loot.LootMappingDropDto;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.persistence.MongoRepository;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class HitAction extends InteractAction {

    public HitAction(IModel model, MongoRepository repository) {
        super(model, repository);
    }

    @Override
    protected void performTransformation() {

        Man man = model.getMan();
        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);
        String evolutionToObject = cell.getEvolutionToObject();

        GameObjectDto newObject = repository.getObjectsCache().get(model.getSeason()).get(evolutionToObject);
        model.set(directionLocation.x, directionLocation.y, newObject);
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
