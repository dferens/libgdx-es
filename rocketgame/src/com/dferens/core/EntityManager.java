package com.dferens.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class EntityManager implements IEntityManager {
    private SortedSet<GameContext> updateEntities;
    private SortedSet<GameContext> renderEntities;
    private IEntityPriorityResolver priorityResolver;
    private World world;
    private WorldConfig worldConfig;

    public WorldConfig getWorldConfig() { return this.worldConfig; }

    public EntityManager(IEntityPriorityResolver priorityResolver, WorldConfig worldConfig) {
        this.updateEntities = new TreeSet<GameContext>(GameContext.constructUpdateComparator());
        this.renderEntities = new TreeSet<GameContext>(GameContext.constructRenderComparator());
        this.priorityResolver = priorityResolver;
        this.worldConfig = worldConfig;
        this.world = new World(worldConfig.gravity, true);
    }

    @Override
    public void createEntity(IEntity entity) {
        PhysicsBody body = null;
        if (entity instanceof IPhysicsBody) {
            body = ((IPhysicsBody) entity).createBody(this.world);
        }
        int updatePriority = priorityResolver.getUpdatePriority(entity);
        int renderPriority = priorityResolver.getRenderPriority(entity);
        GameContext context = new GameContext(entity, body, updatePriority, renderPriority);

        if (entity instanceof IUpdatable) {
            this.updateEntities.add(context);
        }
        if (entity instanceof IRenderable) {
            this.renderEntities.add(context);
        }
    }

    @Override
    public void destroyEntity(IEntity entity) {
        this.updateEntities.remove(entity);
        this.renderEntities.remove(entity);
    }

    @Override
    public Iterator<GameContext> iterateUpdatables() { return this.updateEntities.iterator(); }
    @Override
    public Iterator<GameContext> iterateRenderables() { return this.renderEntities.iterator(); }

    @Override
    public void updateWorld(float deltaTime) {
       this.world.step(deltaTime, worldConfig.velocityIterations, worldConfig.positionIterations);
    }
}
