package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.model.IModel;
import com.mine.castile.registry.Direction;

import java.awt.*;

public class RightAction extends ActionImpl {

    public RightAction(IModel model) {
        super(model);
    }

    @Override
    protected void interactWithObject(GameObjectDto cell) {

    }

    @Override
    protected Direction getDirection() {
        return Direction.RIGHT;
    }

    @Override
    protected void translate(Point point) {
        point.translate(1, 0);
    }

    @Override
    protected boolean withinMap(Point point) {
        return point.x < model.getColumns();
    }
}
