package com.dferens.core;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class EntityManager implements IEntityManager {
    private PriorityQueue<GameContext> entities;

    public EntityManager() {

    }

    @Override
    public void registerEntity(IEntity entity, int priority) {
        // PhysicsBody body = entity.createBody(world);
        // GameContext context = new GameContext(entity, body);
    }

    @Override
    public Iterator<GameContext> iterateEntities() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterator<GameContext> iterateRenderables() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
