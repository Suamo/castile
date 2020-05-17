package com.mine.castile.registry;

import com.mine.castile.renderer.CastileResourceLoader;
import com.mine.castile.renderer.ImageRenderer;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

public class FrameRendererRegistry {
    private final Map<Integer, ImageRenderer> map;

    public FrameRendererRegistry(Direction direction, CastileResourceLoader resourceLoader) {
        map = new HashMap<>();
        Map<String, Resource> images = resourceLoader.getImages();
        Resource resource = images.get("char");
        map.put(0, new ImageRenderer(resource, 0, direction.getSpritePosition()));
        map.put(1, new ImageRenderer(resource, 1, direction.getSpritePosition()));
        map.put(2, new ImageRenderer(resource, 2, direction.getSpritePosition()));
    }

    public ImageRenderer get(Integer key) {
        return map.get(key);
    }
}
