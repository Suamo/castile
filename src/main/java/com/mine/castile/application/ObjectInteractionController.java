package com.mine.castile.application;

import com.mine.castile.application.model.Man;
import com.mine.castile.application.model.ManStatus;
import com.mine.castile.application.model.Model;
import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.common.dom.loot.LootMappingActionsDto;
import com.mine.castile.common.dom.loot.LootMappingDropDto;
import com.mine.castile.common.events.ObjectInteractionEvent;
import com.mine.castile.data.dom.enums.GameObjectActionType;
import com.mine.castile.data.dom.objects.GameObjectAction;
import com.mine.castile.data.persistence.MongoRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class ObjectInteractionController {
    private Model model;
    private MongoRepository repository;

    public ObjectInteractionController(Model model, MongoRepository repository) {
        this.model = model;
        this.repository = repository;
    }

    @EventListener
    public void onEvent(ObjectInteractionEvent event) {
        GameObjectActionType actionType = getGameObjectActionType(event);
        interactWithObject(actionType);

        Man man = model.getMan();
        man.setImageIndex((man.getImageIndex() + 1) % 3);
        model.setMan(man);
    }

    private void interactWithObject(GameObjectActionType actionType) {
        Man man = model.getMan();

        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);

        GameObjectAction action = cell.getActions().get(actionType);
        if (action == null || action.getCount() == null || action.getCount() == 0) {
            System.out.println("Nothing to gather");
            return;
        }

        delayAction(action);

        Integer count = action.getCount();
        tryForLoot(actionType, cell, count);

        System.out.println(String.format("Changed %s count from %s to %s", actionType.name(), count, count - 1));
        action.setCount(--count);

        if (count == 0) {
            performTransformation(actionType);
        }

        ManStatus status = man.getManStatus();
        man.spendEnergy(action.getEnergyPerAction());
        int energy = status.getEnergy();
        if (energy <= 0) {
            status.setEnergy(Man.ENERRY_BASE);
            model.nextSeason();
        }
    }

    private void delayAction(GameObjectAction action) {
        try {
            Integer delayPerAction = action.getDelayPerAction();
            Thread.sleep(delayPerAction);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void performTransformation(GameObjectActionType actionType) {
        Man man = model.getMan();
        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);
        GameObjectAction action = cell.getActions().get(actionType);

        String transformsTo = action.getWhenNoActionTransformsTo();
        if (transformsTo == null) {
            return;
        }

        GameObjectDto newObject = repository.getObjectsCache()
                .get(model.getSeason())
                .get(transformsTo);

        model.set(directionLocation.x, directionLocation.y, new GameObjectDto(newObject));
    }

    private void tryForLoot(GameObjectActionType actionType, GameObjectDto cell, Integer count) {
        Map<String, LootMappingActionsDto> seasonMappings = repository.getLootMappingCache().get(model.getSeason());

        LootMappingActionsDto mappingDto = seasonMappings.get(cell.get_id());
        if (mappingDto == null) {
            return;
        }
        Map<Integer, java.util.List<LootMappingDropDto>> actions = getActions(mappingDto, actionType);
        java.util.List<LootMappingDropDto> possibleDrops = actions.get(count);

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

    private Map<Integer, List<LootMappingDropDto>> getActions(LootMappingActionsDto dto,
                                                              GameObjectActionType actionType) {
        switch (actionType) {
            case gather:
                return dto.getGather();
            case hits:
                return dto.getHits();
        }
        throw new UnsupportedOperationException();
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

    private GameObjectActionType getGameObjectActionType(ObjectInteractionEvent event) {
        switch (event.getObjectInteraction()) {
            case GATHER:
                return GameObjectActionType.gather;
            case HIT:
                return GameObjectActionType.hits;
            default:
                throw new UnsupportedOperationException();
        }
    }

}
