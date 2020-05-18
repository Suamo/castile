package com.mine.castile.view;

import com.mine.castile.model.IModel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.function.Supplier;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createStrokeBorder;

@Component
public class StatsPanel extends JPanel {

    public StatsPanel(IModel model) {
        setLayout(new GridLayout(2, 1));

        setOpaque(true);
        setBounds(20, 20, 170, 50);
        setBorder(createStrokeBorder(new BasicStroke(3), new Color(192, 0, 0)));

        addInfo(model, "Energy: ", () -> "" + model.getMan().getEnergy());
        addInfo(model, "Season: ", () -> model.getSeason().name());
    }

    private void addInfo(IModel model, String title, Supplier<String> content) {
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
//        label.setBorder(createStrokeBorder(new BasicStroke(3), new Color(90, 93, 255)));
        label.setFont(Font.decode("Arial-BOLD-18"));
        if (isTitle) {
            label.setForeground(Color.BLUE);
        }
        return label;
    }

}
