package com.mine.castile.data.dom.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectAction {

    private String seasons;
    private Integer count;
    private Integer energyPerAction;
    private Integer delayPerAction;
    private String whenNoActionTransformsTo;

    public GameObjectAction doClone() {
        return new GameObjectAction(seasons, count, energyPerAction, delayPerAction, whenNoActionTransformsTo);
    }
}
