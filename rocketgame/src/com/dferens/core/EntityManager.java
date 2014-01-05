package com.dferens.core;

import java.util.*;

public abstract class EntityManager implements IEntityManager {
    private final Map<IEntity, Context> contextLookup;
    private final SortedSet<IEntity> updateEntities;
    private final SortedSet<IEntity> renderEntities;
    private final IEntityPriorityResolver priorityResolver;
    private final GameWorld world;
    private final IGameConfigProvider configProvider;

    public EntityManager(IEntityPriorityResolver priorityResolver,
                         IGameConfigProvider configProvider,
                         GameWorld world) {
        this.contextLookup = new HashMap<IEntity, Context>();
        this.updateEntities = new TreeSet<IEntity>(new Context.UpdatePriorityComparator(this.contextLookup));
        this.renderEntities = new TreeSet<IEntity>(new Context.RenderPriorityComparator(this.contextLookup));
        this.priorityResolver = priorityResolver;
        this.configProvider = configProvider;
        this.world = world;
    }

    @Override
    public void createEntity(IEntity entity) {
        PhysicsBody body = null;
        Integer updatePriority = null, renderPriority = null;

        if (entity instanceof IPhysicsBody)
            body = this.world.createBody((IPhysicsBody) entity);

        if (entity instanceof IUpdatable)
            updatePriority = priorityResolver.getUpdatePriority(entity);

        if (entity instanceof IRenderable)
            renderPriority = priorityResolver.getRenderPriority(entity);

        Context context = new Context(world, body, updatePriority, renderPriority);
        this.contextLookup.put(entity, context);

        if (updatePriority != null)
            this.updateEntities.add(entity);

        if (renderPriority != null)
            this.renderEntities.add(entity);
    }

    @Override
    public void destroyEntity(IEntity entity) {
        Context context = this.contextLookup.get(entity);

        this.updateEntities.remove(context);
        this.renderEntities.remove(context);

        PhysicsBody body = context.getBody();
        if (body != null) {
            world.destroyBody(body);
        }
    }

    @Override
    public Iterable<IEntity> iterateUpdatables() {
        return this.updateEntities;
    }

    @Override
    public Iterable<IEntity> iterateRenderables() {
        return this.renderEntities;
    }

    @Override
    public Context getContext(IEntity entity) {
        return this.contextLookup.get(entity);
    }

    @Override
    public void updateWorld(float deltaTime) {
       this.world.step(deltaTime);
    }
}
