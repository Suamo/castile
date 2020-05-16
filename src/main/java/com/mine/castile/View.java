package com.mine.castile;

import com.mine.castile.action.*;
import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.dom.entity.GameObject;
import com.mine.castile.dom.enums.Season;
import com.mine.castile.listener.RefreshListener;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.registry.*;
import com.mine.castile.renderer.ImageRenderer;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static javax.swing.KeyStroke.getKeyStroke;

@Component
public class View extends JComponent {

    private IModel model;
    private CellRendererRegistry rendererRegistry;

    public View(IModel model, CellRendererRegistry rendererRegistry) {
        this.model = model;
        this.rendererRegistry = rendererRegistry;

        configureInputs();
        configureActions(model);
        configureListeners(model);
    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.fillRect(0, 0, width, height);

        drawMap(g2);
        drawMan(g2);
    }

    public Dimension getPreferredSize() {
        int width = (Constants.VIEW_RADIX * 2 + 1) * Constants.CELL_WIDTH;
        int height = (Constants.VIEW_RADIX * 2 + 1) * Constants.CELL_HEIGHT;

        return new Dimension(width, height);
    }

    protected void configureListeners(IModel model) {
        model.addModelListaner(new RefreshListener(this));
    }

    private void configureInputs() {
        InputMap inputMap = getInputMap();

        inputMap.put(getKeyStroke(KeyEvent.VK_UP, 0), Direction.UP);
        inputMap.put(getKeyStroke(KeyEvent.VK_W, 0), Direction.UP);

        inputMap.put(getKeyStroke(KeyEvent.VK_LEFT, 0), Direction.LEFT);
        inputMap.put(getKeyStroke(KeyEvent.VK_A, 0), Direction.LEFT);

        inputMap.put(getKeyStroke(KeyEvent.VK_RIGHT, 0), Direction.RIGHT);
        inputMap.put(getKeyStroke(KeyEvent.VK_D, 0), Direction.RIGHT);

        inputMap.put(getKeyStroke(KeyEvent.VK_DOWN, 0), Direction.DOWN);
        inputMap.put(getKeyStroke(KeyEvent.VK_S, 0), Direction.DOWN);

        inputMap.put(getKeyStroke(KeyEvent.VK_SPACE, 0), "mark");
    }

    private void configureActions(IModel model) {
        ActionMap actionMap = getActionMap();
        actionMap.put(Direction.UP, new UpAction(model));
        actionMap.put(Direction.LEFT, new LeftAction(model));
        actionMap.put(Direction.RIGHT, new RightAction(model));
        actionMap.put(Direction.DOWN, new DownAction(model));
    }

    private void drawMap(Graphics2D g2) {
        Point location = model.getViewportRect().getLocation();
        for (int row = location.y; row < location.y + 2 * Constants.VIEW_RADIX + 2; row++) {
            for (int column = location.x; column < location.x + 2 * Constants.VIEW_RADIX + 2; column++) {
                GameObjectDto cell = model.get(column, row);
                ImageRenderer renderer = rendererRegistry.get(model.getSeason(), cell.get_id());
                Validate.notNull(renderer, "Renderer for map cell '" + cell.get_id() + "' is not registered");
                int x = (column - location.x) * Constants.CELL_WIDTH;
                int y = (row - location.y) * Constants.CELL_HEIGHT;
                Rectangle rect = new Rectangle(x, y, Constants.CELL_WIDTH, Constants.CELL_HEIGHT);
                renderer.render(g2, rect);
            }
        }
    }

    private void drawMan(Graphics2D g2) {
        Rectangle rect = new Rectangle(
                Constants.VIEW_RADIX * Constants.CELL_WIDTH,
                Constants.VIEW_RADIX * Constants.CELL_HEIGHT,
                Constants.CELL_WIDTH,
                Constants.CELL_HEIGHT);
        Man man = model.getMan();
        Direction direction = man.getDirection();
        int imageIndex = man.getImageIndex();
        FrameRendererRegistry registry = ManRendererRegistry.getInstance().get(direction);
        ImageRenderer renderer = registry.get(imageIndex);
        renderer.render(g2, rect);
    }
}
