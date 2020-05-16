package com.mine.castile.registry;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ManRendererRegistry {
    private Map<Direction, FrameRendererRegistry> map;
    private ResourceLoader resourceLoader;

    private ManRendererRegistry(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        map = createMap();
    }

    public FrameRendererRegistry get(Direction key) {
        return map.get(key);
    }

    private Map<Direction, FrameRendererRegistry> createMap() {
        Map<Direction, FrameRendererRegistry> map = new HashMap<Direction, FrameRendererRegistry>();
        map.put(Direction.UP, new FrameRendererRegistry("up", resourceLoader));
        map.put(Direction.DOWN, new FrameRendererRegistry("down", resourceLoader));
        map.put(Direction.LEFT, new FrameRendererRegistry("left", resourceLoader));
        map.put(Direction.RIGHT, new FrameRendererRegistry("right", resourceLoader));
        return map;
    }
}
