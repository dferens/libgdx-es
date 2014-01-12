package com.dferens.libgdxes;

public class Context{
    private final int updatePriority;
    private final int renderPriority;
    private final EntityManager entityManager;
    private PhysicsBody boxBody;

    public int getUpdatePriority() { return this.updatePriority; }
    public int getRenderPriority() { return this.renderPriority; }

    public Context(EntityManager entityManager, PhysicsBody body, Integer updatePriority, Integer renderPriority) {
        if (updatePriority == null) updatePriority = -1;
        if (renderPriority == null) renderPriority = -1;

        this.entityManager = entityManager;
        this.boxBody = body;
        this.updatePriority = updatePriority;
        this.renderPriority = renderPriority;
    }

    public EntityManager getEntityManager() { return this.entityManager; }
    public PhysicsBody getBody() { return this.boxBody; }
    public void destroyBody() {
        this.boxBody.destroy();
        this.boxBody = null;
    }
}
