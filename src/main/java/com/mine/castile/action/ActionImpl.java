package com.mine.castile.action;

import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.registry.Cell;
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
        translate(point);
        man.setDirection(getDirection());
        if (withinMap(point) && model.get(point.x, point.y).getHits() > 0) {
            man.setImageIndex((man.getImageIndex() + 1) % 2);
            man.setLocation(point);

            Cell cell = model.get(point.x, point.y);
            for (int hit = 0; hit < cell.getHits(); hit++) {
                int energyToDistract = cell.getEnergyPerHit();
                int energy = man.getEnergy();
                int energyLeft = energy - energyToDistract;
                if (energyLeft < 0) {
                    energyLeft = 0;
                }
                man.setEnergy(energyLeft);
                try {
                    Thread.sleep(cell.getDelayPerHit());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            Cell newCell = cell.getCellToReplace();
            if (newCell != null) {
                model.set(point.x, point.y, newCell);
                System.out.println("You've collected the " + cell.name());
            }
        } else {
            man.setImageIndex(2);
        }
        if (man.getEnergy() == 0) {
            man.setEnergy(Man.ENERRY_BASE);
            model.nextSeason();
        }
        model.setMan(man);
    }

    protected abstract Direction getDirection();

    protected abstract void translate(Point point);

    protected abstract boolean withinMap(Point point);
}
