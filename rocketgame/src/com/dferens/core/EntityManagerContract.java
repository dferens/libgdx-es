package com.dferens.core;

public interface EntityManagerContract {
    void init();

    void createEntity(Entity entity);

    void destroyEntity(Entity entity);

    void clear();

    /**
     * @return entities in order they should be updated.
     */
    Iterable<Updatable> iterateUpdatables();

    /**
     * @return entities in order they should be rendered.
     */
    Iterable<Renderable> iterateRenderables();

    Context getContext(Entity entity);

    void updateWorld(float deltaTime);
}
