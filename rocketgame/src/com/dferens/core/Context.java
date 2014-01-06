package com.dferens.core;

import java.util.Comparator;
import java.util.Map;

public class Context{
    public static class UpdatePriorityComparator implements Comparator<Entity> {

        private final Map<Entity, Context> lookup;

        UpdatePriorityComparator(Map<Entity, Context> lookup) {
            this.lookup = lookup;
        }
        @Override
        public int compare(Entity e1, Entity e2) {
            Context o1 = lookup.get(e1), o2 = lookup.get(e2);
            return Integer.compare(o1.updatePriority, o2.updatePriority);
        }
    }
    public static class RenderPriorityComparator implements Comparator<Entity> {

        private final Map<Entity, Context> lookup;

        RenderPriorityComparator(Map<Entity, Context> lookup) {
            this.lookup = lookup;
        }
        @Override
        public int compare(Entity e1, Entity e2) {
            Context o1 = lookup.get(e1), o2 = lookup.get(e2);
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
