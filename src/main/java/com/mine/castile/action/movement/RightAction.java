package com.mine.castile.action.movement;

import com.mine.castile.action.ActionImpl;
import com.mine.castile.model.IModel;
import com.mine.castile.registry.Direction;

import java.awt.*;

public class RightAction extends ActionImpl {

    public RightAction(IModel model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        proceedToDirection(Direction.RIGHT);
    }

    @Override
    protected boolean withinMap(Point point) {
        return point.x < model.getColumns();
    }
}
