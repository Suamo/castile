package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.dto.loot.LootMappingActions;
import com.mine.castile.dom.dto.loot.LootMappingDropDto;
import com.mine.castile.dom.entity.objects.GameObjectAction;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.model.ManStatus;
import com.mine.castile.persistence.MongoRepository;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class InteractAction extends ActionImpl {

    protected MongoRepository repository;

    public InteractAction(IModel model, MongoRepository repository) {
        super(model);
        this.repository = repository;
    }

    @Override
    protected void interactWithObject() {
        Man man = model.getMan();

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

        if (count == 0) {
            performTransformation();
        }

        ManStatus status = man.getManStatus();
        man.spendEnergy(action.getEnergyPerAction());
        int energy = status.getEnergy();
        if (energy <= 0) {
            status.setEnergy(Man.ENERRY_BASE);
            model.nextSeason();
        }
    }

    private void performTransformation() {
        Man man = model.getMan();
        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);
        GameObjectAction action = cell.getActions().get(getActionType());

        String transformsTo = action.getWhenNoActionTransformsTo();
        if (transformsTo == null) {
            return;
        }

        GameObjectDto newObject = repository.getObjectsCache()
                .get(model.getSeason())
                .get(transformsTo);

        model.set(directionLocation.x, directionLocation.y, new GameObjectDto(newObject));
    }

    private void tryForLoot(GameObjectDto cell, Integer count) {
        Map<String, LootMappingActions> seasonMappings = repository.getLootMappingCache().get(model.getSeason());

        LootMappingActions mappingDto = seasonMappings.get(cell.get_id());
        if (mappingDto == null) {
            return;
        }
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
