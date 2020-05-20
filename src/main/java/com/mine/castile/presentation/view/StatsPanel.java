package com.mine.castile.presentation.view;

import com.mine.castile.application.model.ManStatus;
import com.mine.castile.application.model.Model;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createStrokeBorder;

@Component
public class StatsPanel extends JPanel {

    public StatsPanel(Model model) {
        setLayout(new GridLayout(0, 1));

        setOpaque(true);
        setBounds(20, 20, 170, 130);
        setBorder(createStrokeBorder(new BasicStroke(3), new Color(192, 0, 0)));

        addInfo(model, "Season: ", () -> model.getSeason().name());

        ManStatus status = model.getMan().getManStatus();
        addInfo(model, "Energy: ", () -> "" + status.getEnergy());
        addInfo(model, "Food: ", () -> "" + status.getFood());
        addInfo(model, "Water: ", () -> "" + status.getWater());
        addInfo(model, "MutRes: ", () -> "" + status.getMutationResistance());
        addInfo(model, "Inspiration: ", () -> "" + status.getInspiration());
    }

    private void addInfo(Model model, String title, Supplier<String> content) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        add(rowPanel);

        int margin = 0;
        rowPanel.setBorder(createEmptyBorder(margin, 3, margin, margin));

        JLabel contentLabel = newJLabel(content.get(), false);
        rowPanel.add(newJLabel(title, true), BorderLayout.LINE_START);
        rowPanel.add(contentLabel, BorderLayout.CENTER);

        model.addModelListaner(e -> {
            contentLabel.setText(content.get());
        });
    }

    private JLabel newJLabel(String text, boolean isTitle) {
        JLabel label = new JLabel(text);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.LEFT);
        label.setFont(Font.decode("Arial-BOLD-18"));
        if (isTitle) {
            label.setForeground(Color.BLUE);
        }
        return label;
    }

}
