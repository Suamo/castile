package com.mine.castile.application.model;

import com.mine.castile.application.dom.loot.LootMappingDropDto;
import com.mine.castile.presentation.registry.Direction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class Man {
    public static final int ENERRY_BASE = 50;

    private int column;
    private int row;
    private int imageIndex;
    private Direction direction;
    private ManStatus manStatus;

    private List<LootMappingDropDto> inventory = new ArrayList<>();

    public Man(@Value("${game.init.character.coordinates}") String characterCoordinates,
               ManStatus manStatus) {
        this.manStatus = manStatus;
        this.imageIndex = 2;
        this.direction = Direction.DOWN;
        setCoordinates(characterCoordinates);
    }

    private void setCoordinates(@Value("${game.init.character.coordinates}") String characterCoordinates) {
        String[] split = characterCoordinates.split(":");
        int[] coordinates = new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
        this.column = coordinates[1] - 1;
        this.row = coordinates[0] - 1;
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

    public Point getDirectionLocation() {
        switch (direction){
            case UP:
                return new Point(column, row - 1);
            case LEFT:
                return new Point(column - 1, row);
            case RIGHT:
                return new Point(column + 1, row);
            case DOWN:
                return new Point(column, row + 1);
            default:
                throw new UnsupportedOperationException(direction.getName() + " is not configured");
        }
    }

    public void setLocation(Point location) {
        column = location.x;
        row = location.y;
    }

    public List<LootMappingDropDto> getInventory() {
        return inventory;
    }

    public ManStatus getManStatus() {
        return manStatus;
    }

    public void spendEnergy(Integer energySpent) {

    }
}
