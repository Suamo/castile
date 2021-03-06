package com.mine.castile.application.dom;

import com.mine.castile.data.dom.enums.Season;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResourcesConsumptionRules {

    private Map<Season, Integer> foodSpending = new HashMap<>();
    private Map<Season, Integer> waterSpending = new HashMap<>();
    private Map<Season, Integer> mutResSpending = new HashMap<>();
    private Map<Season, Integer> inspirationSpending = new HashMap<>();

    public ResourcesConsumptionRules(
            @Value("${game.resources-spending.food}") String foodSpening,
            @Value("${game.resources-spending.water}") String waterSpening,
            @Value("${game.resources-spending.mutRes}") String mutResSpening,
            @Value("${game.resources-spending.inspiration}") String inspirationSpening) {

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
        int index;
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
