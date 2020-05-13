package com.mine.castile.persistence;

import com.google.gson.Gson;
import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.entity.GameObject;
import com.mine.castile.dom.enums.Season;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Repository
public class MongoRepository {

    @Value("${game.objects.directory}")
    private String gameObjectsDirectory;

    private Gson gson;
    private MongoTemplate mongoTemplate;

    private Map<Season, GameObjectDto> cache;

    public MongoRepository(Gson gson, MongoTemplate mongoTemplate) {
        this.gson = gson;
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    private void loadData() throws IOException {
        mongoTemplate.dropCollection("gameObject");

        File folder = new File(gameObjectsDirectory);
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

        List<GameObject> objects = mongoTemplate.findAll(GameObject.class);
        cache = convertToDtos(objects);
    }

    private Map<Season, GameObjectDto> convertToDtos(List<GameObject> objects) {
        Map<Season, GameObjectDto> dtos = new HashMap<>();
        for (GameObject object : objects) {
            for (Season season : Season.values()) {
                dtos.put(season, new GameObjectDto(object, season));
            }
        }
        return dtos;
    }

    public Map<Season, GameObjectDto> getCache() {
        return Collections.unmodifiableMap(cache);
    }
}
