package com.mine.castile.dom.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mine.castile.dom.enums.GameObjectActionType;

import java.lang.reflect.Type;

public class GameObjectActionTypeDeserializer implements JsonDeserializer<GameObjectActionType> {
    @Override
    public GameObjectActionType deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context)
            throws JsonParseException {
        GameObjectActionType[] scopes = GameObjectActionType.values();
        for (GameObjectActionType type : scopes)
        {
            if (type.name().equals(json.getAsString()))
                return type;
        }
        return null;
    }
}