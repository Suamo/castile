package com.mine.castile.listener;

import java.util.EventListener;

/**
 * IModelListener<p />
 * Change log:<b />
 * 06.10.2009 : created by o.korneychuk
 */
public interface IModelListener extends EventListener {
    void modelChanged(ModelEvent e);
}
