package com.mine.castile.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mine.castile.data.dom.enums.GameObjectActionType;
import com.mine.castile.data.dom.enums.GameObjectAppearType;
import com.mine.castile.data.persistence.deserializers.GameObjectActionTypeDeserializer;
import com.mine.castile.data.persistence.deserializers.GameObjectAppearTypeDeserializer;
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

import java.io.IOException;

@Configuration
public class DataConfiguration {

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
    public MongoTemplate mongoTemplate() {
        MongoClient client = MongoClients.create("mongodb://localhost:27017");
        return new MongoTemplate(client, "myDb");
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
