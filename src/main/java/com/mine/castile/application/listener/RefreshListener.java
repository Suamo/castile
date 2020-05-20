package com.mine.castile.application.listener;

import com.mine.castile.presentation.view.View;

public class RefreshListener implements IModelListener {
    private View view;

    public RefreshListener(View view) {
        this.view = view;
    }

    public void modelChanged(ModelEvent e) {
        view.paintImmediately(0, 0, view.getWidth(), view.getHeight()); // instead of async repaint()
    }
}
