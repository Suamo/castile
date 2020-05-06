package com.mine.castile.dom;

import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.dom.enums.GameObjectAppearType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public class GameObject {

    private @Id String _id;
    private Map<GameObjectAppearType, GameObjectAppear> appear;
    private boolean blocking;
    private Map<GameObjectActionType, GameObjectAction> actions;
    private String evolutionToObject;

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
}
