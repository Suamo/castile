package com.mine.castile;

import com.mine.castile.dom.dto.loot.LootMappingDropDto;
import com.mine.castile.model.IModel;
import de.flapdoodle.embed.process.distribution.BitSize;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createStrokeBorder;

@SpringBootApplication
public class CastileApplication {

    public static void main(String[] args) {
        System.out.println("OS bits (mongo detect): " + BitSize.detect());

        new SpringApplicationBuilder(CastileApplication.class)
                .headless(false).run(args);
    }

    @Bean
    public JFrame frame(View view, IModel model) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(view, BorderLayout.CENTER);
        panel.add(inventoryPanel(model), BorderLayout.PAGE_END);

        JFrame frame = new JFrame("The Castile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    private JPanel inventoryPanel(IModel model) {
        JPanel container = new JPanel(new GridLayout(3, 0));
        container.setBorder(createStrokeBorder(new BasicStroke(3), new Color(192, 0, 0)));

        addInfo(container, model, "Season: ", () -> model.getSeason().name());
        addInfo(container, model, "Energy: ", () -> "" + model.getMan().getEnergy());
        addInfo(container, model, "Inventory: ", () -> inventoryAsText(model));

        return container;
    }

    private void addInfo(JPanel container, IModel model,
                         String title, Supplier<String> content) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        container.add(rowPanel);
        int margin = 5;
        rowPanel.setBorder(createEmptyBorder(margin, margin, margin, margin));

        JLabel contentLabel = newJLabel(content.get(), false);
        rowPanel.add(newJLabel(title, true), BorderLayout.LINE_START);
        rowPanel.add(contentLabel, BorderLayout.CENTER);

        model.addModelListaner(e -> {
            contentLabel.setText(content.get());
        });
    }

    private JLabel newJLabel(String text, boolean isTitle) {
        JLabel label = new JLabel(text);
        label.setFont(Font.decode("Arial-BOLD-18"));
        if (isTitle) {
            label.setForeground(Color.BLUE);
        }
        return label;
    }

    private String inventoryAsText(IModel model) {
        Map<String, Integer> map = new HashMap<>();
        List<LootMappingDropDto> inventory = model.getMan().getInventory();
        if (inventory.isEmpty()) {
            return "";
        }

        for (LootMappingDropDto dto : inventory) {
            Integer count = map.getOrDefault(dto.getId(), 0);
            map.put(dto.getId(), ++count);
        }
        StringBuilder sb = new StringBuilder();
        for (String id : map.keySet()) {
            sb.append(id).append(" [").append(map.get(id)).append("], ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }

}
