package com.dferens.core;

public interface EntityPriorityResolver {
    /**
     * Returns entity's render priority, like z-index.
     * Entity with priority 1 will be rendered above entity with priority 0;
     *
     * @param entity
     * @return
     */
    int getRenderPriority(Renderable entity);

    /**
     * Returns entity's update priority, works similar to render priority;
     *
     * @param entity
     * @return
     */
    int getUpdatePriority(Updatable entity);
}
