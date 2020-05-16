package com.mine.castile.model;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.enums.Season;
import com.mine.castile.listener.IModelListener;

import java.awt.*;

public interface IModel {
    GameObjectDto get(int row, int column) throws IndexOutOfBoundsException;

    void set(int row, int column, GameObjectDto cell) throws IndexOutOfBoundsException;

    int getRows();

    int getColumns();

    Rectangle getViewportRect();

    Man getMan();

    void setMan(Man man);

    void addModelListaner(IModelListener listener);

    Season getSeason();

    void nextSeason();
}
