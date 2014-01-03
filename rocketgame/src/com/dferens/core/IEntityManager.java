package com.dferens.core;

import java.util.Iterator;

public interface IEntityManager {
    void createEntity(IEntity entity);
    void destroyEntity(IEntity entity);
    /**
     *
     * @return entities in order they should be updated.
     */
    Iterator<GameContext> iterateUpdatables();

    /**
     *
     * @return entities in order they should be rendered.
     */
    Iterator<GameContext> iterateRenderables();

    void updateWorld(float deltaTime);
}
