package com.mine.castile.registry;

import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.enums.Season;
import com.mine.castile.persistence.MongoRepository;
import com.mine.castile.renderer.CastileResourceLoader;
import com.mine.castile.renderer.ImageRenderer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CellRendererRegistry {
    private Map<Season, Map<String, ImageRenderer>> map = new HashMap<>();

    private MongoRepository repository;
    private CastileResourceLoader resourceLoader;

    public CellRendererRegistry(MongoRepository repository, CastileResourceLoader resourceLoader) {
        this.repository = repository;
        this.resourceLoader = resourceLoader;
        createMap();
    }

    private void createMap() {
        for (Season season : Season.values()) {
            map.put(season, new HashMap<>());
        }

        Map<Season, Map<String, GameObjectDto>> cache = repository.getCache();
        for (Season season : cache.keySet()) {
            for (String id : cache.get(season).keySet()) {
                Map<String, Resource> images = resourceLoader.getImages();
                Resource resource = images.get(id);
                map.get(season).put(id, new ImageRenderer(resource));
            }
        }
    }

    public ImageRenderer get(Season season, String id) {
        return map.get(season).get(id);
    }
}
