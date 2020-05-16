package com.mine.castile.action.movement;

import com.mine.castile.action.ActionImpl;
import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.model.IModel;
import com.mine.castile.registry.Direction;

import java.awt.*;

public class UpAction extends ActionImpl {

    public UpAction(IModel model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        proceedToDirection(Direction.UP);
    }

    @Override
    protected void translate(Point point) {
        point.translate(0, -1);
    }

    @Override
    protected boolean withinMap(Point point) {
        return point.y >= 0;
    }
}
