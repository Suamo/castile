package com.mine.castile.application.action.movement;

import com.mine.castile.application.action.ActionCharacterAction;
import com.mine.castile.application.model.Model;
import com.mine.castile.presentation.registry.Direction;

import java.awt.*;

public class LeftAction extends ActionCharacterAction {

    public LeftAction(Model model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        proceedToDirection(Direction.LEFT);
    }

    @Override
    protected boolean isStepIntoPossible(Point point) {
        return point.x >= 0;
    }

}
