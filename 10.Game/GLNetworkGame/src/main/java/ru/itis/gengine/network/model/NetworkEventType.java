package ru.itis.gengine.network.model;

import java.io.Serializable;

public enum NetworkEventType implements Serializable {
    WORLD_SNAPSHOT,
    INITIALIZE_WORLD_HOST,
    INITIALIZE_WORLD_CLIENT,
    COMPONENT_STATE,
    ENTITY_STATE,
    DESTROY_ENTITY,
    END
}
