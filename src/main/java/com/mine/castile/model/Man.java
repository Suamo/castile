package com.mine.castile.model;

import com.mine.castile.registry.Direction;

import java.awt.*;

public class Man {
    public static final int ENERRY_BASE = 50;
    private int column;
    private int row;
    private int imageIndex;
    private Direction direction;
    private int energy;

    public Man(int column, int row) {
        this.column = column;
        this.row = row;
        imageIndex = 2;
        direction = Direction.DOWN;
        energy = ENERRY_BASE;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getLocation() {
        return new Point(column, row);
    }

    public void setLocation(Point location) {
        column = location.x;
        row = location.y;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        System.out.println("ENergy now is " + energy);
        this.energy = energy;
    }
}
