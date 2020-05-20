package com.mine.castile.application.action.movement;

import com.mine.castile.application.action.ActionCharacterAction;
import com.mine.castile.application.model.Model;
import com.mine.castile.presentation.registry.Direction;

import java.awt.*;

public class DownAction extends ActionCharacterAction {

    public DownAction(Model model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        proceedToDirection(Direction.DOWN);
    }

    @Override
    protected boolean isStepIntoPossible(Point point) {
        return point.y < model.getRows();
    }

}
