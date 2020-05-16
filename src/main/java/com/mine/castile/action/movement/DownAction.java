package com.mine.castile.action.movement;

import com.mine.castile.action.ActionImpl;
import com.mine.castile.model.IModel;
import com.mine.castile.registry.Direction;

import java.awt.*;

public class DownAction extends ActionImpl {

    public DownAction(IModel model) {
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
