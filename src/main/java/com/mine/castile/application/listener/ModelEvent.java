package com.mine.castile.application.listener;

import java.util.EventObject;

public class ModelEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ModelEvent(Object source) {
        super(source);
    }
}
