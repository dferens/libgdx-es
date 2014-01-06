package com.dferens.core;

import java.util.*;

public abstract class EntityManager implements IEntityManager {
    private final Map<IEntity, Context> contextLookup;
    private final SortedSet<IEntity> updateEntities;
    private final SortedSet<IEntity> renderEntities;
    private final IEntityPriorityResolver priorityResolver;
    private final IGameConfigProvider configProvider;
    private GameWorld world;

    public EntityManager(IEntityPriorityResolver priorityResolver,
                         IGameConfigProvider configProvider) {
        this.contextLookup = new HashMap<IEntity, Context>();
        this.updateEntities = new TreeSet<IEntity>(new Context.UpdatePriorityComparator(this.contextLookup));
        this.renderEntities = new TreeSet<IEntity>(new Context.RenderPriorityComparator(this.contextLookup));
        this.priorityResolver = priorityResolver;
        this.configProvider = configProvider;

        this.init();
    }

    @Override
    public void init() {
        this.world = new GameWorld(configProvider);
    }

    @Override
    public void createEntity(IEntity entity) {
        PhysicsBody body = null;
        Integer updatePriority = null, renderPriority = null;

        if (entity instanceof IPhysicsBody)
            body = this.world.createBody((IPhysicsBody) entity);

        if (entity instanceof IUpdatable)
            updatePriority = priorityResolver.getUpdatePriority((IUpdatable) entity);

        if (entity instanceof IRenderable)
            renderPriority = priorityResolver.getRenderPriority((IRenderable) entity);

        Context context = new Context(world, body, updatePriority, renderPriority);
        this.contextLookup.put(entity, context);

        if (updatePriority != null)
            this.updateEntities.add(entity);

        if (renderPriority != null)
            this.renderEntities.add(entity);
    }

    @Override
    public void destroyEntity(IEntity entity) {
        Context context = this.contextLookup.remove(entity);

        this.updateEntities.remove(context);
        this.renderEntities.remove(context);

        PhysicsBody body = context.getBody();
        if (body != null) {
            world.destroyBody(body);
        }
    }

    @Override
    public void clear() {
        //
        // TODO: prevent calling this method inside update loop etc.
        //
        for (IEntity entity : this.contextLookup.keySet()) {
            this.destroyEntity(entity);
        }
        this.world = null;
    }

    @Override
    public Iterable<IEntity> iterateUpdatables() { return this.updateEntities; }

    @Override
    public Iterable<IEntity> iterateRenderables() { return this.renderEntities; }

    @Override
    public Context getContext(IEntity entity) { return this.contextLookup.get(entity); }

    @Override
    public void updateWorld(float deltaTime) {
       this.world.step(deltaTime);
    }
}
