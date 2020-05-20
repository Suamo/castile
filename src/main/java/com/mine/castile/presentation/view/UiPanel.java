package com.mine.castile.presentation.view;

import com.mine.castile.application.model.Model;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class UiPanel extends JPanel {

    private View view;
    private Model model;
    private InventoryPanel inventoryPanel;
    private StatsPanel statsPanel;

    public UiPanel(View view, Model model, InventoryPanel inventoryPanel, StatsPanel statsPanel) {
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
        add(layeredPane, CENTER_ALIGNMENT);
        setOpaque(true);
    }
}
