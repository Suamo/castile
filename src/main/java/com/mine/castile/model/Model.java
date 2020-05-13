package com.mine.castile.model;

import com.mine.castile.Constants;
import com.mine.castile.dom.enums.Season;
import com.mine.castile.listener.IModelListener;
import com.mine.castile.listener.ModelEvent;
import com.mine.castile.registry.Cell;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

import javax.swing.event.EventListenerList;
import java.awt.*;

@Component
public class Model implements IModel {
    protected EventListenerList listenerList = new EventListenerList();

    private Maze maze;
    private Man man;
    private Point enterLocation;
    private Season season;

    public Model(Maze maze) {
        this.maze = maze;
        season = Season.summer;
        setup();
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

    public Cell get(int row, int column) throws IndexOutOfBoundsException {
        return maze.get(row, column);
    }

    public void set(int row, int column, Cell cell) throws IndexOutOfBoundsException {
        maze.set(row, column, cell);
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

    public void removeModelListener(IModelListener listener) {
        listenerList.remove(IModelListener.class, listener);
    }

    protected void fireModelChanged(ModelEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == IModelListener.class) {
                ((IModelListener) listeners[i + 1]).modelChanged(e);
            }
        }
    }

    private void setup() {
        int rows = maze.getRows();
        int columns = maze.getColumns();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Cell cell = maze.get(column, row);
                if (Cell.ENTER == cell) {
                    enterLocation = new Point(column, row);
                    man = new Man(column, row);
                }
            }
        }
        Validate.notNull(enterLocation, "Maze without ENTER");
    }

    @Override
    public Season getSeason() {
        return season;
    }

    @Override
    public void nextSeason() {
        this.season = this.season.getNextSeason();

        int rows = maze.getRows();
        int columns = maze.getColumns();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Cell cell = maze.get(column, row);
                if (Cell.ENTER == cell) {
                    enterLocation = new Point(column, row);
                    man = new Man(column, row);
                }
            }
        }
        fireModelChanged(new ModelEvent(this));
        System.out.println("Its " + season.name() + " now!");
    }
}
