package com.mine.castile.application.model;

import com.mine.castile.data.dom.enums.Season;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResourcesConsumptionRules {

    @Value("${game.resources.food.init}")
    private int foodInitialValue;

    @Value("${game.resources.water.init}")
    private int waterInitialValue;

    @Value("${game.resources.mutRes.init}")
    private int mutResInitialValue;

    @Value("${game.resources.inspiration.init}")
    private int inspirationInitialValue;

    private Map<Season, Integer> foodSpending = new HashMap<>();
    private Map<Season, Integer> waterSpending = new HashMap<>();
    private Map<Season, Integer> mutResSpending = new HashMap<>();
    private Map<Season, Integer> inspirationSpending = new HashMap<>();

    public ResourcesConsumptionRules(
            @Value("${game.resources.food.spending}") String foodSpening,
            @Value("${game.resources.water.spending}") String waterSpening,
            @Value("${game.resources.mutRes.spending}") String mutResSpening,
            @Value("${game.resources.inspiration.spending}") String inspirationSpening) {

        String[] food = foodSpening.split(",");
        String[] water = waterSpening.split(",");
        String[] mutRes = mutResSpening.split(",");
        String[] inspiration = inspirationSpening.split(",");

        for (Season season : Season.values()) {
            foodSpending.put(season, getSeasonValue(food, season));
            waterSpending.put(season, getSeasonValue(water, season));
            mutResSpending.put(season, getSeasonValue(mutRes, season));
            inspirationSpending.put(season, getSeasonValue(inspiration, season));
        }

    }

    public int getFoodSpening(Season season) {
        return foodSpending.get(season);
    }

    public int getWaterSpening(Season season) {
        return waterSpending.get(season);
    }

    public int getMutResSpening(Season season) {
        return mutResSpending.get(season);
    }

    public int getInspirationSpening(Season season) {
        return inspirationSpending.get(season);
    }

    private int getSeasonValue(String[] values, Season season) {
        int index = -1;
        switch (season) {
            case spring1:
            case spring2:
            case spring3:
                index = 0;
                break;
            case summer1:
            case summer2:
            case summer3:
                index = 1;
                break;
            case fall1:
            case fall2:
            case fall3:
                index = 2;
                break;
            case winter1:
            case winter2:
            case winter3:
                index = 3;
                break;
            default:
                throw new UnsupportedOperationException();
        }
        String value = values[index];
        return Integer.parseInt(value);
    }
}
