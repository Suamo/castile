package com.mine.castile.common.events;

import com.mine.castile.common.enums.Direction;

public class CharacterMoveEvent implements ApplicationEvent {
    private Direction direction;

    public CharacterMoveEvent(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String getId() {
        return direction.name();
    }
}
