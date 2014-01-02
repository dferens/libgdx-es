package com.dferens.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BaseEntityManager implements IEntityManager {
    protected List<ActiveObject> entities;

    public BaseEntityManager() {
        this.entities = new LinkedList<ActiveObject>();
    }
    @Override
    public Iterator<ActiveObject> iterateEntities() {
        return entities.iterator();
    }

    @Override
    public Iterator<ActiveObject> iterateRenderables() {
        return entities.iterator();
    }
}
