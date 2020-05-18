package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.dto.loot.LootMappingActions;
import com.mine.castile.dom.dto.loot.LootMappingDropDto;
import com.mine.castile.dom.entity.objects.GameObjectAction;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.persistence.MongoRepository;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class InteractAction extends ActionImpl {

    private MongoRepository repository;

    public InteractAction(IModel model, MongoRepository repository) {
        super(model);
        this.repository = repository;
    }

    @Override
    protected void interactWithObject() {
        Man man = model.getMan();
        man.setImageIndex(2);

        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);

        GameObjectAction action = cell.getActions().get(getActionType());
        if (action == null || action.getCount() == null || action.getCount() == 0) {
            System.out.println("Nothing to gather");
            return;
        }

        delayAction(action);

        Integer count = action.getCount();
        tryForLoot(cell, count);

        System.out.println("Changed " + getActionType().name() +
                " count from " + count + " to " + --count);
        action.setCount(count);

        man.reduceEnergy(action.getEnergyPerAction());
    }

    private void tryForLoot(GameObjectDto cell, Integer count) {
        Map<String, LootMappingActions> seasonMappings = repository.getLootMappingCache().get(model.getSeason());

        LootMappingActions mappingDto = seasonMappings.get(cell.get_id());
        Map<Integer, java.util.List<LootMappingDropDto>> actions = getActions(mappingDto);
        List<LootMappingDropDto> possibleDrops = actions.get(count);

        for (LootMappingDropDto possibleDrop : possibleDrops) {
            if (hadChance(possibleDrop.getChance())) {
                int maxCount = possibleDrop.getMaxCount();
                int rolled = roll(1, maxCount);
                for (int i = 0; i < rolled; i++) {
                    System.out.println("A " + possibleDrop.getId() + " has been found!");
                    model.getMan().getInventory().add(possibleDrop);
                }
            }
        }
    }

    private boolean hadChance(int chance) {
        if (chance == 0) {
            return false;
        }
        int min = 1;
        int max = 100;
        int roll = roll(min, max);
        return (100 - chance) <= roll;
    }

    private int roll(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    @Override
    protected boolean isStepIntoPossible(Point point) {
        return false;
    }

    public abstract Map<Integer, List<LootMappingDropDto>> getActions(LootMappingActions mappingDto);

    public abstract GameObjectActionType getActionType();
}
