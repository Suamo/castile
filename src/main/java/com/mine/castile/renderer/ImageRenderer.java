package com.mine.castile.renderer;

import com.mine.castile.Constants;
import com.mine.castile.dom.enums.Season;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageRenderer {
    private String resource;

    public ImageRenderer(String objectId, String folder) {
        this.resource = String.format("/image/%s/%s.png", folder, objectId);
    }

    public void render(Graphics2D g2, Rectangle rect) {
        try {
            InputStream is = getClass().getResourceAsStream(resource);
            BufferedImage image = ImageIO.read(is);
            int x2 = rect.x + Constants.CELL_WIDTH;
            int y2 = rect.y + Constants.CELL_HEIGHT;
            g2.drawImage(image, rect.x, rect.y, x2, y2, 0, 0,
                    Constants.CELL_WIDTH/2, Constants.CELL_HEIGHT/2, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
