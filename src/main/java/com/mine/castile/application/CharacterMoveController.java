package com.mine.castile.application;

import com.mine.castile.application.model.Man;
import com.mine.castile.application.model.ManStatus;
import com.mine.castile.application.model.Model;
import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.common.enums.Direction;
import com.mine.castile.common.events.CharacterMoveEvent;
import com.mine.castile.data.dom.enums.GameObjectActionType;
import com.mine.castile.data.dom.objects.GameObjectAction;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.awt.*;

@Controller
public class CharacterMoveController {

    private Model model;

    public CharacterMoveController(Model model) {
        this.model = model;
    }

    @EventListener
    public void onEvent(CharacterMoveEvent event) {
        Man man = model.getMan();

        man.setImageIndex((man.getImageIndex() + 1) % 3);

        Point point = man.getDirectionLocation();
        boolean isStepIntoPossible = isStepIntoPossible(event, point);
        proceedToDirection(event.getDirection(), isStepIntoPossible);

        model.setMan(man);
    }

    private void proceedToDirection(Direction direction, boolean isStepIntoPossible) {
        Direction oldDirection = model.getMan().getDirection();
        model.getMan().setDirection(direction);
        if (oldDirection != direction) {
            return; // just change direction without movement
        }

        Man man = model.getMan();
        Point point = man.getDirectionLocation();
        GameObjectDto cell = model.get(point.x, point.y);

        if (isStepIntoPossible && !cell.isBlocking()) {
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

    private void delayAction(GameObjectAction action) {
        try {
            Integer delayPerAction = action.getDelayPerAction();
            Thread.sleep(delayPerAction);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isStepIntoPossible(CharacterMoveEvent event, Point point) {
        switch (event.getDirection()) {
            case UP:
                return point.y >= 0;
            case LEFT:
                return point.x >= 0;
            case RIGHT:
                return point.x < model.getColumns();
            case DOWN:
                return point.y < model.getRows();
        }
        return false;
    }

}
