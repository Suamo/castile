package com.mine.castile.listener;

import com.mine.castile.View;

public class RefreshListener implements IModelListener {
    private View view;

    public RefreshListener(View view) {
        this.view = view;
    }

    public void modelChanged(ModelEvent e) {
        view.revalidate(); // todo: is needed?
        view.paintImmediately(0, 0, view.getWidth(), view.getHeight()); // instead of async repaint()
    }
}
