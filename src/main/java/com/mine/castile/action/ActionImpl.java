package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.entity.GameObjectAction;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.registry.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class ActionImpl extends AbstractAction {

    protected final IModel model;

    public ActionImpl(IModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Man man = model.getMan();
        interactWithObject();

        if (man.getEnergy() == 0) {
            man.setEnergy(Man.ENERRY_BASE);
            model.nextSeason();
        }
        model.setMan(man);
    }

    protected void proceedToDirection(Direction direction) {
        Direction oldDirection = model.getMan().getDirection();
        model.getMan().setDirection(direction);
        if (oldDirection != direction) {
            return; // just change direction without movement
        }

        Man man = model.getMan();
        Point point = man.getDirectionLocation();
        GameObjectDto cell = model.get(point.x, point.y);

        if (withinMap(point) && cell != null && !cell.isBlocking()) {
            man.setImageIndex((man.getImageIndex() + 1) % 2);
            man.setLocation(point);

            try {
                GameObjectAction action = cell.getActions().get(GameObjectActionType.stepInto);
                Integer delayPerAction = action.getDelayPerAction();
                Thread.sleep(delayPerAction);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    protected abstract void interactWithObject();

    protected abstract boolean withinMap(Point point);
}
