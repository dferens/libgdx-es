package com.dferens.core;

public interface IEntityManager {
    void createEntity(IEntity entity);

    void destroyEntity(IEntity entity);

    /**
     * @return entities in order they should be updated.
     */
    Iterable<IEntity> iterateUpdatables();

    /**
     * @return entities in order they should be rendered.
     */
    Iterable<IEntity> iterateRenderables();

    Context getContext(IEntity entity);

    void updateWorld(float deltaTime);
}
