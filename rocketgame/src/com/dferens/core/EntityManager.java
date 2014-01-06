package com.dferens.core;

public interface EntityManager {
    void init();

    void createEntity(Entity entity);

    void destroyEntity(Entity entity);

    void clear();

    /**
     * @return entities in order they should be updated.
     */
    Iterable<Entity> iterateUpdatables();

    /**
     * @return entities in order they should be rendered.
     */
    Iterable<Entity> iterateRenderables();

    Context getContext(Entity entity);

    void updateWorld(float deltaTime);
}
