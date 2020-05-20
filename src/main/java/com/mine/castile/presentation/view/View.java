package com.mine.castile.presentation.view;

import com.mine.castile.application.action.hotkeys.InventoryAction;
import com.mine.castile.application.action.interaction.GatherAction;
import com.mine.castile.application.action.interaction.HitAction;
import com.mine.castile.application.action.movement.DownAction;
import com.mine.castile.application.action.movement.LeftAction;
import com.mine.castile.application.action.movement.RightAction;
import com.mine.castile.application.action.movement.UpAction;
import com.mine.castile.application.listener.RefreshListener;
import com.mine.castile.application.model.Man;
import com.mine.castile.application.model.Model;
import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.data.persistence.MongoRepository;
import com.mine.castile.presentation.PresentationConstants;
import com.mine.castile.presentation.registry.CellRendererRegistry;
import com.mine.castile.presentation.registry.Direction;
import com.mine.castile.presentation.registry.FrameRendererRegistry;
import com.mine.castile.presentation.registry.ManRendererRegistry;
import com.mine.castile.presentation.renderer.ImageRenderer;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static javax.swing.KeyStroke.getKeyStroke;

@Component
public class View extends JComponent {

    private Model model;
    private CellRendererRegistry rendererRegistry;
    private ManRendererRegistry manRendererRegistry;
    private final MongoRepository repository;
    private final InventoryPanel inventoryPanel;

    public View(Model model, CellRendererRegistry rendererRegistry, ManRendererRegistry manRendererRegistry,
                MongoRepository repository, InventoryPanel inventoryPanel) {
        this.model = model;
        this.rendererRegistry = rendererRegistry;
        this.manRendererRegistry = manRendererRegistry;
        this.repository = repository;
        this.inventoryPanel = inventoryPanel;

        Dimension size = getPreferredSize();
        setOpaque(true);
        setBounds(5, 5, (int) size.getWidth(), (int) size.getHeight());

        configureInputs();
        configureListeners();
    }

    @Override
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.fillRect(0, 0, width, height);

        drawMap(g2);
        drawMan(g2);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PresentationConstants.VIEWPORT_WIDTH, PresentationConstants.VIEWPORT_HEIGHT);
    }

    private void configureListeners() {
        model.addModelListaner(new RefreshListener(this));
    }

    private void configureInputs() {
        bindKey(new UpAction(model), "up", KeyEvent.VK_UP, KeyEvent.VK_W);
        bindKey(new LeftAction(model), "left", KeyEvent.VK_LEFT, KeyEvent.VK_A);
        bindKey(new RightAction(model), "right", KeyEvent.VK_RIGHT, KeyEvent.VK_D);
        bindKey(new DownAction(model), "down", KeyEvent.VK_DOWN, KeyEvent.VK_S);
        bindKey(new GatherAction(model, repository), "gather", KeyEvent.VK_SPACE);
        bindKey(new HitAction(model, repository), "hit", KeyEvent.VK_ALT);
        bindKey(new InventoryAction(inventoryPanel), "inventory", KeyEvent.VK_I);
    }

    private void bindKey(AbstractAction action, Object name, int... keys) {
        for (int key : keys) {
            getInputMap().put(getKeyStroke(key, 0, true), name);
        }
        getActionMap().put(name, action);
    }

    private void drawMap(Graphics2D g2) {
        Point location = model.getViewportRect().getLocation();
        for (int row = location.y; row < location.y + 2 * PresentationConstants.VIEW_RADIX + 2; row++) {
            for (int column = location.x; column < location.x + 2 * PresentationConstants.VIEW_RADIX + 2; column++) {
                GameObjectDto cell = model.get(column, row);
                ImageRenderer renderer = rendererRegistry.get(model.getSeason(), cell.get_id());
                int x = (column - location.x) * PresentationConstants.CELL_WIDTH;
                int y = (row - location.y) * PresentationConstants.CELL_HEIGHT;
                Rectangle rect = new Rectangle(x, y, PresentationConstants.CELL_WIDTH, PresentationConstants.CELL_HEIGHT);
                renderer.render(g2, rect);
            }
        }
    }

    private void drawMan(Graphics2D g2) {
        Rectangle rect = new Rectangle(
                PresentationConstants.VIEW_RADIX * PresentationConstants.CELL_WIDTH,
                PresentationConstants.VIEW_RADIX * PresentationConstants.CELL_HEIGHT,
                PresentationConstants.CELL_WIDTH,
                PresentationConstants.CELL_HEIGHT);
        Man man = model.getMan();
        Direction direction = man.getDirection();
        int imageIndex = man.getImageIndex();
        FrameRendererRegistry registry = manRendererRegistry.get(direction);
        ImageRenderer renderer = registry.get(imageIndex);
        renderer.render(g2, rect);
    }
}
