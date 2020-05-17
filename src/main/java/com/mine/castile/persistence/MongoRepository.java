package com.mine.castile.persistence;

import com.google.gson.Gson;
import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.entity.GameObject;
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

    private Map<Season, Map<String, GameObjectDto>> cache;

    public MongoRepository(Gson gson, MongoTemplate mongoTemplate, CastileResourceLoader resourceLoader) {
        this.gson = gson;
        this.mongoTemplate = mongoTemplate;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    private void loadData() throws IOException {
        mongoTemplate.dropCollection("gameObject");

        loadFiles();

        List<GameObject> objects = mongoTemplate.findAll(GameObject.class);
        cache = convertToDtos(objects);
    }

    private void loadFiles() throws IOException {

        Collection<Resource> resources = resourceLoader.getDescriptors().values();

        for (final Resource resource : resources) {
            String file = asString(resource);
            byte[] bytes = file.getBytes();
            String json = new String(bytes);
            GameObject gameObject = gson.fromJson(json, GameObject.class);
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

    private Map<Season, Map<String, GameObjectDto>> convertToDtos(List<GameObject> objects) {
        Map<Season, Map<String, GameObjectDto>> dtos = new HashMap<>();
        for (GameObject object : objects) {
            for (Season season : Season.values()) {
                Map<String, GameObjectDto> map = dtos.getOrDefault(season, new HashMap<>());
                map.put(object.get_id(), new GameObjectDto(object, season));
                dtos.put(season, map);
            }
        }
        return dtos;
    }

    public Map<Season, Map<String, GameObjectDto>> getCache() {
        return cache;
    }
}
