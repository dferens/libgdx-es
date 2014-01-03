package com.dferens.core;

import java.util.Comparator;

public class GameContext {
    public static Comparator<GameContext> constructUpdateComparator() {
        return new Comparator<GameContext>() {
            @Override
            public int compare(GameContext o1, GameContext o2) {
                return Integer.compare(o1.updatePriority, o2.updatePriority);
            }
        };
    }
    public static Comparator<GameContext> constructRenderComparator() {
        return new Comparator<GameContext>() {
            @Override
            public int compare(GameContext o1, GameContext o2) {
                return Integer.compare(o1.renderPriority, o2.renderPriority);
            }
        };
    }

    private IEntity entity;
    private PhysicsBody boxBody;
    private int updatePriority;
    private int renderPriority;

    public GameContext(IEntity entity, PhysicsBody body) {
        this.entity = entity;
        this.boxBody = body;
    }
    public GameContext(IEntity entity, PhysicsBody body, int updatePriority, int renderPriority) {
        this(entity, body);
        this.updatePriority = updatePriority;
        this.renderPriority = renderPriority;
    }

    public IEntity getEntity() { return this.entity; }
    public PhysicsBody getBody() { return this.boxBody; }
}
