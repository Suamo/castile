package com.mine.castile.view;

import com.mine.castile.model.IModel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class UiPanel extends JPanel {

    private View view;
    private IModel model;
    private InventoryPanel inventoryPanel;
    private StatsPanel statsPanel;

    public UiPanel(View view, IModel model, InventoryPanel inventoryPanel, StatsPanel statsPanel) {
        this.view = view;
        this.model = model;
        this.inventoryPanel = inventoryPanel;
        this.statsPanel = statsPanel;
        setup();
    }

    private void setup() {
        Dimension size = view.getPreferredSize();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension((int) size.getWidth(), (int) size.getHeight()));
        layeredPane.add(view, new Integer(0));
        layeredPane.add(statsPanel, new Integer(1));
        layeredPane.add(inventoryPanel, new Integer(2));
        add(layeredPane);
        setOpaque(true);
    }
}
