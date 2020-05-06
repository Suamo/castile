package com.mine.castile.dom;

public class GameObjectAction {
    private int count;
    private int energyPerAction;
    private int delayPerAction;
    private String whenNoActionTransformsTo;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getEnergyPerAction() {
        return energyPerAction;
    }

    public void setEnergyPerAction(int energyPerAction) {
        this.energyPerAction = energyPerAction;
    }

    public int getDelayPerAction() {
        return delayPerAction;
    }

    public void setDelayPerAction(int delayPerAction) {
        this.delayPerAction = delayPerAction;
    }

    public String getWhenNoActionTransformsTo() {
        return whenNoActionTransformsTo;
    }

    public void setWhenNoActionTransformsTo(String whenNoActionTransformsTo) {
        this.whenNoActionTransformsTo = whenNoActionTransformsTo;
    }
}
