package com.mine.castile;

import com.mine.castile.view.UiPanel;
import de.flapdoodle.embed.process.distribution.BitSize;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

@SpringBootApplication
public class CastileApplication {

    public static void main(String[] args) {
        System.out.println("OS bits (mongo detect): " + BitSize.detect());

        new SpringApplicationBuilder(CastileApplication.class)
                .headless(false).run(args);
    }

    @Bean
    public JFrame frame(UiPanel panel) {
        JFrame frame = new JFrame("The Castile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }

}
