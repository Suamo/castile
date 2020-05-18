package com.mine.castile.model;

import com.mine.castile.Constants;
import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.enums.Season;
import com.mine.castile.listener.IModelListener;
import com.mine.castile.listener.ModelEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.event.EventListenerList;
import java.awt.*;

@Component
public class Model implements IModel {
    protected EventListenerList listenerList = new EventListenerList();

    private Maze maze;
    private Man man;
    private Season season;

    public Model(Maze maze,
                 @Value("${game.init.season}") Season initialSeason,
                 @Value("${game.init.character.coordinates}") String characterCoordinates) {
        this.maze = maze;
        this.season = initialSeason;
        addCharacter(characterCoordinates);
    }

    private void addCharacter(String characterCoordinates) {
        int[] coordinates = getCoordinates(characterCoordinates);
        man = new Man(coordinates[1] - 1, coordinates[0] - 1);
    }

    public Man getMan() {
        return man;
    }

    public void setMan(Man man) {
        this.man = man;
        fireModelChanged(new ModelEvent(this));
    }

    public Rectangle getViewportRect() {
        int x = man.getColumn() - Constants.VIEW_RADIX;
        int y = man.getRow() - Constants.VIEW_RADIX;
        int width = 2 * Constants.VIEW_RADIX + 1;
        int height = 2 * Constants.VIEW_RADIX + 1;
        return new Rectangle(x, y, width, height);
    }

    public GameObjectDto get(int row, int column) throws IndexOutOfBoundsException {
        return maze.get(row, column);
    }

    public void set(int row, int column, GameObjectDto dto) throws IndexOutOfBoundsException {
        maze.set(row, column, dto);
    }

    public int getRows() {
        return maze.getRows();
    }

    public int getColumns() {
        return maze.getColumns();
    }

    public void addModelListaner(IModelListener listener) {
        listenerList.add(IModelListener.class, listener);
    }

    protected void fireModelChanged(ModelEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == IModelListener.class) {
                ((IModelListener) listeners[i + 1]).modelChanged(e);
            }
        }
    }

    private int[] getCoordinates(String characterCoordinates) {
        String[] split = characterCoordinates.split(":");
        return new int[]{Integer.parseInt(split[0]), Integer.parseInt(split[1])};
    }

    @Override
    public Season getSeason() {
        return season;
    }

    @Override
    public void nextSeason() {
        this.season = this.season.getNextSeason();
        fireModelChanged(new ModelEvent(this));
        System.out.println("Its " + season.name() + " now!");
    }
}
