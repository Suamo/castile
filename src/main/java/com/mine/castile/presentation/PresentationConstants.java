package com.mine.castile.presentation;

public interface PresentationConstants {
    int CELL_WIDTH = 80;
    int CELL_HEIGHT = 80;
    int VIEW_RADIX = 5;

    int VIEWPORT_WIDTH = (PresentationConstants.VIEW_RADIX * 2 + 1) * PresentationConstants.CELL_WIDTH;
    int VIEWPORT_HEIGHT = (PresentationConstants.VIEW_RADIX * 2 + 1) * PresentationConstants.CELL_HEIGHT;
}
