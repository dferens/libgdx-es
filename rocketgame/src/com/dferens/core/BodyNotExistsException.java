package com.dferens.core;


public class BodyNotExistsException extends Exception {
    private IEntity entity;

    public BodyNotExistsException(IEntity entity) {
        super();
        this.entity = entity;
    }

    public IEntity getEntity() { return this.entity; }
}
