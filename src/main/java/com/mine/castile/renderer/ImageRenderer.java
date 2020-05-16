package com.mine.castile.renderer;

import com.mine.castile.Constants;
import org.springframework.core.io.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageRenderer {
    private final BufferedImage image;

    public ImageRenderer(String objectId, String folder, ResourceLoader resourceLoader) {
        String resource = String.format("image/%s/%s.png", folder, objectId);
        try {
            InputStream is;
            if (new File(resource).exists()) {
                is = new FileInputStream(resource);
            } else {
                is = resourceLoader.getResource("classpath:" + resource).getInputStream();
            }
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void render(Graphics2D g2, Rectangle rect) {
        int x2 = rect.x + Constants.CELL_WIDTH;
        int y2 = rect.y + Constants.CELL_HEIGHT;
        g2.drawImage(image, rect.x, rect.y, x2, y2, 0, 0,
                Constants.CELL_WIDTH / 2, Constants.CELL_HEIGHT / 2, null);
    }
}
