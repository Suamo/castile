package com.mine.castile.listener;

import java.util.EventListener;

public interface IModelListener extends EventListener {
    void modelChanged(ModelEvent e);
}
