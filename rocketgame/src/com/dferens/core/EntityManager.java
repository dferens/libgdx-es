package com.dferens.core;

import com.badlogic.gdx.physics.box2d.World;

import java.util.*;

public class EntityManager implements IEntityManager {
    private TreeMap<IEntity, GameContext> updateEntities;
    private TreeMap<IEntity, GameContext> renderEntities;
    private IEntityPriorityResolver priorityResolver;
    private World world;
    private GameConfig gameConfig;

    public GameConfig getGameConfig() { return this.gameConfig; }

    public EntityManager(IEntityPriorityResolver priorityResolver, GameConfig gameConfig) {
        HashMap<IEntity, GameContext> updateHashMap = new HashMap<IEntity, GameContext>();
        HashMap<IEntity, GameContext> renderHashMap = new HashMap<IEntity, GameContext>();
        Comparator<IEntity> updateComparator = new GameContext.UpdateComparator(updateHashMap);
        Comparator<IEntity> renderComparator = new GameContext.RenderComparator(renderHashMap);
        this.updateEntities = new TreeMap<IEntity, GameContext>(updateComparator);
        this.renderEntities = new TreeMap<IEntity, GameContext>(renderComparator);
        this.priorityResolver = priorityResolver;
        this.gameConfig = gameConfig;
        this.world = new World(gameConfig.worldGravity, true);
    }

    @Override
    public void createEntity(IEntity entity) {
        PhysicsBody body = null;
        if (entity instanceof IPhysicsBody) {
            body = ((IPhysicsBody) entity).createBody(this.world);
        }
        int updatePriority = priorityResolver.getUpdatePriority(entity);
        int renderPriority = priorityResolver.getRenderPriority(entity);
        GameContext context = new GameContext(body, updatePriority, renderPriority);

        if (entity instanceof IUpdatable) {
            this.updateEntities.put(entity, context);
        }
        if (entity instanceof IRenderable) {
            this.renderEntities.put(entity, context);
        }
    }

    @Override
    public void destroyEntity(IEntity entity) {
        GameContext context = null;
        if (this.updateEntities.containsKey(entity)) {
            context = this.updateEntities.remove(entity);
        }
        if (this.renderEntities.containsKey(entity)) {
            context = this.renderEntities.remove(entity);
        }

        PhysicsBody body = context.getBody();
        if (body != null) {
            body.destroy(world);
        }
    }

    @Override
    public Iterable<Map.Entry<IEntity,GameContext>> iterateUpdatables() {
        return this.updateEntities.entrySet();
    }
    @Override
    public Iterable<Map.Entry<IEntity,GameContext>> iterateRenderables() {
        return this.renderEntities.entrySet();
    }

    @Override
    public void updateWorld(float deltaTime) {
       this.world.step(deltaTime, gameConfig.worldVelocityIterations, gameConfig.worldPositionIterations);
    }
}
