package com.mine.castile.dom.dto;

import com.mine.castile.dom.entity.GameObject;
import com.mine.castile.dom.entity.GameObjectAction;
import com.mine.castile.dom.entity.GameObjectAppear;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.dom.enums.GameObjectAppearType;
import com.mine.castile.dom.enums.Season;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document
public class GameObjectDto {

    private @Id String _id;
    private Map<GameObjectAppearType, GameObjectAppear> appear;
    private boolean blocking;
    private Map<GameObjectActionType, GameObjectAction> actions;
    private String evolutionToObject;

    public GameObjectDto(GameObject gameObject, Season season) {
        Map<Season, GameObject> overridesMap = gameObject.getSeasonOverrides();
        GameObject overrides = overridesMap == null || overridesMap.get(season) == null
                ? new GameObject() : overridesMap.get(season);

        _id = gameObject.get_id();
        appear = appearWithOverrides(gameObject.getAppear(), overrides.getAppear());
        blocking = defaultIfNull(gameObject.isBlocking(), overrides.isBlocking());
        actions = actionsWithOverrides(gameObject.getActions(), overrides.getActions());
        evolutionToObject = defaultIfNull(gameObject.getEvolutionToObject(), overrides.getEvolutionToObject());
    }

    private Map<GameObjectAppearType, GameObjectAppear> appearWithOverrides(
            Map<GameObjectAppearType, GameObjectAppear> initialObject,
            Map<GameObjectAppearType, GameObjectAppear> overriddenObject) {

        HashMap<GameObjectAppearType, GameObjectAppear> result = new HashMap<>();

        for (GameObjectAppearType type : GameObjectAppearType.values()) {

            GameObjectAppear initial = initialObject.get(type);
            if (initial == null) {
                initial = new GameObjectAppear();
            }
            if (overriddenObject == null || overriddenObject.get(type) == null) {
                result.put(type, new GameObjectAppear(initial.getChance(), initial.getReplaceObjects()));
            } else {
                GameObjectAppear overridden = overriddenObject.get(type);
                Integer chance = defaultIfNull(overridden.getChance(), initial.getChance());
                String replaceObjects = defaultIfNull(overridden.getReplaceObjects(), initial.getReplaceObjects());

                result.put(type, new GameObjectAppear(chance, replaceObjects));
            }

        }
        return result;
    }

    private Map<GameObjectActionType, GameObjectAction> actionsWithOverrides(
            Map<GameObjectActionType, GameObjectAction> initialObject,
            Map<GameObjectActionType, GameObjectAction> overriddenObject) {

        HashMap<GameObjectActionType, GameObjectAction> result = new HashMap<>();

        for (GameObjectActionType type : GameObjectActionType.values()) {

            GameObjectAction initial = initialObject.get(type);
            if (initial == null) {
                initial = new GameObjectAction();
            }
            if (overriddenObject == null || overriddenObject.get(type) == null) {
                result.put(type, new GameObjectAction(initial.getCount(), initial.getDelayPerAction(),
                        initial.getEnergyPerAction(), initial.getWhenNoActionTransformsTo()));
            } else {
                GameObjectAction overridden = overriddenObject.get(type);
                Integer count = defaultIfNull(overridden.getCount(), initial.getCount());
                Integer delayPerAction = defaultIfNull(overridden.getDelayPerAction(), initial.getDelayPerAction());
                Integer energyPerAction = defaultIfNull(overridden.getEnergyPerAction(), initial.getEnergyPerAction());
                String whenNoActionTransformsTo = defaultIfNull(overridden.getWhenNoActionTransformsTo(), initial.getWhenNoActionTransformsTo());

                result.put(type, new GameObjectAction(count, delayPerAction, energyPerAction, whenNoActionTransformsTo));
            }
        }
        return result;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Map<GameObjectAppearType, GameObjectAppear> getAppear() {
        return appear;
    }

    public void setAppear(Map<GameObjectAppearType, GameObjectAppear> appear) {
        this.appear = appear;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
    }

    public Map<GameObjectActionType, GameObjectAction> getActions() {
        return actions;
    }

    public void setActions(Map<GameObjectActionType, GameObjectAction> actions) {
        this.actions = actions;
    }

    public String getEvolutionToObject() {
        return evolutionToObject;
    }

    public void setEvolutionToObject(String evolutionToObject) {
        this.evolutionToObject = evolutionToObject;
    }

    private <T> T defaultIfNull(T init, T overr) {
        if (init == null) {
            return overr;
        }
        return init;
    }
}
