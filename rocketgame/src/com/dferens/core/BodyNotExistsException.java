package com.dferens.core;


public class BodyNotExistsException extends Exception {
    private Updatable entity;

    public BodyNotExistsException(Updatable entity) {
        super();
        this.entity = entity;
    }

    public Updatable getEntity() { return this.entity; }
}
