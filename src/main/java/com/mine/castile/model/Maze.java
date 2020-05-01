package com.mine.castile.model;

import org.apache.commons.lang.Validate;
import com.mine.castile.registry.Cell;

/**
 * Maze<p />
 * Change log:<br />
 * 15.10.2009 : created by o.korneychuk
 */
public class Maze {
    private Cell[][] cells;

    public Maze(Cell[][] cells) {
        Validate.notNull(cells);
        Validate.isTrue(cells.length > 0);
        Validate.isTrue(cells[0].length > 0);
        Validate.noNullElements(cells);

        this.cells = cells;
    }

    public Cell get(int row, int column) {
        return cells[column][row];
    }

    public void set(int row, int column, Cell cell) {
        cells[column][row] = cell;
    }

    public int getRows() {
        return cells.length;
    }

    public int getColumns() {
        return cells[0].length;
    }
}
