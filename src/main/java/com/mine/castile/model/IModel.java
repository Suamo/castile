package com.mine.castile.model;

import com.mine.castile.listener.IModelListener;
import com.mine.castile.registry.Cell;
import com.mine.castile.registry.Season;

import java.awt.*;

public interface IModel {
    Cell get(int row, int column) throws IndexOutOfBoundsException;
    void set(int row, int column, Cell cell) throws IndexOutOfBoundsException;

    int getRows();

    int getColumns();

    Rectangle getViewportRect();

    Man getMan();
    void setMan(Man man);

    void addModelListaner(IModelListener listener);

    Season getSeason();
    void nextSeason();
}
