package com.dferens.core;

public class ActiveObject {

    private IEntity entity;
    private PhysicsBody boxBody;

    private ActiveObject(IEntity entity, PhysicsBody body) {
        this.entity = entity;
        this.boxBody = body;
    }

    public IEntity getEntity() { return this.entity; }
    public PhysicsBody getBody() { return this.boxBody; }
}
