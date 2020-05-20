package com.mine.castile.application.model;

import com.mine.castile.application.io.MazeLoader;
import com.mine.castile.common.dom.GameObjectDto;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

@Component
public class Maze {
    public static final GameObjectDto VOID_OBJECT = GameObjectDto.createWithDefaults("void");

    private GameObjectDto[][] cells;

    public Maze(MazeLoader mazeLoader) {
        GameObjectDto[][] cells = mazeLoader.init();
        Validate.notNull(cells);
        Validate.isTrue(cells.length > 0);
        Validate.isTrue(cells[0].length > 0);
        Validate.noNullElements(cells);

        this.cells = cells;
    }

    public GameObjectDto get(int row, int column) {
        if (row < 0 || column < 0 || row >= cells[0].length || column >= cells.length) {
            return VOID_OBJECT;
        }
        return cells[column][row];
    }

    public void set(int row, int column, GameObjectDto cell) {
        cells[column][row] = cell;
    }

    public int getRows() {
        return cells.length;
    }

    public int getColumns() {
        return cells[0].length;
    }
}
