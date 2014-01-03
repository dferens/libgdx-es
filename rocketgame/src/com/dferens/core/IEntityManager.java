package com.dferens.core;

import java.util.Iterator;

public interface IEntityManager {
    void registerEntity(IEntity entity, int priority);
    /**
     *
     * @return entities in order they should be updated.
     */
    Iterator<GameContext> iterateEntities();

    /**
     *
     * @return entities in order they should be rendered.
     */
    Iterator<GameContext> iterateRenderables();
}
