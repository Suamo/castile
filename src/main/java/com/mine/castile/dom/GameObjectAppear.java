package com.mine.castile.dom;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GameObjectAppear {

    private int chance;
    private String replaceObjects;

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public String getReplaceObjects() {
        return replaceObjects;
    }

    public void setReplaceObjects(String replaceObjects) {
        this.replaceObjects = replaceObjects;
    }
}
