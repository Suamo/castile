package com.mine.castile.io;

import com.mine.castile.registry.Cell;

import java.util.Random;

public class CellGenerator {

    public static Cell generateCell() {

        int randomInt = getRandomInt(0, 100);

        int pct = 0;
        for (Cell cell : Cell.values()) {
            pct = pct + cell.getChance();
            if (randomInt <= pct) {
                return cell;
            }
        }
        return Cell.PLAIN;
    }

    private static int getRandomInt(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
