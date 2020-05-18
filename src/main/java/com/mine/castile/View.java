package com.mine.castile;

import com.mine.castile.action.GatherAction;
import com.mine.castile.action.HitAction;
import com.mine.castile.action.movement.DownAction;
import com.mine.castile.action.movement.LeftAction;
import com.mine.castile.action.movement.RightAction;
import com.mine.castile.action.movement.UpAction;
import com.mine.castile.dom.dto.GameObjectDto;
import com.mine.castile.listener.RefreshListener;
import com.mine.castile.model.IModel;
import com.mine.castile.model.Man;
import com.mine.castile.persistence.MongoRepository;
import com.mine.castile.registry.CellRendererRegistry;
import com.mine.castile.registry.Direction;
import com.mine.castile.registry.FrameRendererRegistry;
import com.mine.castile.registry.ManRendererRegistry;
import com.mine.castile.renderer.ImageRenderer;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static javax.swing.KeyStroke.getKeyStroke;

@Component
public class View extends JComponent {

    private IModel model;
    private CellRendererRegistry rendererRegistry;
    private ManRendererRegistry manRendererRegistry;

    public View(IModel model, CellRendererRegistry rendererRegistry,
                ManRendererRegistry manRendererRegistry, MongoRepository repository) {
        this.model = model;
        this.rendererRegistry = rendererRegistry;
        this.manRendererRegistry = manRendererRegistry;

        cacheMap();
        configureInputs();
        configureActions(model, repository);
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

        inputMap.put(getKeyStroke(KeyEvent.VK_UP, 0, true), Direction.UP);
        inputMap.put(getKeyStroke(KeyEvent.VK_W, 0, true), Direction.UP);

        inputMap.put(getKeyStroke(KeyEvent.VK_LEFT, 0, true), Direction.LEFT);
        inputMap.put(getKeyStroke(KeyEvent.VK_A, 0, true), Direction.LEFT);

        inputMap.put(getKeyStroke(KeyEvent.VK_RIGHT, 0, true), Direction.RIGHT);
        inputMap.put(getKeyStroke(KeyEvent.VK_D, 0, true), Direction.RIGHT);

        inputMap.put(getKeyStroke(KeyEvent.VK_DOWN, 0, true), Direction.DOWN);
        inputMap.put(getKeyStroke(KeyEvent.VK_S, 0, true), Direction.DOWN);

        inputMap.put(getKeyStroke(KeyEvent.VK_SPACE, 0, true), "gather");
        inputMap.put(getKeyStroke(KeyEvent.VK_ALT, 0, true), "hit");
    }

    private void configureActions(IModel model, MongoRepository repository) {
        ActionMap actionMap = getActionMap();
        actionMap.put(Direction.UP, new UpAction(model));
        actionMap.put(Direction.LEFT, new LeftAction(model));
        actionMap.put(Direction.RIGHT, new RightAction(model));
        actionMap.put(Direction.DOWN, new DownAction(model));
        actionMap.put("gather", new GatherAction(model, repository));
        actionMap.put("hit", new HitAction(model, repository));
    }

    private void cacheMap() {
//        for (int row = 0; row < model.getRows(); row++) {
//            for (int column = 0; column < model.getColumns(); column++) {
//                GameObjectDto cell = model.get(column, row);
//                ImageRenderer renderer = rendererRegistry.get(model.getSeason(), cell.get_id());
//                int x = (column - location.x) * Constants.CELL_WIDTH;
//                int y = (row - location.y) * Constants.CELL_HEIGHT;
//                Rectangle rect = new Rectangle(x, y, Constants.CELL_WIDTH, Constants.CELL_HEIGHT);
//                renderer.render(g2, rect);
//            }
//        }
    }

    private void drawMap(Graphics2D g2) {
        Point location = model.getViewportRect().getLocation();
        for (int row = location.y; row < location.y + 2 * Constants.VIEW_RADIX + 2; row++) {
            for (int column = location.x; column < location.x + 2 * Constants.VIEW_RADIX + 2; column++) {
                GameObjectDto cell = model.get(column, row);
                ImageRenderer renderer = rendererRegistry.get(model.getSeason(), cell.get_id());
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
        FrameRendererRegistry registry = manRendererRegistry.get(direction);
        ImageRenderer renderer = registry.get(imageIndex);
        renderer.render(g2, rect);
    }
}
