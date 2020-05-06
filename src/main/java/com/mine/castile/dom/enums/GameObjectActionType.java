package com.mine.castile.dom.enums;

public enum GameObjectActionType {
    STEP_INTO("stepInto"),
    GATHER("gather"),
    HITS("hits");

    private String identifier;

    GameObjectActionType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
