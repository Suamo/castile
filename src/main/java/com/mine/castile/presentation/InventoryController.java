package com.mine.castile.presentation;

import com.mine.castile.common.events.InventoryRequestEvent;
import com.mine.castile.presentation.view.InventoryPanel;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class InventoryController {

    private boolean isShown = false;

    private final InventoryPanel inventoryPanel;

    public InventoryController(InventoryPanel inventoryPanel) {
        this.inventoryPanel = inventoryPanel;
    }

    @EventListener(InventoryRequestEvent.class)
    public void onEvent() {
        isShown = !isShown;
        inventoryPanel.reloadInventory();
        inventoryPanel.setVisible(isShown);
    }

}
