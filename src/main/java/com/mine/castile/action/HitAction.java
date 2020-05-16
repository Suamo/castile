package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.registry.Direction;

import java.awt.*;

public class HitAction extends ActionImpl {

    public HitAction(IModel model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        Man man = model.getMan();
        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);

        System.out.println("Hitting the " + cell.get_id() + "...");

        model.getMan().setImageIndex(2);
    }

    @Override
    protected void translate(Point point) {

    }

    @Override
    protected boolean withinMap(Point point) {
        return point.y < model.getRows();
    }

}
