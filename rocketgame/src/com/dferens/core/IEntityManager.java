package com.dferens.core;

import java.util.Map;

public interface IEntityManager {
    void createEntity(IEntity entity);
    void destroyEntity(IEntity entity);
    /**
     *
     * @return entities in order they should be updated.
     */
    Iterable<Map.Entry<IEntity,GameContext>> iterateUpdatables();

    /**
     *
     * @return entities in order they should be rendered.
     */
    Iterable<Map.Entry<IEntity,GameContext>> iterateRenderables();

    void updateWorld(float deltaTime);
}
