package com.mine.castile.common.events;

import com.mine.castile.common.enums.ObjectInteraction;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ObjectInteractionEvent implements ApplicationEvent {
    private ObjectInteraction objectInteraction;

    @Override
    public String getId() {
        return objectInteraction.name();
    }
}
