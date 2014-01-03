package com.dferens.core;

public class GameContext {

    private IEntity entity;
    private PhysicsBody boxBody;
    private int updatePriority;
    private int renderPriority;

    public GameContext(IEntity entity, PhysicsBody body) {
        this.entity = entity;
        this.boxBody = body;
    }
    public GameContext(IEntity entity, PhysicsBody body, int updatePriority, int renderPriority) {
        this(entity, body);
        this.updatePriority = updatePriority;
        this.renderPriority = renderPriority;
    }

    public IEntity getEntity() { return this.entity; }
    public PhysicsBody getBody() { return this.boxBody; }
    public int getUpdatePriority() { return this.updatePriority; }
    public int getRenderPriority() { return this.renderPriority; }
}
