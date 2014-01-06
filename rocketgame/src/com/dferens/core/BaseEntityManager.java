package com.dferens.core;

import java.util.*;

public abstract class BaseEntityManager implements EntityManager {
    private final Map<Entity, Context> contextLookup;
    private final SortedSet<Entity> updateEntities;
    private final SortedSet<Entity> renderEntities;
    private final EntityPriorityResolver priorityResolver;
    private final GameConfigProvider configProvider;
    private GameWorld world;

    public BaseEntityManager(EntityPriorityResolver priorityResolver,
                             GameConfigProvider configProvider) {
        this.contextLookup = new HashMap<Entity, Context>();
        this.updateEntities = new TreeSet<Entity>(new Context.UpdatePriorityComparator(this.contextLookup));
        this.renderEntities = new TreeSet<Entity>(new Context.RenderPriorityComparator(this.contextLookup));
        this.priorityResolver = priorityResolver;
        this.configProvider = configProvider;

        this.init();
    }

    @Override
    public void init() {
        this.world = new GameWorld(configProvider);
    }

    @Override
    public void createEntity(Entity entity) {
        PhysicsBody body = null;
        Integer updatePriority = null, renderPriority = null;

        if (entity instanceof PhysicsApplied)
            body = this.world.createBody((PhysicsApplied) entity);

        if (entity instanceof Updatable)
            updatePriority = priorityResolver.getUpdatePriority((Updatable) entity);

        if (entity instanceof Renderable)
            renderPriority = priorityResolver.getRenderPriority((Renderable) entity);

        Context context = new Context(world, body, updatePriority, renderPriority);
        this.contextLookup.put(entity, context);

        if (updatePriority != null)
            this.updateEntities.add(entity);

        if (renderPriority != null)
            this.renderEntities.add(entity);
    }

    @Override
    public void destroyEntity(Entity entity) {
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
        for (Entity entity : this.contextLookup.keySet()) {
            this.destroyEntity(entity);
        }
        this.world = null;
    }

    @Override
    public Iterable<Entity> iterateUpdatables() { return this.updateEntities; }

    @Override
    public Iterable<Entity> iterateRenderables() { return this.renderEntities; }

    @Override
    public Context getContext(Entity entity) { return this.contextLookup.get(entity); }

    @Override
    public void updateWorld(float deltaTime) {
       this.world.step(deltaTime);
    }
}
