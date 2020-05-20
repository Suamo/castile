package com.mine.castile.presentation.renderer;

import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static com.mine.castile.presentation.PresentationConstants.CELL_HEIGHT;
import static com.mine.castile.presentation.PresentationConstants.CELL_WIDTH;

public class ImageRenderer {
    private SpriteSheet spriteSheet;

    private int spriteRow;
    private int spriteColumn;

    public ImageRenderer(Resource resource, int spriteRow, int spriteColumn) {
        this.spriteRow = spriteRow;
        this.spriteColumn = spriteColumn;

        try {
            InputStream is = resource.getInputStream();
            BufferedImage image = ImageIO.read(is);

            spriteSheet = new SpriteSheetBuilder().
                    withSheet(image).
                    withColumns(4).
                    withRows(3).
                    build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageRenderer(Resource resource) {
        this(resource, 0, 0);
    }

    public void render(Graphics2D g2, Rectangle rect) {

        BufferedImage image = spriteSheet.getSprite(spriteRow, spriteColumn);

        // the coordinates of the first corner of the destination rectangle
        int dx1 = rect.x;
        int dy1 = rect.y;

        // the coordinates of the second corner of the destination rectangle
        int dx2 = dx1 + CELL_WIDTH;
        int dy2 = dy1 + CELL_HEIGHT;

        // the coordinates of the first corner of the source rectangle
        int sx1 = 0;
        int sy1 = 0;

        // the coordinates of the second corner of the source rectangle
        int sx2 = CELL_WIDTH / 2;
        int sy2 = CELL_HEIGHT / 2;

        g2.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
    }
}
