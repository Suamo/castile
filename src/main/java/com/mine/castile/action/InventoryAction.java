package com.mine.castile.action;

import com.mine.castile.model.IModel;
import com.mine.castile.persistence.MongoRepository;
import com.mine.castile.view.InventoryPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class InventoryAction extends AbstractAction {

    private final InventoryPanel inventoryPanel;
    private final IModel model;
    private final MongoRepository repository;

    private boolean isShown = false;

    public InventoryAction(InventoryPanel inventoryPanel, IModel model, MongoRepository repository) {
        this.inventoryPanel = inventoryPanel;
        this.model = model;
        this.repository = repository;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        isShown = !isShown;
        inventoryPanel.reloadInventory();
        inventoryPanel.setVisible(isShown);
    }

}
