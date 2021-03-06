package com.mine.castile.application.io;

import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.data.dom.enums.Season;
import com.mine.castile.data.persistence.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Component
public class CellGenerator {

    private Map<Season, Map<String, GameObjectDto>> cache;

    public CellGenerator(MongoRepository repository) {
        cache = repository.getObjectsCache();
    }

    public GameObjectDto generateCell(Season season) {
        Map<String, GameObjectDto> objectDto = cache.get(season);

        for (String id : objectDto.keySet()) {
            int chance = objectDto.get(id).getAppearOnStart();
            if (hasChance(chance)) {
                return objectDto.get(id).doClone();
            }
        }
        return generateCell(season);
    }

    public GameObjectDto evolveCell(GameObjectDto currentDto, Season season) {
        String id = currentDto.getId();
        Map<String, Integer> possibleObjects = cache.get(season).get(id).getEvolutionToObject();

        String newId = getEvolutionId(possibleObjects, id);
        return cache.get(season).get(newId).doClone();
    }

    private String getEvolutionId(Map<String, Integer> possibleObjects, String defaultId) {
        if (possibleObjects == null) {
            return defaultId;
        }
        for (String id : possibleObjects.keySet()) {
            int chance = possibleObjects.get(id);
            if (hasChance(chance)) {
                return id;
            }
        }
        return getEvolutionId(possibleObjects, defaultId);
    }

    private boolean hasChance(int chance) {
        if (chance == 0) {
            return false;
        }
        int min = 1;
        int max = 100;
        Random r = new Random();
        int roll = r.nextInt((max - min) + 1) + min;
        return (100 - chance) <= roll;
    }

    public GameObjectDto getWall() {
        GameObjectDto dto = new GameObjectDto();
        dto.setId("wall");
        dto.setBlocking(true);
        return dto;
    }
}
