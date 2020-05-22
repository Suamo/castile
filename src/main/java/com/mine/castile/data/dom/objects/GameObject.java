package com.mine.castile.data.dom.objects;

import com.mine.castile.data.dom.enums.GameObjectActionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document
public class GameObject {

    private @Id
    String _id;

    private int appearOnStart;
    private String appearInGame;

    private Boolean blocking;

    private Map<GameObjectActionType, List<GameObjectAction>> actions;
    private List<GameObjectEvolution> evolution;

}
