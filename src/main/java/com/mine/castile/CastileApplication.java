package com.mine.castile;

import com.mine.castile.dom.dto.loot.LootMappingDropDto;
import com.mine.castile.model.IModel;
import de.flapdoodle.embed.process.distribution.BitSize;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class CastileApplication {

    public static void main(String[] args) {
        System.out.println("OS bits (mongo detect): " + BitSize.detect());
        ;
        new SpringApplicationBuilder(CastileApplication.class)
                .headless(false).run(args);
    }

    @Bean
    public JFrame frame(View view) {
        JFrame frame = new JFrame("The Castile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

    @Bean
    public JDialog inventory(JFrame frame, IModel model) {
        JLabel energyText = new JLabel("" + model.getMan().getEnergy());
        JLabel seasonText = new JLabel(model.getSeason().name());
        JLabel inventoryText = new JLabel(inventoryAsText(model));

        model.addModelListaner(e -> {
            energyText.setText("" + model.getMan().getEnergy());
            seasonText.setText(model.getSeason().name());
            inventoryText.setText(inventoryAsText(model));
        });

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Season"));
        panel.add(energyText);
        panel.add(new JLabel("Energy"));
        panel.add(seasonText);
        panel.add(new JLabel("Inventory"));
        panel.add(inventoryText);
        panel.setPreferredSize(new Dimension(400, 100));


        JDialog dialog = new JDialog(frame, "Inventory", true);
        dialog.setModal(false);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.add(panel);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setResizable(false);
        return dialog;
    }

    private String inventoryAsText(IModel model) {
        return model.getMan().getInventory().stream()
                .map(LootMappingDropDto::getId)
                .collect(Collectors.joining(", ", "{", "}"));
    }

}
