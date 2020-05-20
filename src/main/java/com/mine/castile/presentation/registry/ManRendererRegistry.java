package com.mine.castile.presentation.registry;

import com.mine.castile.common.enums.Direction;
import com.mine.castile.presentation.renderer.CastileResourceLoader;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ManRendererRegistry {
    private Map<Direction, FrameRendererRegistry> map;
    private CastileResourceLoader resourceLoader;

    private ManRendererRegistry(CastileResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        map = createMap();
    }

    public FrameRendererRegistry get(Direction key) {
        return map.get(key);
    }

    private Map<Direction, FrameRendererRegistry> createMap() {
        Map<Direction, FrameRendererRegistry> map = new HashMap<Direction, FrameRendererRegistry>();
        map.put(Direction.UP, new FrameRendererRegistry(Direction.UP, resourceLoader));
        map.put(Direction.DOWN, new FrameRendererRegistry(Direction.DOWN, resourceLoader));
        map.put(Direction.LEFT, new FrameRendererRegistry(Direction.LEFT, resourceLoader));
        map.put(Direction.RIGHT, new FrameRendererRegistry(Direction.RIGHT, resourceLoader));
        return map;
    }
}
