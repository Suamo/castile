package com.mine.castile.presentation;

import com.mine.castile.application.dom.Model;
import com.mine.castile.common.events.InventoryRequestEvent;
import com.mine.castile.common.events.ModelChangedEvent;
import com.mine.castile.presentation.view.InventoryPanel;
import com.mine.castile.presentation.view.StatusPanel;
import com.mine.castile.presentation.view.View;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class PresentationController {

    private boolean isInventoryShown = false;

    private Model model;
    private View view;
    private StatusPanel statsPanel;
    private InventoryPanel inventoryPanel;

    public PresentationController(Model model, View view, StatusPanel statsPanel, InventoryPanel inventoryPanel) {
        this.model = model;
        this.view = view;
        this.statsPanel = statsPanel;
        this.inventoryPanel = inventoryPanel;
    }

    @EventListener(ModelChangedEvent.class)
    public void configureListeners() {
        view.refresh();
        statsPanel.refresh(model);
    }

    @EventListener(InventoryRequestEvent.class)
    public void onEvent() {
        isInventoryShown = !isInventoryShown;
        inventoryPanel.reloadInventory();
        inventoryPanel.setVisible(isInventoryShown);
    }

}
