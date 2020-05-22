package com.mine.castile.common.dom;

import com.mine.castile.data.dom.enums.GameObjectActionType;
import com.mine.castile.data.dom.objects.GameObjectAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameObjectDto implements Cloneable {

    private String id;
    private int appearOnStart;
    private Map<String, Integer> appearInGame;
    private boolean blocking;
    private Map<GameObjectActionType, GameObjectAction> actions;
    private Map<String, Integer> evolutionToObject;

    public GameObjectDto doClone() {
        return new GameObjectDto(id, appearOnStart, appearInGame,
                blocking, doClone(actions), evolutionToObject);
    }

    private Map<GameObjectActionType, GameObjectAction> doClone
            (Map<GameObjectActionType, GameObjectAction> actions) {
        if (actions == null) {
            return null;
        }

        Map<GameObjectActionType, GameObjectAction> result = new HashMap<>();
        for (GameObjectActionType type : actions.keySet()) {
            GameObjectAction action = actions.get(type);
            result.put(type, action.doClone());
        }
        return result;
    }

    public static GameObjectDto createWithDefaults(String id) {
        return new GameObjectDto(id, 0, null, true, new HashMap<>(), null);
    }
}
