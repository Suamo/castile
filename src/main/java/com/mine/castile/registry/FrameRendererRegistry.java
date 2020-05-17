package com.mine.castile.registry;

import com.mine.castile.renderer.ImageRenderer;
import org.springframework.core.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

public class FrameRendererRegistry {
    private final Map<Integer, ImageRenderer> map;

    public FrameRendererRegistry(Direction direction, ResourceLoader resourceLoader) {
        map = new HashMap<>();
        map.put(0, new ImageRenderer("char", "char", resourceLoader, 0, direction.getSpritePosition()));
        map.put(1, new ImageRenderer("char", "char", resourceLoader, 1, direction.getSpritePosition()));
        map.put(2, new ImageRenderer("char", "char", resourceLoader, 2, direction.getSpritePosition()));
    }

    public ImageRenderer get(Integer key) {
        return map.get(key);
    }
}
