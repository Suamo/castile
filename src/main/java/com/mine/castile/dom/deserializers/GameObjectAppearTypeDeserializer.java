package com.mine.castile.dom.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mine.castile.dom.enums.GameObjectActionType;
import com.mine.castile.dom.enums.GameObjectAppearType;

import java.lang.reflect.Type;

public class GameObjectAppearTypeDeserializer implements JsonDeserializer<GameObjectAppearType> {
    @Override
    public GameObjectAppearType deserialize(JsonElement json, Type typeOfT,
                                            JsonDeserializationContext context)
            throws JsonParseException {
        GameObjectAppearType[] scopes = GameObjectAppearType.values();
        for (GameObjectAppearType type : scopes)
        {
            if (type.getIdentifier().equals(json.getAsString()))
                return type;
        }
        return null;
    }
}
