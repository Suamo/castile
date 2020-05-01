package com.mine.castile.registry;

import com.mine.castile.renderer.ImageRenderer;

import java.util.HashMap;
import java.util.Map;

public class CellRendererRegistry {
    private static CellRendererRegistry instance = new CellRendererRegistry();
    private Map<Season, Map<Cell, ImageRenderer>> map;

    public static CellRendererRegistry getInstance() {
        if (instance == null) {
            instance = new CellRendererRegistry();
        }
        return instance;
    }

    public ImageRenderer get(Season season, Cell key) {
        return map.get(season).get(key);
    }

    private CellRendererRegistry() {
        map = createMap();
    }

    private Map<Season, Map<Cell, ImageRenderer>> createMap() {
        Map<Season, Map<Cell, ImageRenderer>> map = new HashMap<>();
        for (Season season : Season.values()) {
            map.put(season, new HashMap<>());
        }

        for (Cell cell : Cell.values()) {
            for (Season season : Season.values()) {
                map.get(season).put(cell, new ImageRenderer(cell.getImagePath(season)));
            }
        }
        return map;
    }
}
