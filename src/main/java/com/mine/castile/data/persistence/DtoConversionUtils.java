package com.mine.castile.data.persistence;

import com.mine.castile.data.dom.enums.GameObjectActionType;
import com.mine.castile.data.dom.enums.Season;
import com.mine.castile.data.dom.objects.GameObject;
import com.mine.castile.data.dom.objects.GameObjectAction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DtoConversionUtils {

    public static Map<String, Integer> parseChances(String rawChances) {
        if (rawChances == null || !rawChances.startsWith("chance ")) {
            return null;
        }
        String[] chancesArray = rawChances.replace("chance ", "").split(" ");

        HashMap<String, Integer> map = new HashMap<>();
        for (String chance : chancesArray) {
            String[] split = chance.split(":");
            map.put(split[0], Integer.parseInt(split[1]));
        }
        return map;
    }

    public static Map<GameObjectActionType, GameObjectAction> prepareActions(GameObject entity, Season season) {
        Map<GameObjectActionType, List<GameObjectAction>> actions = entity.getActions();
        if (actions == null) {
            return null;
        }
        HashMap<GameObjectActionType, GameObjectAction> result = new HashMap<>();
        for (GameObjectActionType type : actions.keySet()) {
            List<GameObjectAction> actionList = actions.get(type);
            for (GameObjectAction action : actionList) {
                String seasons = action.getSeasons();
                if (seasons == null || seasons.contains(season.name())) {
                    result.put(type, action);
                }
            }
        }
        return result;
    }

}
