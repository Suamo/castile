package com.mine.castile.presentation;

import com.mine.castile.application.model.Model;
import com.mine.castile.common.events.ModelChangedEvent;
import com.mine.castile.presentation.view.StatusPanel;
import com.mine.castile.presentation.view.View;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class PresentationController {
    private Model model;
    private View view;
    private StatusPanel statsPanel;

    public PresentationController(Model model, View view, StatusPanel statsPanel) {
        this.model = model;
        this.view = view;
        this.statsPanel = statsPanel;
    }

    @EventListener(ModelChangedEvent.class)
    public void configureListeners() {
        view.refresh();
        statsPanel.refresh(model);
    }

}
