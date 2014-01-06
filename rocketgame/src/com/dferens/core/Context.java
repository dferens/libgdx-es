package com.dferens.core;

import java.util.Comparator;
import java.util.Map;

public class Context{
    public static class UpdatePriorityComparator implements Comparator<Updatable> {

        private final Map<Entity, Context> lookup;

        UpdatePriorityComparator(Map<Entity, Context> lookup) {
            this.lookup = lookup;
        }
        @Override
        public int compare(Updatable u1, Updatable u2) {
            Context o1 = lookup.get(u1), o2 = lookup.get(u2);
            return Integer.compare(o1.updatePriority, o2.updatePriority);
        }
    }
    public static class RenderPriorityComparator implements Comparator<Renderable> {

        private final Map<Entity, Context> lookup;

        RenderPriorityComparator(Map<Entity, Context> lookup) {
            this.lookup = lookup;
        }
        @Override
        public int compare(Renderable r1, Renderable r2) {
            Context o1 = lookup.get(r1), o2 = lookup.get(r2);
            return Integer.compare(o1.renderPriority, o2.renderPriority);
        }
    }

    private int updatePriority;
    private int renderPriority;

    protected final GameWorld world;
    protected final PhysicsBody boxBody;

    public Context(GameWorld world, PhysicsBody body, Integer updatePriority, Integer renderPriority) {
        this.world = world;
        this.boxBody = body;
        this.updatePriority = updatePriority;
        this.renderPriority = renderPriority;
    }

    public GameWorld getBoxWorld() { return this.world; }
    public PhysicsBody getBody() { return this.boxBody; }
}
