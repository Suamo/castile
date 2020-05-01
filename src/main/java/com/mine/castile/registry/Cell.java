package com.mine.castile.registry;

import java.util.HashMap;
import java.util.Map;

public enum Cell {
    PLAIN(1, 1, 100, 70, "grass"),

    STREAM(1, 2, 500, 5, "stream"),
    PUDDLE(1, 4, 100, 5, "puddle"),
    WATERNEST(1, 4, 500, 5, "stream"),

    BUSH(2, 8, 500, 5, "bush", PLAIN),
    TREE(3, 16, 1000, 5, "tree", PLAIN),

    ROCK(3, 16, 1000, 5, "rock", PLAIN),

    ENTER(1, 0, 0, 0, "grass"),
    WALL(0, 0, 0, 0, "wall"),
    VOID(0, 0, 0, 0, "void");

    private int hits;
    private int energyPerHit;
    private int delayPerHit;
    private int chance;
    private Map<Season, String> imagePaths = new HashMap<>();
    private Cell cellToReplace;

    Cell(int hits, int energyPerHit, int delayPerHit, int chance, String fileName) {
        this.hits = hits;
        this.energyPerHit = energyPerHit;
        this.delayPerHit = delayPerHit;
        this.chance = chance;
        for (Season season : Season.values()) {
            String value = String.format("/image/%s/%s.png", season.name().toLowerCase(), fileName);
            this.imagePaths.put(season, value);
        }
    }

    Cell(int hits, int energyPerHit, int delayPerHit, int chance, String fileName, Cell cellToReplace) {
        this(hits, energyPerHit, delayPerHit, chance, fileName);
        this.cellToReplace = cellToReplace;
    }

    public int getHits() {
        return hits;
    }

    public int getEnergyPerHit() {
        return energyPerHit;
    }

    public long getDelayPerHit() {
        return delayPerHit;
    }

    public int getChance() {
        return chance;
    }

    public String getImagePath(Season season) {
        return imagePaths.get(season);
    }

    public Cell getCellToReplace() {
        return cellToReplace;
    }
}
