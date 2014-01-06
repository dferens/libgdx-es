package com.dferens.core;

import com.dferens.core.entities.Entity;
import com.dferens.core.entities.PhysicsApplied;
import com.dferens.core.entities.Renderable;
import com.dferens.core.entities.Updatable;

import java.util.*;

public abstract class EntityManager implements SettingsProvider {
    private final Map<Entity, Context> contextLookup;
    private final SortedSet<Updatable> updateEntities;
    private final SortedSet<Renderable> renderEntities;
    protected final SettingsProvider settingsProvider;
    protected final GameWorld world;

    @Override
    public Settings getSettings() { return this.settingsProvider.getSettings(); }

    protected EntityManager(SettingsProvider settingsProvider, GameWorld world) {
        this.contextLookup = new HashMap<Entity, Context>();
        this.updateEntities = new TreeSet<Updatable>(new Context.UpdatePriorityComparator(this.contextLookup));
        this.renderEntities = new TreeSet<Renderable>(new Context.RenderPriorityComparator(this.contextLookup));
        this.settingsProvider = settingsProvider;
        this.world = world;
        this.clear();
    }

    public void createEntity(Entity entity) {
        PhysicsBody body = null;
        Integer updatePriority = null, renderPriority = null;

        if (entity instanceof PhysicsApplied)
            body = this.world.createBody((PhysicsApplied) entity);

        if (entity instanceof Updatable) {
            updatePriority = ((Updatable)entity).getUpdatePriority();
            this.updateEntities.add((Updatable) entity);
        }

        if (entity instanceof Renderable) {
            renderPriority = ((Renderable) entity).getRenderPriority();
            this.renderEntities.add((Renderable) entity);
        }

        Context context = new Context(this, body, updatePriority, renderPriority);
        this.contextLookup.put(entity, context);
    }
    public void destroyEntity(Entity entity) {
        Context context = this.contextLookup.remove(entity);

        this.updateEntities.remove(entity);
        this.renderEntities.remove(entity);

        PhysicsBody body = context.getBody();
        if (body != null) body.destroy();
    }
    public void clear() {
        //
        // TODO: prevent calling this method inside update loop etc.
        //
        for (Entity entity : this.contextLookup.keySet()) {
            this.destroyEntity(entity);
        }
    }
    public void updateWorld(float deltaTime) {
        this.world.step(deltaTime);
    }

    public Iterable<Updatable> iterateUpdatables() { return this.updateEntities; }
    public Iterable<Renderable> iterateRenderables() { return this.renderEntities; }
    public Context getContext(Entity entity) { return this.contextLookup.get(entity); }
}
