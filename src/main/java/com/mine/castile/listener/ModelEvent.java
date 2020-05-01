package com.mine.castile.listener;

import java.util.EventObject;

/**
 * ModelEvent<p />
 * Change log:<b />
 * 06.10.2009 : created by o.korneychuk
 */
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
