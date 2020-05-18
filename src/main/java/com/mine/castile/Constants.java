package com.mine.castile;

public interface Constants {
    int CELL_WIDTH = 80;
    int CELL_HEIGHT = 80;
    int VIEW_RADIX = 5;

    int VIEWPORT_WIDTH = (Constants.VIEW_RADIX * 2 + 1) * Constants.CELL_WIDTH;
    int VIEWPORT_HEIGHT = (Constants.VIEW_RADIX * 2 + 1) * Constants.CELL_HEIGHT;
}
