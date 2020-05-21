package com.mine.castile.presentation.view;

import com.mine.castile.application.dom.Man;
import com.mine.castile.application.dom.Model;
import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.common.enums.Direction;
import com.mine.castile.common.enums.ObjectInteraction;
import com.mine.castile.common.events.ApplicationEvent;
import com.mine.castile.common.events.CharacterMoveEvent;
import com.mine.castile.common.events.InventoryRequestEvent;
import com.mine.castile.common.events.ObjectInteractionEvent;
import com.mine.castile.presentation.PresentationConstants;
import com.mine.castile.presentation.registry.CellRendererRegistry;
import com.mine.castile.presentation.registry.FrameRendererRegistry;
import com.mine.castile.presentation.registry.ManRendererRegistry;
import com.mine.castile.presentation.renderer.ImageRenderer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static java.awt.event.KeyEvent.*;
import static javax.swing.KeyStroke.getKeyStroke;

@Component
public class View extends JComponent implements ApplicationEventPublisherAware {

    private Model model;
    private CellRendererRegistry rendererRegistry;
    private ManRendererRegistry manRendererRegistry;
    private ApplicationEventPublisher eventPublisher;

    public View(Model model, CellRendererRegistry rendererRegistry, ManRendererRegistry manRendererRegistry) {
        this.model = model;
        this.rendererRegistry = rendererRegistry;
        this.manRendererRegistry = manRendererRegistry;

        Dimension size = getPreferredSize();
        setOpaque(true);
        setBounds(5, 5, (int) size.getWidth(), (int) size.getHeight());

        configureInputs();
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

    private void configureInputs() {
        bindKey(new CharacterMoveEvent(Direction.UP), VK_UP, VK_W);
        bindKey(new CharacterMoveEvent(Direction.LEFT), VK_LEFT, VK_A);
        bindKey(new CharacterMoveEvent(Direction.RIGHT), VK_RIGHT, VK_D);
        bindKey(new CharacterMoveEvent(Direction.DOWN), VK_DOWN, VK_S);
        bindKey(new ObjectInteractionEvent(ObjectInteraction.GATHER), VK_SPACE);
        bindKey(new ObjectInteractionEvent(ObjectInteraction.HIT), VK_ALT);
        bindKey(new InventoryRequestEvent(), VK_I);
    }

    private void bindKey(ApplicationEvent event, int... keys) {
        for (int key : keys) {
            getInputMap().put(getKeyStroke(key, 0, true), event.getId());
        }
        getActionMap().put(event.getId(), new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventPublisher.publishEvent(event);
            }
        });
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

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void refresh() {
        paintImmediately(0, 0, getWidth(), getHeight()); // instead of async repaint()
    }
}
