package com.mine.castile.renderer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class CastileResourceLoader {
    private static final String IMAGES_FILETYPE = ".png";

    private ResourceLoader resourceLoader;

    private Map<String, Resource> images = new HashMap<>();
    private Map<String, Resource> descriptors = new HashMap<>();

    public CastileResourceLoader(ResourceLoader resourceLoader,
                                 @Value("${game.objects.images}") String imagesDirectory,
                                 @Value("${game.objects.descriptors}") String descriptorsDirectory) {
        this.resourceLoader = resourceLoader;

        loadCache(images, imagesDirectory);
        loadCache(descriptors, descriptorsDirectory);
    }

    private void loadCache(Map<String, Resource> cache, String pattern) {
        try {
            Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
            for (Resource resource : resources) {
                String name = Objects.requireNonNull(resource.getFilename()).replace(IMAGES_FILETYPE, "");
                cache.put(name, resource);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, Resource> getImages() {
        return images;
    }

    public Map<String, Resource> getDescriptors() {
        return descriptors;
    }
}
