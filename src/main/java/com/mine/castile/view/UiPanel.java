package com.mine.castile.view;

import com.mine.castile.View;
import com.mine.castile.dom.dto.loot.LootMappingDropDto;
import com.mine.castile.model.IModel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static javax.swing.BorderFactory.*;

@Component
public class UiPanel extends JPanel {

    private IModel model;

    public UiPanel(View view, IModel model) {
        this.model = model;
        setup(view);
    }

    private void setup(View view) {
        Dimension size = view.getPreferredSize();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension((int) size.getWidth(), (int) size.getHeight()));
        layeredPane.add(view, new Integer(0));
        layeredPane.add(energyPanel(), new Integer(1));
        layeredPane.add(inventoryPanel(view), new Integer(2));
        add(layeredPane);
        setOpaque(true);
    }

    private JPanel energyPanel() {
        JPanel container = new JPanel(new GridLayout(2, 1));
        container.setOpaque(true);
        container.setBounds(20, 20, 170, 50);
        container.setBorder(createStrokeBorder(new BasicStroke(3), new Color(192, 0, 0)));

        addInfo(container, model, "Energy: ", () -> "" + model.getMan().getEnergy());
        addInfo(container, model, "Season: ", () -> model.getSeason().name());

        return container;
    }


    private JPanel inventoryPanel(View view) {
        JPanel container = new JPanel(new GridLayout(0, 1));
        container.setOpaque(true);
        int width = 170;
        int height = 200;
        int x = view.getWidth() - width;
        int y = view.getHeight() - height;

        container.setBounds(x, y, width, height);
        Border border = createStrokeBorder(new BasicStroke(3), new Color(192, 0, 0));
        container.setBorder(createTitledBorder(border, "inventory"));
        fillInv(container);

        model.addModelListaner(e -> {
            container.removeAll();
            fillInv(container);
            container.revalidate();
            container.repaint();
        });

        return container;
    }

    private void fillInv(JPanel container) {
        Map<String, Integer> map = inventoryAsMap(model);
        for (String item : map.keySet()) {
            container.add(new Label(item + " (" + map.get(item) + ")"));
        }
    }

    private void addInfo(JPanel container, IModel model,
                         String title, Supplier<String> content) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        container.add(rowPanel);
        int margin = 0;
        rowPanel.setBorder(createEmptyBorder(margin, 3, margin, margin));

        JLabel contentLabel = newJLabel(content.get(), false);
        rowPanel.add(newJLabel(title, true), BorderLayout.LINE_START);
        rowPanel.add(contentLabel, BorderLayout.CENTER);

        model.addModelListaner(e -> {
            contentLabel.setText(content.get());
        });
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

//    private String inventoryAsText(IModel model) {
//        Map<String, Integer> map = new HashMap<>();
//        List<LootMappingDropDto> inventory = model.getMan().getInventory();
//        if (inventory.isEmpty()) {
//            return "";
//        }
//
//        for (LootMappingDropDto dto : inventory) {
//            Integer count = map.getOrDefault(dto.getId(), 0);
//            map.put(dto.getId(), ++count);
//        }
//        StringBuilder sb = new StringBuilder();
//        for (String id : map.keySet()) {
//            sb.append(id).append(" [").append(map.get(id)).append("], ");
//        }
//        sb.delete(sb.length() - 2, sb.length());
//        return sb.toString();
//    }

    private JLabel newJLabel(String text, boolean isTitle) {
        JLabel label = new JLabel(text);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.LEFT);
//        label.setBorder(createStrokeBorder(new BasicStroke(3), new Color(90, 93, 255)));
        label.setFont(Font.decode("Arial-BOLD-18"));
        if (isTitle) {
            label.setForeground(Color.BLUE);
        }
        return label;
    }
}
