package com.dferens.core;


public class BodyNotExistsException extends Exception {
    private IUpdatable entity;

    public BodyNotExistsException(IUpdatable entity) {
        super();
        this.entity = entity;
    }

    public IUpdatable getEntity() { return this.entity; }
}
