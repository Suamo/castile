package com.mine.castile.io;

import com.mine.castile.dom.dto.GameObjectDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MapLoader {

    @Value("${game.init.map.rows}")
    private int rows;

    @Value("${game.init.map.columns}")
    private int columns;

    @Value("${game.init.castile.coordinates}")
    private String castileCoordinates;

    @Value("${game.init.castile.sixe.rows}")
    private int castileSizeRows;

    @Value("${game.init.castile.sixe.columns}")
    private int castileSizeColumns;

    private CellGenerator cellGenerator;

    public MapLoader(CellGenerator cellGenerator) {
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
        int x = Integer.parseInt(coordinates[0]) - 1;
        int y = Integer.parseInt(coordinates[1]) - 1;

        for (int row = y; row < y + castileSizeRows; row++) {
            for (int column = x; column < x + castileSizeColumns; column++) {
                cells[row][column] = cellGenerator.getWall();
            }
        }
        return cells;
    }
}
