package com.mine.castile.registry;

import java.util.HashMap;
import java.util.Map;

public class ManRendererRegistry {
    private static ManRendererRegistry instance = new ManRendererRegistry();
    private Map<Direction, FrameRendererRegistry> map;

    public static ManRendererRegistry getInstance() {
        if (instance == null) {
            instance = new ManRendererRegistry();
        }
        return instance;
    }

    public FrameRendererRegistry get(Direction key) {
        return map.get(key);
    }

    private ManRendererRegistry() {
        map = createMap();
    }

    private Map<Direction, FrameRendererRegistry> createMap() {
        Map<Direction, FrameRendererRegistry> map = new HashMap<Direction, FrameRendererRegistry>();
        map.put(Direction.UP, new FrameRendererRegistry("up"));
        map.put(Direction.DOWN, new FrameRendererRegistry("down"));
        map.put(Direction.LEFT, new FrameRendererRegistry("left"));
        map.put(Direction.RIGHT, new FrameRendererRegistry("right"));
        return map;
    }
}
