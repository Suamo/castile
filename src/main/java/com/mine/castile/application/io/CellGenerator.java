package com.mine.castile.application.io;

import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.data.dom.enums.GameObjectAppearType;
import com.mine.castile.data.dom.enums.Season;
import com.mine.castile.data.dom.objects.GameObjectAppear;
import com.mine.castile.data.persistence.MongoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

@Component
public class CellGenerator {

    @Value("${game.init.season}")
    private Season initialSeason;

    private Map<Season, Map<String, GameObjectDto>> cache;

    public CellGenerator(MongoRepository repository) {
        cache = repository.getObjectsCache();
    }

    public GameObjectDto generateCell() {
        Map<String, GameObjectDto> objectDto = cache.get(initialSeason);

        for (String id : objectDto.keySet()) {
            GameObjectDto dto = new GameObjectDto(objectDto.get(id));
            GameObjectAppear onStart = dto.getAppear().get(GameObjectAppearType.onStart);
            int chance = onStart.getChance();
            if (hadChance(chance)) {
                return dto;
            }
        }
        return generateCell();
    }

    private boolean hadChance(int chance) {
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
        dto.set_id("wall");
        dto.setBlocking(true);
        return dto;
    }
}
