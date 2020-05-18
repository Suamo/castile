package com.mine.castile;

import com.mine.castile.view.UiPanel;
import de.flapdoodle.embed.process.distribution.BitSize;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
public class CastileApplication {

    @Value("${game.fullscreen}")
    private boolean isFullscreen;

    public static void main(String[] args) {
        System.out.println("OS bits (mongo detect): " + BitSize.detect());

        new SpringApplicationBuilder(CastileApplication.class)
                .headless(false).run(args);
    }

    @Bean
    public JFrame frame(UiPanel panel) {
        JFrame frame = new JFrame("The Castile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (isFullscreen) {
            JPanel wrapper = new JPanel();
            wrapper.add(panel, Component.CENTER_ALIGNMENT);
            wrapper.setBackground(Color.DARK_GRAY);
            frame.setContentPane(wrapper);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        } else {
            frame.setContentPane(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
        }
        frame.setResizable(false);
        frame.setVisible(true);
        return frame;
    }

}
