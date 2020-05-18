package com.mine.castile.dom.entity.loot;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class LootMapping {

    private @Id
    String _id;
    private List<LootMappingSeason> mapping;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setMapping(List<LootMappingSeason> mapping) {
        this.mapping = mapping;
    }

    public List<LootMappingSeason> getMapping() {
        return mapping;
    }
}
