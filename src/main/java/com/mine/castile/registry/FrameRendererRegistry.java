package com.mine.castile.registry;

import com.mine.castile.dom.enums.Season;
import com.mine.castile.renderer.ImageRenderer;

import java.util.HashMap;
import java.util.Map;

public class FrameRendererRegistry {
    private final Map<Integer, ImageRenderer> map;

    public FrameRendererRegistry(String suffix) {
        map = new HashMap<>();
        map.put(0, new ImageRenderer(suffix + "_0", "char"));
        map.put(1, new ImageRenderer(suffix + "_1", "char"));
        map.put(2, new ImageRenderer(suffix + "_2", "char"));
    }

    public ImageRenderer get(Integer key) {
        return map.get(key);
    }
}
