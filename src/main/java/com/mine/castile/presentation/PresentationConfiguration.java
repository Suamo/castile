package com.mine.castile.presentation;

import com.mine.castile.presentation.view.UiPanel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.awt.*;

@Configuration
public class PresentationConfiguration {

    @Value("${game.fullscreen}")
    private boolean isFullscreen;

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
