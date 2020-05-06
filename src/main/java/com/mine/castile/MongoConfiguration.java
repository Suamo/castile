package com.mine.castile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mine.castile.dom.GameObject;
import com.mine.castile.dom.deserializers.GameObjectActionTypeDeserializer;
import com.mine.castile.dom.deserializers.GameObjectAppearTypeDeserializer;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.dom.enums.GameObjectAppearType;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Configuration
public class MongoConfiguration {

    public static final String GAME_OBJECTS_DIR =
            "D:\\dev\\source\\castile\\src\\main\\resources\\mongo\\gameObjects";

    @Bean
    public IMongodConfig mongodConfig() throws IOException {
        Net net = new Net("localhost", 27017, Network.localhostIsIPv6());
        Storage replication = new Storage("D:\\mongo\\myDb", null, 0);
        return new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .replication(replication)
                .net(net)
                .build();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public MongodExecutable mongodExecutable(IMongodConfig mongodConfig) {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        return starter.prepare(mongodConfig);
    }

    @DependsOn("mongodExecutable")
    @Bean
    public MongoTemplate mongoTemplate(Gson gson) throws IOException {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        MongoTemplate mongoTemplate = new MongoTemplate(client, "myDb");

        loadData(gson, mongoTemplate);
        return mongoTemplate;
    }

    private void loadData(Gson gson, MongoTemplate mongoTemplate) throws IOException {
        mongoTemplate.dropCollection("gameObject");

        File folder = new File(GAME_OBJECTS_DIR);
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
        mongoTemplate.findAll(GameObject.class);
    }

    @Bean
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameObjectActionType.class,
                new GameObjectActionTypeDeserializer());
        gsonBuilder.registerTypeAdapter(GameObjectAppearType.class,
                new GameObjectAppearTypeDeserializer());
        return gsonBuilder.create();
    }

}
