package com.mine.castile.application.listener;

import java.util.EventListener;

public interface IModelListener extends EventListener {
    void modelChanged(ModelEvent e);
}
