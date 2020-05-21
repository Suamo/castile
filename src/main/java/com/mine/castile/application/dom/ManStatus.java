package com.mine.castile.application.dom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ManStatus {

    @Value("${game.init.character.energy}")
    private int energy;

    @Value("${game.init.character.food}")
    private int food;

    @Value("${game.init.character.water}")
    private int water;

    @Value("${game.init.character.mutation-resistance}")
    private int mutationResistance;

    @Value("${game.init.character.inspiration}")
    private int inspiration;

    private ResourcesConsumptionRules consumptionRules;

    public ManStatus(ResourcesConsumptionRules consumptionRules) {
        this.consumptionRules = consumptionRules;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getMutationResistance() {
        return mutationResistance;
    }

    public void setMutationResistance(int mutationResistance) {
        this.mutationResistance = mutationResistance;
    }

    public int getInspiration() {
        return inspiration;
    }

    public void setInspiration(int inspiration) {
        this.inspiration = inspiration;
    }

    public ResourcesConsumptionRules getConsumptionRules() {
        return consumptionRules;
    }
}
