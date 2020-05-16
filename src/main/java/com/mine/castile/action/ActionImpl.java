package com.mine.castile.action;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.registry.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public abstract class ActionImpl extends AbstractAction {

    protected IModel model;

    public ActionImpl(IModel model) {
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Man man = model.getMan();
        Point point = man.getLocation();
        translate(point); // moves character to new position
        man.setDirection(getDirection());

        GameObjectDto cell = model.get(point.x, point.y);

        if (withinMap(point) && cell != null && !cell.isBlocking()) {
            interactWithObject(cell);
            man.setImageIndex((man.getImageIndex() + 1) % 2);
            man.setLocation(point);
            model.set(point.x, point.y, cell);
            System.out.println("You've collected the " + cell.get_id());
            man.setImageIndex(2);
        }

        if (man.getEnergy() == 0) {
            man.setEnergy(Man.ENERRY_BASE);
            model.nextSeason();
        }
        model.setMan(man);
    }

    protected abstract void interactWithObject(GameObjectDto cell);

    protected abstract Direction getDirection();

    protected abstract void translate(Point point);

    protected abstract boolean withinMap(Point point);
}
