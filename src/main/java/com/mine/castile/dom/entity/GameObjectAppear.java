package com.mine.castile.dom.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class GameObjectAppear {

    private Integer chance;
    private String replaceObjects;

    public GameObjectAppear() {
    }

    public GameObjectAppear(Integer chance, String replaceObjects) {
        this.chance = chance;
        this.replaceObjects = replaceObjects;
    }

    public Integer getChance() {
        return chance;
    }

    public void setChance(Integer chance) {
        this.chance = chance;
    }

    public String getReplaceObjects() {
        return replaceObjects;
    }

    public void setReplaceObjects(String replaceObjects) {
        this.replaceObjects = replaceObjects;
    }
}
