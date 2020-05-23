package com.mine.castile.presentation.view;

import javax.swing.*;
import java.awt.*;

public class GridLayoutDemo extends JFrame {

    public GridLayoutDemo(String name) {
        super(name);
    }

    private static void createAndShowGUI() {
        GridLayoutDemo frame = new GridLayoutDemo("GridLayoutDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(addComponentsToPane());
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(GridLayoutDemo::createAndShowGUI);
    }

    public static JPanel addComponentsToPane() {
        final JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JButton("Button 1"));
        panel.add(new JButton("Button 2"));
        panel.add(new JButton("Button 3"));
        panel.add(new JButton("Long-Named Button 4"));
        panel.add(new JButton("5"));

        return panel;
    }
}
