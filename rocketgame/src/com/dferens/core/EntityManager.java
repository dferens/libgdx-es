package com.dferens.core;

import java.util.*;

public abstract class EntityManager implements IEntityManager {
    private final TreeMap<IEntity, Context> updateEntities;
    private final TreeMap<IEntity, Context> renderEntities;
    private final IEntityPriorityResolver priorityResolver;
    private final GameWorld world;
    private final IGameConfigProvider configProvider;

    public EntityManager(IEntityPriorityResolver priorityResolver, IGameConfigProvider configProvider) {
        HashMap<IEntity, Context> updateHashMap = new HashMap<IEntity, Context>();
        HashMap<IEntity, Context> renderHashMap = new HashMap<IEntity, Context>();
        Comparator<IEntity> updateComparator = new Context.UpdatePriorityComparator(updateHashMap);
        Comparator<IEntity> renderComparator = new Context.RenderPriorityComparator(renderHashMap);
        this.updateEntities = new TreeMap<IEntity, Context>(updateComparator);
        this.renderEntities = new TreeMap<IEntity, Context>(renderComparator);
        this.priorityResolver = priorityResolver;
        this.configProvider = configProvider;
        this.world = new GameWorld(this.configProvider);
    }

    @Override
    public void createEntity(IEntity entity) {
        PhysicsBody body = null;
        if (entity instanceof IPhysicsBody) {
            body = this.world.createBody((IPhysicsBody) entity);
        }
        Context context = new Context(world, body);

        if (entity instanceof IUpdatable) {
            context.setUpdatePriority(priorityResolver.getUpdatePriority(entity));
            this.updateEntities.put(entity, context);
        }
        if (entity instanceof IRenderable) {
            context.setRenderPriority(priorityResolver.getRenderPriority(entity));
            this.renderEntities.put(entity, context);
        }
    }

    @Override
    public void destroyEntity(IEntity entity) {
        Context context = null;
        if (this.updateEntities.containsKey(entity)) {
            context = this.updateEntities.remove(entity);
        }
        if (this.renderEntities.containsKey(entity)) {
            context = this.renderEntities.remove(entity);
        }

        PhysicsBody body = context.getBody();
        if (body != null) {
            world.destroyBody(body);
        }
    }

    @Override
    public Iterable<Map.Entry<IEntity,Context>> iterateUpdatables() {
        return this.updateEntities.entrySet();
    }
    @Override
    public Iterable<Map.Entry<IEntity,Context>> iterateRenderables() {
        return this.renderEntities.entrySet();
    }

    @Override
    public void updateWorld(float deltaTime) {
       this.world.step(deltaTime);
    }
}
