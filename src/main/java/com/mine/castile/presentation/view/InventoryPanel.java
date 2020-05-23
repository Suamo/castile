package com.mine.castile.presentation.view;

import com.mine.castile.application.dom.Model;
import com.mine.castile.common.dom.loot.LootMappingDropDto;
import com.mine.castile.presentation.PresentationConstants;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.BorderFactory.*;
import static javax.swing.border.TitledBorder.CENTER;
import static javax.swing.border.TitledBorder.DEFAULT_POSITION;

@Component
public class InventoryPanel extends JPanel {

    public static final Font FONT = Font.decode("Arial-18");
    private Model model;

    public InventoryPanel(Model model) {
        this.model = model;
        setLayout(new GridBagLayout());

//        setOpaque(true);

        int width = 400;
        int height = 400;

        int x = PresentationConstants.VIEWPORT_WIDTH / 2 - width / 2;
        int y = PresentationConstants.VIEWPORT_HEIGHT / 2 - height / 2;

        setBounds(x, y, width, height);
        Border border = createStrokeBorder(new BasicStroke(3), new Color(192, 0, 0));
        setBorder(createTitledBorder(border, "inventory", CENTER, DEFAULT_POSITION, FONT));

        setVisible(false);
    }

    public void reloadInventory() {
        removeAll();
        fillInv(model);
        revalidate();
        repaint();
    }

    private void fillInv(Model model) {
        Map<String, Integer> map = inventoryAsMap(model);
        int i = 0;
        for (String item : map.keySet()) {
            String text = item + " (" + map.get(item) + ")";


            JLabel label = new JLabel(text);
            label.setBorder(createEmptyBorder(0, 10, 0, 0));
            GridBagConstraints c = new GridBagConstraints();
            c.weightx = 2.0;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = i;
            add(label, c);

            label.setFont(FONT);
            JButton button = new JButton("Eat");
            c.weightx = 0.2;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 1;
            c.gridy = i;
            add(button, c);
            i++;
        }
    }

    private Map<String, Integer> inventoryAsMap(Model model) {
        Map<String, Integer> map = new HashMap<>();
        List<LootMappingDropDto> inventory = model.getMan().getInventory();

        for (LootMappingDropDto dto : inventory) {
            Integer count = map.getOrDefault(dto.getId(), 0);
            map.put(dto.getId(), ++count);
        }
        return map;
    }
}
