package com.mine.castile.view;

import com.mine.castile.Constants;
import com.mine.castile.dom.dto.loot.LootMappingDropDto;
import com.mine.castile.model.IModel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.BorderFactory.*;

@Component
public class InventoryPanel extends JPanel {

    public static final Font FONT = Font.decode("Arial-18");
    private IModel model;

    public InventoryPanel(IModel model) {
        this.model = model;
        GridLayout mgr = new GridLayout(0, 1);
        mgr.setHgap(20);
        mgr.setRows(15);

        setLayout(mgr);

        setOpaque(true);

        int width = 400;
        int height = 400;

        int x = Constants.VIEWPORT_WIDTH / 2 - width / 2;
        int y = Constants.VIEWPORT_HEIGHT / 2 - height / 2;

        setBounds(x, y, width, height);
        Border border = createStrokeBorder(new BasicStroke(3), new Color(192, 0, 0));
        setBorder(createTitledBorder(border, "inventory",
                TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, FONT));

        setVisible(false);
    }

    private void fillInv(IModel model) {
        Map<String, Integer> map = inventoryAsMap(model);
        for (String item : map.keySet()) {
            JLabel label = new JLabel(item + " (" + map.get(item) + ")");
            label.setFont(FONT);
            int margin = 20;
            label.setBorder(createEmptyBorder(margin, margin, margin, margin));
            add(label);
        }
    }

    private Map<String, Integer> inventoryAsMap(IModel model) {
        Map<String, Integer> map = new HashMap<>();
        List<LootMappingDropDto> inventory = model.getMan().getInventory();

        for (LootMappingDropDto dto : inventory) {
            Integer count = map.getOrDefault(dto.getId(), 0);
            map.put(dto.getId(), ++count);
        }
        return map;
    }

    public void reloadInventory() {
        removeAll();
        fillInv(model);
        revalidate();
        repaint();
    }
}
