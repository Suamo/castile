package com.mine.castile.application.model;

import com.mine.castile.application.dom.GameObjectDto;
import com.mine.castile.application.listener.IModelListener;
import com.mine.castile.application.listener.ModelEvent;
import com.mine.castile.data.dom.enums.Season;
import com.mine.castile.presentation.PresentationConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.event.EventListenerList;
import java.awt.*;

@Component
public class Model {
    protected EventListenerList listenerList = new EventListenerList();

    private Maze maze;
    private Man man;
    private Season season;

    public Model(Maze maze, Man man,
                 @Value("${game.init.season}") Season initialSeason) {
        this.maze = maze;
        this.man = man;
        this.season = initialSeason;
    }

    public Man getMan() {
        return man;
    }

    public void setMan(Man man) {
        this.man = man;
        modelUpdated();
    }

    public void modelUpdated() {
        fireModelChanged(new ModelEvent(this));
    }

    public Rectangle getViewportRect() {
        int x = man.getColumn() - PresentationConstants.VIEW_RADIX;
        int y = man.getRow() - PresentationConstants.VIEW_RADIX;
        int width = 2 * PresentationConstants.VIEW_RADIX + 1;
        int height = 2 * PresentationConstants.VIEW_RADIX + 1;
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

    public Season getSeason() {
        return season;
    }

    public void nextSeason() {
        this.season = this.season.getNextSeason();
        modelUpdated();
        System.out.println("Its " + season.name() + " now!");
    }
}
