package com.mine.castile.presentation.renderer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CastileResourceLoader {
    private ResourceLoader resourceLoader;

    private Map<String, Resource> images = new HashMap<>();
    private Map<String, Resource> loot = new HashMap<>();
    private Map<String, Resource> lootMapping = new HashMap<>();
    private Map<String, Resource> descriptors = new HashMap<>();

    public CastileResourceLoader(ResourceLoader resourceLoader,
                                 @Value("${game.objects.images}") String imagesDirectory,
                                 @Value("${game.objects.loot}") String lootDirectory,
                                 @Value("${game.objects.lootMapping}") String lootMappingDirectory,
                                 @Value("${game.objects.descriptors}") String descriptorsDirectory) {
        this.resourceLoader = resourceLoader;

        loadCache(images, imagesDirectory);
        loadCache(loot, lootDirectory);
        loadCache(lootMapping, lootMappingDirectory);
        loadCache(descriptors, descriptorsDirectory);
    }

    private void loadCache(Map<String, Resource> cache, String pattern) {
        try {
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
            for (Resource resource : resources) {
                String filename = resource.getFilename();
                assert filename != null;
                filename = filename.split("\\.")[0];
                cache.put(filename, resource);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Resource> getImages() {
        return images;
    }

    public Map<String, Resource> getLoot() {
        return loot;
    }

    public Map<String, Resource> getLootMapping() {
        return lootMapping;
    }

    public Map<String, Resource> getDescriptors() {
        return descriptors;
    }
}
