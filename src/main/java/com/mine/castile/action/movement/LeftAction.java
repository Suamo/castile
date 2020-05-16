package com.mine.castile.action.movement;

import com.mine.castile.action.ActionImpl;
import com.mine.castile.model.IModel;
import com.mine.castile.registry.Direction;

import java.awt.*;

public class LeftAction extends ActionImpl {

    public LeftAction(IModel model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        proceedToDirection(Direction.LEFT);
    }

    @Override
    protected boolean withinMap(Point point) {
        return point.x >= 0;
    }

}
