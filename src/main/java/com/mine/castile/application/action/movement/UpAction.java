package com.mine.castile.application.action.movement;

import com.mine.castile.application.action.ActionCharacterAction;
import com.mine.castile.application.model.Model;
import com.mine.castile.presentation.registry.Direction;

import java.awt.*;

public class UpAction extends ActionCharacterAction {

    public UpAction(Model model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        proceedToDirection(Direction.UP);
    }

    @Override
    protected boolean isStepIntoPossible(Point point) {
        return point.y >= 0;
    }
}
