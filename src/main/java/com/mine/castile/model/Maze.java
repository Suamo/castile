package com.mine.castile.model;

import com.mine.castile.io.MapLoader;
import org.apache.commons.lang.Validate;
import com.mine.castile.registry.Cell;
import org.springframework.stereotype.Component;

@Component
public class Maze {
    private Cell[][] cells;

    public Maze() {
        Cell[][] cells = new MapLoader().load();
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
