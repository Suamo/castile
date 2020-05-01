package com.mine.castile.registry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class CellResources {
    private Resource bush;

    public CellResources(@Value("classpath:image/fall/bush.png") Resource bush) {
        this.bush = bush;
    }

    public Resource getBush() {
        return bush;
    }
}
