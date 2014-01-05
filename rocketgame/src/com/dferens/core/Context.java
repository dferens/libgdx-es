package com.dferens.core;

import java.util.Comparator;
import java.util.Map;

public class Context{
    static class RenderPriorityComparator implements Comparator<IEntity> {
        private final Map<IEntity, Context> map;

        public RenderPriorityComparator(Map<IEntity, Context> map) {
            this.map = map;
        }
        @Override
        public int compare(IEntity o1, IEntity o2) {
            return Integer.compare(map.get(o1).renderPriority, map.get(o2).renderPriority);
        }
    }
    static class UpdatePriorityComparator implements Comparator<IEntity> {
        private final Map<IEntity, Context> map;

        public UpdatePriorityComparator(Map<IEntity, Context> map) {
            this.map = map;
        }
        @Override
        public int compare(IEntity o1, IEntity o2) {
            return Integer.compare(map.get(o1).updatePriority, map.get(o2).updatePriority);
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
