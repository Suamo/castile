package com.mine.castile.dom.entity;

import com.mine.castile.dom.enums.GameObjectActionType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
public class LootMapping {

    private @Id
    String _id;
    private Map<GameObjectActionType, List<List<LootMappingDrop>>> mapping;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Map<GameObjectActionType, List<List<LootMappingDrop>>> getMapping() {
        return mapping;
    }

    public void setMapping(Map<GameObjectActionType, List<List<LootMappingDrop>>> mapping) {
        this.mapping = mapping;
    }
}
