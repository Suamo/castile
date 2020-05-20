package com.mine.castile.common.events;

import com.mine.castile.common.enums.ObjectInteraction;

public class ObjectInteractionEvent implements ApplicationEvent {
    private ObjectInteraction objectInteraction;

    public ObjectInteractionEvent(ObjectInteraction objectInteraction) {
        this.objectInteraction = objectInteraction;
    }

    public ObjectInteraction getObjectInteraction() {
        return objectInteraction;
    }

    @Override
    public String getId() {
        return objectInteraction.name();
    }
}
