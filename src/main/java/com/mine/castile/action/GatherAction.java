package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;

import java.awt.*;

public class GatherAction extends ActionImpl {

    public GatherAction(IModel model) {
        super(model);
    }

    @Override
    protected void interactWithObject() {
        Man man = model.getMan();
        Point directionLocation = man.getDirectionLocation();
        GameObjectDto cell = model.get(directionLocation.x, directionLocation.y);

        System.out.println("Gathering the " + cell.get_id() + "...");

        model.getMan().setImageIndex(2);
    }

    @Override
    protected boolean withinMap(Point point) {
        return point.y < model.getRows();
    }

}
