package com.mine.castile.registry;

import com.mine.castile.renderer.ImageRenderer;

import java.util.HashMap;
import java.util.Map;

public class FrameRendererRegistry {
    private final Map<Integer, ImageRenderer> map;

    public FrameRendererRegistry(String suffix) {
        map = new HashMap<>();
        map.put(0, new ImageRenderer("/image/char/" + suffix + "_0.png"));
        map.put(1, new ImageRenderer("/image/char/" + suffix + "_1.png"));
        map.put(2, new ImageRenderer("/image/char/" + suffix + "_2.png"));
    }

    public ImageRenderer get(Integer key) {
        return map.get(key);
    }
}
