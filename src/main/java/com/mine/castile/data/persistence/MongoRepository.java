package com.mine.castile.data.persistence;

import com.google.gson.Gson;
import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.common.dom.loot.LootMappingActionsDto;
import com.mine.castile.common.dom.loot.LootMappingDropDto;
import com.mine.castile.data.dom.enums.Season;
import com.mine.castile.data.dom.loot.*;
import com.mine.castile.data.dom.objects.GameObject;
import com.mine.castile.presentation.renderer.CastileResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.*;

import static com.mine.castile.data.persistence.DtoConversionUtils.parceChances;
import static com.mine.castile.data.persistence.DtoConversionUtils.prepareActions;
import static java.nio.charset.StandardCharsets.UTF_8;

@Repository
public class MongoRepository {

    private Gson gson;
    private MongoTemplate mongoTemplate;
    private CastileResourceLoader resourceLoader;

    private Map<Season, Map<String, GameObjectDto>> objectsCache;
    private Map<Season, Map<String, Loot>> lootCache;
    private Map<Season, Map<String, LootMappingActionsDto>> lootMappingCache;

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
                map.put(entity.get_id(), objectToDto(entity, season));
                dtos.put(season, map);
            }
        }
        return dtos;
    }

    private GameObjectDto objectToDto(GameObject entity, Season season) {
        GameObjectDto dto = new GameObjectDto();
        dto.setId(entity.get_id());
        dto.setAppearOnStart(entity.getAppearOnStart());
        dto.setAppearInGame(parceChances(entity.getAppearInGame()));
        dto.setBlocking(entity.getBlocking());
        dto.setActions(prepareActions(entity, season));
        dto.setEvolutionToObject(parceChances(entity.getEvolutionToObject()));
        return dto;
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

    private Map<Season, Map<String, LootMappingActionsDto>> lootMappingToDtos(List<LootMapping> entities) {
        Map<Season, Map<String, LootMappingActionsDto>> dtos = new HashMap<>();
        for (LootMapping entity : entities) {

            String id = entity.get_id();

            for (LootMappingSeason entitySeasonMapping : entity.getMapping()) {
                for (Season entitySeason : entitySeasonMapping.getSeasons()) {
                    Map<String, LootMappingActionsDto> dtoSeasonMappings = dtos.getOrDefault(entitySeason, new HashMap<>());
                    dtos.put(entitySeason, dtoSeasonMappings);

                    LootMappingActionsDto dtoMappingActions = dtoSeasonMappings.getOrDefault(id, new LootMappingActionsDto());
                    dtoSeasonMappings.put(id, dtoMappingActions);


                    List<LootMappingAction> entityGathers = entitySeasonMapping.getGather();
                    List<LootMappingAction> entityHits = entitySeasonMapping.getHits();

                    Map<Integer, List<LootMappingDropDto>> dtoGather = dtoMappingActions.getGather();
                    Map<Integer, List<LootMappingDropDto>> dtoHits = dtoMappingActions.getHits();

                    populateDtoLootPerAction(entityGathers, dtoGather);
                    populateDtoLootPerAction(entityHits, dtoHits);
                }
            }
        }
        return dtos;
    }

    private void populateDtoLootPerAction(List<LootMappingAction> entityGathers, Map<Integer, List<LootMappingDropDto>> dtoGather) {
        if (entityGathers == null) {
            return;
        }
        for (LootMappingAction entityGather : entityGathers) {
            List<LootMappingDropDto> newDtoDrops = convertDrops(entityGather.getLoot());
            for (Integer affectedAction : entityGather.getAffectedActions()) {
                List<LootMappingDropDto> dtoDrops =
                        dtoGather.getOrDefault(affectedAction, new ArrayList<>());
                dtoGather.put(affectedAction, dtoDrops);
                dtoDrops.addAll(newDtoDrops);
            }
        }
    }

    private List<LootMappingDropDto> convertDrops(List<LootMappingDrop> loot) {
        ArrayList<LootMappingDropDto> dtoDrops = new ArrayList<>();
        for (LootMappingDrop entityDrop : loot) {
            dtoDrops.add(new LootMappingDropDto(entityDrop));
        }
        return dtoDrops;
    }

    public Map<Season, Map<String, GameObjectDto>> getObjectsCache() {
        return objectsCache;
    }

    public Map<Season, Map<String, Loot>> getLootCache() {
        return lootCache;
    }

    public Map<Season, Map<String, LootMappingActionsDto>> getLootMappingCache() {
        return lootMappingCache;
    }
}
