package com.mine.castile.dom.enums;

public enum GameObjectAppearType {
    on_start("onStart"),
    IN_GAME("inGame");

    private String identifier;

    GameObjectAppearType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
