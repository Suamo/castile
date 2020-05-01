package com.mine.castile.io;

import com.mine.castile.registry.Cell;

public class MapLoader {

    public Cell[][] load() {
        int rows = 20;
        int columns = 30;
        Cell[][] cells = new Cell[rows][columns];
        for (int row = 0; row < columns; row++) {
            for (int column = 0; column < rows; column++) {
                cells[column][row] = CellGenerator.generateCell();
            }
        }
        cells[0][columns / 2 - 5] = Cell.ENTER;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                cells[j][columns / 2 - 2 + i] = Cell.WALL;
            }
        }
        return cells;
    }
}
