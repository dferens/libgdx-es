package com.dferens.core;

import java.util.Comparator;
import java.util.Map;

public class GameContext {
    static class RenderComparator implements Comparator<IEntity> {
        private final Map<IEntity, GameContext> map;

        public RenderComparator(Map<IEntity, GameContext> map) {
            this.map = map;
        }
        @Override
        public int compare(IEntity o1, IEntity o2) {
            return Integer.compare(map.get(o1).renderPriority, map.get(o2).renderPriority);
        }
    }
    static class UpdateComparator implements Comparator<IEntity> {
        private final Map<IEntity, GameContext> map;

        public UpdateComparator(Map<IEntity, GameContext> map) {
            this.map = map;
        }
        @Override
        public int compare(IEntity o1, IEntity o2) {
            return Integer.compare(map.get(o1).updatePriority, map.get(o2).updatePriority);
        }
    }
    private PhysicsBody boxBody;
    private int updatePriority;
    private int renderPriority;

    public GameContext(PhysicsBody body) {
        this.boxBody = body;
    }
    public GameContext(PhysicsBody body, int updatePriority, int renderPriority) {
        this(body);
        this.updatePriority = updatePriority;
        this.renderPriority = renderPriority;
    }

    public PhysicsBody getBody() { return this.boxBody; }
}
