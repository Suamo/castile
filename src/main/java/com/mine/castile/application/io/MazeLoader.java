package com.mine.castile.application.io;

import com.mine.castile.common.dom.GameObjectDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MazeLoader {

    @Value("${game.init.map.rows}")
    private int rows;

    @Value("${game.init.map.columns}")
    private int columns;

    @Value("${game.init.castile.coordinates}")
    private String castileCoordinates;

    @Value("${game.init.castile.size.rows}")
    private int castileSizeRows;

    @Value("${game.init.castile.size.columns}")
    private int castileSizeColumns;

    private CellGenerator cellGenerator;

    public MazeLoader(CellGenerator cellGenerator) {
        this.cellGenerator = cellGenerator;
    }

    public GameObjectDto[][] init() {
        GameObjectDto[][] cells = new GameObjectDto[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                cells[row][column] = cellGenerator.generateCell();
            }
        }
        String[] coordinates = castileCoordinates.split(":");
        int y = Integer.parseInt(coordinates[0]) - 1;
        int x = Integer.parseInt(coordinates[1]) - 1;

        for (int row = y; row < y + castileSizeRows; row++) {
            for (int column = x; column < x + castileSizeColumns; column++) {
                cells[row][column] = cellGenerator.getWall();
            }
        }
        return cells;
    }
}
