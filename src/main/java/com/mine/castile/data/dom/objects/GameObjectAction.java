package com.mine.castile.data.dom.objects;

public class GameObjectAction {
    private Integer count;
    private Integer energyPerAction;
    private Integer delayPerAction;
    private String whenNoActionTransformsTo;

    public GameObjectAction() {
    }

    public GameObjectAction(Integer count, Integer energyPerAction, Integer delayPerAction, String whenNoActionTransformsTo) {
        this.count = count;
        this.energyPerAction = energyPerAction;
        this.delayPerAction = delayPerAction;
        this.whenNoActionTransformsTo = whenNoActionTransformsTo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getEnergyPerAction() {
        return energyPerAction;
    }

    public void setEnergyPerAction(Integer energyPerAction) {
        this.energyPerAction = energyPerAction;
    }

    public Integer getDelayPerAction() {
        return delayPerAction;
    }

    public void setDelayPerAction(Integer delayPerAction) {
        this.delayPerAction = delayPerAction;
    }

    public String getWhenNoActionTransformsTo() {
        return whenNoActionTransformsTo;
    }

    public void setWhenNoActionTransformsTo(String whenNoActionTransformsTo) {
        this.whenNoActionTransformsTo = whenNoActionTransformsTo;
    }
}
