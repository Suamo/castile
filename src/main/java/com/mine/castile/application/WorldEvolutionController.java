package com.mine.castile.application;

import com.mine.castile.application.dom.Model;
import com.mine.castile.application.io.CellGenerator;
import com.mine.castile.common.dom.GameObjectDto;
import com.mine.castile.common.events.WorldEvolutionEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

@Controller
public class WorldEvolutionController {
    private Model model;
    private CellGenerator generator;

    public WorldEvolutionController(Model model, CellGenerator generator) {
        this.model = model;
        this.generator = generator;
    }

    @EventListener(WorldEvolutionEvent.class)
    public void onEvent() {

        for (int row = 0; row < model.getRows(); row++) {
            for (int column = 0; column < model.getColumns(); column++) {
                GameObjectDto currentObject = model.get(column, row);
                model.set(column, row, generator.evolveCell(currentObject, model.getSeason()));
            }
        }

    }

}
