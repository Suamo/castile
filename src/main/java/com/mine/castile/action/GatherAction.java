package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.entity.GameObjectAction;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;

import java.awt.*;

public class GatherAction extends ActionImpl {

    public GatherAction(IModel model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        Man man = model.getMan();
        man.setImageIndex(2);

        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);

        System.out.println("Gathering the " + cell.get_id() + "...");
        GameObjectAction action = cell.getActions().get(GameObjectActionType.gather);
        if (action == null || action.getCount() == 0) {
            System.out.println("Nothing to gather");
            return;
        }

        delayAction(action);

        Integer count = action.getCount();
        System.out.println("Changed gathering count from " + count + " to " + --count);
        action.setCount(count);

        System.out.println("Found some loot (TBD)"); // todo: implement loot

        man.reduceEnerty(action.getEnergyPerAction());
    }

    @Override
    protected boolean isStepIntoPossible(Point point) {
        return false;
    }

}
