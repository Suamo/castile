package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.entity.objects.GameObjectAction;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.persistence.MongoRepository;

import java.awt.*;

public class HitAction extends ActionImpl {

    private MongoRepository repository;

    public HitAction(IModel model, MongoRepository repository) {
        super(model);
        this.repository = repository;
    }

    @Override
    protected void interactWithObject() {
        Man man = model.getMan();
        man.setImageIndex(2);

        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);

        System.out.println("Hitting the " + cell.get_id() + "...");
        GameObjectAction action = cell.getActions().get(GameObjectActionType.hits);
        if (action == null || action.getCount() == null || action.getCount() == 0) {
            System.out.println("Nothing to gather");
            return;
        }

        delayAction(action);

        Integer count = action.getCount();
        System.out.println("Changed hits count from " + count + " to " + --count);
        action.setCount(count);

        System.out.println("Found some loot (TBD)"); // todo: implement loot

        man.reduceEnergy(action.getEnergyPerAction());
    }

    @Override
    protected boolean isStepIntoPossible(Point point) {
        return false;
    }

}
