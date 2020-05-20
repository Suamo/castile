package com.mine.castile.application.action.hotkeys;

import com.mine.castile.presentation.view.InventoryPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InventoryAction extends AbstractAction {

    private final InventoryPanel inventoryPanel;

    private boolean isShown = false;

    public InventoryAction(InventoryPanel inventoryPanel) {
        this.inventoryPanel = inventoryPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        isShown = !isShown;
        inventoryPanel.reloadInventory();
        inventoryPanel.setVisible(isShown);
    }

}