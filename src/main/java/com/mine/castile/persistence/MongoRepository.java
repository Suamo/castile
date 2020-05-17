package com.mine.castile.persistence;

import com.google.gson.Gson;
import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.entity.GameObject;
import com.mine.castile.dom.entity.Loot;
import com.mine.castile.dom.entity.LootMapping;
import com.mine.castile.dom.enums.Season;
import com.mine.castile.renderer.CastileResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Repository
public class MongoRepository {

    private Gson gson;
    private MongoTemplate mongoTemplate;
    private CastileResourceLoader resourceLoader;

    private Map<Season, Map<String, GameObjectDto>> objectsCache;
    private Map<Season, Map<String, Loot>> lootCache;
    private Map<Season, Map<String, LootMapping>> lootMappingCache;

    public MongoRepository(Gson gson, MongoTemplate mongoTemplate, CastileResourceLoader resourceLoader) {
        this.gson = gson;
        this.mongoTemplate = mongoTemplate;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void loadData() {
        mongoTemplate.dropCollection("gameObject");
        mongoTemplate.dropCollection("loot");
        mongoTemplate.dropCollection("lootMapping");

        uploadFilesToMongo(GameObject.class, resourceLoader.getDescriptors().values());
        uploadFilesToMongo(Loot.class, resourceLoader.getLoot().values());
        uploadFilesToMongo(LootMapping.class, resourceLoader.getLootMapping().values());

        List<GameObject> objects = mongoTemplate.findAll(GameObject.class);
        objectsCache = objectsToDtos(objects);

        List<Loot> loot = mongoTemplate.findAll(Loot.class);
        lootCache = lootToDtos(loot);

        List<LootMapping> lootMapping = mongoTemplate.findAll(LootMapping.class);
        lootMappingCache = lootMappingToDtos(lootMapping);
    }

    private <T> void uploadFilesToMongo(Class<T> clazz, Collection<Resource> resources) {
        for (final Resource resource : resources) {
            String file = asString(resource);
            byte[] bytes = file.getBytes();
            String json = new String(bytes);
            T gameObject = gson.fromJson(json, clazz);
            mongoTemplate.insert(gameObject);
        }
    }

    private static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Map<Season, Map<String, GameObjectDto>> objectsToDtos(List<GameObject> entities) {
        Map<Season, Map<String, GameObjectDto>> dtos = new HashMap<>();
        for (GameObject entity : entities) {
            for (Season season : Season.values()) {
                Map<String, GameObjectDto> map = dtos.getOrDefault(season, new HashMap<>());
                map.put(entity.get_id(), new GameObjectDto(entity, season));
                dtos.put(season, map);
            }
        }
        return dtos;
    }

    private Map<Season, Map<String, Loot>> lootToDtos(List<Loot> entities) {
        Map<Season, Map<String, Loot>> dtos = new HashMap<>();
        for (Loot entity : entities) {
            for (Season season : Season.values()) {
                Map<String, Loot> map = dtos.getOrDefault(season, new HashMap<>());
                map.put(entity.get_id(), entity);
                dtos.put(season, map);
            }
        }
        return dtos;
    }

    private Map<Season, Map<String, LootMapping>> lootMappingToDtos(List<LootMapping> entities) {
        Map<Season, Map<String, LootMapping>> dtos = new HashMap<>();
        for (LootMapping entity : entities) {
            for (Season season : Season.values()) {
                Map<String, LootMapping> map = dtos.getOrDefault(season, new HashMap<>());
                map.put(entity.get_id(), entity);
                dtos.put(season, map);
            }
        }
        return dtos;
    }

    public Map<Season, Map<String, GameObjectDto>> getObjectsCache() {
        return objectsCache;
    }

    public Map<Season, Map<String, Loot>> getLootCache() {
        return lootCache;
    }

    public Map<Season, Map<String, LootMapping>> getLootMappingCache() {
        return lootMappingCache;
    }
}
