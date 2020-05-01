package com.mine.castile.registry;

public enum Season {
    WINTER,
    SPRING,
    SUMMER,
    FALL;

    public Season getNextSeason() {
        switch (this) {
            case WINTER:
                return Season.SPRING;
            case SPRING:
                return Season.SUMMER;
            case SUMMER:
                return Season.FALL;
            case FALL:
                return Season.WINTER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
