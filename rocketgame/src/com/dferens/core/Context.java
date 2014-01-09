package com.dferens.core;

import com.dferens.core.entities.Entity;
import com.dferens.core.entities.Renderable;
import com.dferens.core.entities.Updatable;

import java.util.Comparator;
import java.util.Map;

public class Context{
    public static class UpdatePriorityComparator implements Comparator<Updatable> {

        private final Map<Entity, ? extends Context> lookup;

        UpdatePriorityComparator(Map<Entity, ? extends Context> lookup) {
            this.lookup = lookup;
        }

        @Override
        public int compare(Updatable u1, Updatable u2) {
            Context o1 = lookup.get(u1), o2 = lookup.get(u2);
            return Integer.compare(o1.updatePriority, o2.updatePriority);
        }
    }
    public static class RenderPriorityComparator implements Comparator<Renderable> {
        private final Map<Entity, ? extends Context> lookup;

        RenderPriorityComparator(Map<Entity, ? extends Context> lookup) {
            this.lookup = lookup;
        }

        @Override
        public int compare(Renderable r1, Renderable r2) {
            Context o1 = lookup.get(r1), o2 = lookup.get(r2);
            return Integer.compare(o1.renderPriority, o2.renderPriority);
        }
    }
    private final int updatePriority;
    private final int renderPriority;
    private final EntityManager entityManager;
    private PhysicsBody boxBody;

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
