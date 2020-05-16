package com.mine.castile.persistence;

import com.google.gson.Gson;
import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.entity.GameObject;
import com.mine.castile.dom.enums.Season;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class MongoRepository {

    @Value("${game.objects.directory}")
    private String gameObjectsDirectory;

    private Gson gson;
    private MongoTemplate mongoTemplate;

    private Map<Season, Map<String, GameObjectDto>> cache;

    public MongoRepository(Gson gson, MongoTemplate mongoTemplate) {
        this.gson = gson;
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    private void loadData() throws IOException {
        mongoTemplate.dropCollection("gameObject");

        File folder = new File(gameObjectsDirectory);
        if (folder.exists()) {
            loadFiles(folder);
        }
        folder = new File(getClass().getResource("/" + gameObjectsDirectory).getFile());
        loadFiles(folder);

        List<GameObject> objects = mongoTemplate.findAll(GameObject.class);
        cache = convertToDtos(objects);
    }

    private void loadFiles(File folder) throws IOException {
        File[] files = Objects.requireNonNull(folder.listFiles());
        for (final File fileEntry : files) {
            if (!fileEntry.getName().endsWith(".json")) {
                continue;
            }
            Path path = Paths.get(fileEntry.getAbsolutePath()).toAbsolutePath();
            byte[] bytes = Files.readAllBytes(path);
            String json = new String(bytes);
            GameObject gameObject = gson.fromJson(json, GameObject.class);
            mongoTemplate.insert(gameObject);
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
