package com.mine.castile;

import com.mine.castile.io.MapLoader;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Maze;
import com.mine.castile.model.Model;
import com.mine.castile.registry.Cell;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;

@SpringBootApplication
public class CastileApplication {

    public CastileApplication() {
        initUI();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(CastileApplication.class)
                .headless(false).run(args);
    }

    private void initUI() {
        IModel model = createModel();
        View view = new View(model);

        JFrame frame = new JFrame("The Castile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(view);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private IModel createModel() {
        Cell[][] cells = new MapLoader().load();
        return new Model(new Maze(cells));
    }

}
