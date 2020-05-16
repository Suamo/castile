package com.mine.castile;

import com.mine.castile.listener.IModelListener;
import com.mine.castile.listener.ModelEvent;
import com.mine.castile.model.IModel;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class CastileApplication {

    public static void main(String[] args) {
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
        JLabel energyLaber = new JLabel("" + model.getMan().getEnergy());

        model.addModelListaner(new IModelListener() {
            @Override
            public void modelChanged(ModelEvent e) {
                energyLaber.setText("" + model.getMan().getEnergy());
            }
        });

        JPanel panel = new JPanel(new GridLayout(0,2));
        panel.add(new JLabel("Energy"));
        panel.add(energyLaber);
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

}
