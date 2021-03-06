package com.mine.castile.presentation.renderer.sprites;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private final BufferedImage[][] sprites;

    public SpriteSheet(BufferedImage[][] sprites) {
        this.sprites = sprites;
    }

    public BufferedImage getSprite(int state, int progress) {
        return sprites[state][progress];
    }
}
