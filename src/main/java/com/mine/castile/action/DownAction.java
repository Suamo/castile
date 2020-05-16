package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.model.IModel;
import com.mine.castile.registry.Direction;

import java.awt.*;

public class DownAction extends ActionImpl {

    public DownAction(IModel model) {
        super(model);
    }

    @Override
    protected void interactWithObject(GameObjectDto cell) {

    }

    @Override
    protected Direction getDirection() {
        return Direction.DOWN;
    }

    @Override
    protected void translate(Point point) {
        point.translate(0, 1);
    }

    @Override
    protected boolean withinMap(Point point) {
        return point.y < model.getRows();
    }

}
