package com.mine.castile.dom.enums;

public enum Season {
    spring,
    summer,
    fall,
    winter;

    public Season getNextSeason() {
        int next = this.ordinal() + 1;
        if (next >= values().length) {
            next = 0;
        }
        return values()[next];
    }
}
