package com.mine.castile.data.persistence.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mine.castile.data.dom.enums.GameObjectAppearType;

import java.lang.reflect.Type;

public class GameObjectAppearTypeDeserializer implements JsonDeserializer<GameObjectAppearType> {
    @Override
    public GameObjectAppearType deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context)
            throws JsonParseException {
        GameObjectAppearType[] scopes = GameObjectAppearType.values();
        for (GameObjectAppearType type : scopes) {
            if (type.name().equals(json.getAsString()))
                return type;
        }
        return null;
    }
}
