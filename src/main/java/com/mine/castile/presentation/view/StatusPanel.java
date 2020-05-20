package com.mine.castile.presentation.view;

import com.mine.castile.application.model.ManStatus;
import com.mine.castile.application.model.Model;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createStrokeBorder;

@Component
public class StatusPanel extends JPanel {

    private final JLabel seasonLanel;
    private final JLabel energyLabel;
    private final JLabel foodLabel;
    private final JLabel waterLabel;
    private final JLabel mutResLabel;
    private final JLabel inspirationLabel;

    public StatusPanel(Model model) {
        setLayout(new GridLayout(0, 1));

        setOpaque(true);
        setBounds(20, 20, 170, 130);
        setBorder(createStrokeBorder(new BasicStroke(3), new Color(192, 0, 0)));

        seasonLanel = addInfo("Season: ");
        energyLabel = addInfo("Energy: ");
        foodLabel = addInfo("Food: ");
        waterLabel = addInfo("Water: ");
        mutResLabel = addInfo("MutRes: ");
        inspirationLabel = addInfo("Inspiration: ");

        refresh(model);
    }

    public void refresh(Model model) {
        ManStatus status = model.getMan().getManStatus();
        seasonLanel.setText(model.getSeason().name());
        energyLabel.setText("" + status.getEnergy());
        foodLabel.setText("" + status.getFood());
        waterLabel.setText("" + status.getWater());
        mutResLabel.setText("" + status.getMutationResistance());
        inspirationLabel.setText("" + status.getInspiration());
    }

    private JLabel addInfo(String title) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        add(rowPanel);

        int margin = 0;
        rowPanel.setBorder(createEmptyBorder(margin, 3, margin, margin));

        JLabel contentLabel = newJLabel("", false);
        rowPanel.add(newJLabel(title, true), BorderLayout.LINE_START);
        rowPanel.add(contentLabel, BorderLayout.CENTER);

        return contentLabel;
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
