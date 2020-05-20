package com.mine.castile.application.action;

import com.mine.castile.application.model.Man;
import com.mine.castile.application.model.ManStatus;
import com.mine.castile.application.model.Model;
import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.data.dom.enums.GameObjectActionType;
import com.mine.castile.data.dom.objects.GameObjectAction;
import com.mine.castile.presentation.registry.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class ActionCharacterAction extends AbstractAction {

    protected final Model model;

    public ActionCharacterAction(Model model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Man man = model.getMan();
        interactWithObject();

        man.setImageIndex((man.getImageIndex() + 1) % 3);
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

        if (isStepIntoPossible(point) && !cell.isBlocking()) {
            man.setLocation(point);

            GameObjectAction action = cell.getActions().get(GameObjectActionType.stepInto);
            delayAction(action);

            man.spendEnergy(action.getEnergyPerAction());
            ManStatus manStatus = man.getManStatus();
            int energy = manStatus.getEnergy();
            if (energy <= 0) {
                manStatus.setEnergy(Man.ENERRY_BASE);
                model.nextSeason();
            }
        }
    }

    protected void delayAction(GameObjectAction action) {
        try {
            Integer delayPerAction = action.getDelayPerAction();
            Thread.sleep(delayPerAction);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    protected abstract void interactWithObject();

    protected abstract boolean isStepIntoPossible(Point point);
}