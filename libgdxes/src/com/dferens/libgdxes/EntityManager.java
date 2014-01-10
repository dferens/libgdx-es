package com.dferens.libgdxes;

import com.dferens.libgdxes.entities.Entity;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.entities.utils.Box2dOverlayEntity;
import com.dferens.libgdxes.entities.utils.FPSLoggerEntity;

import java.util.*;

public abstract class EntityManager implements SettingsProvider {
    private final Map<Entity, Context> contextLookup;
    private final SortedSet<Updatable> updateEntities;
    private final SortedSet<Renderable> renderEntities;
    protected final GameManager gameManager;
    protected final GameWorld world;
    protected FPSLoggerEntity fpsLoggerEntity;
    protected Box2dOverlayEntity boxOverlay;

    @Override
    public Settings getSettings() { return this.gameManager.getSettings(); }
    public GameWorld getWorld() { return  this.world; }

    protected EntityManager(GameManager gameManager, GameWorld world) {
        this.contextLookup = new HashMap<Entity, Context>();
        this.updateEntities = new TreeSet<Updatable>(new Context.UpdatePriorityComparator(this.contextLookup));
        this.renderEntities = new TreeSet<Renderable>(new Context.RenderPriorityComparator(this.contextLookup));
        this.gameManager = gameManager;
        this.world = world;

        this.clear();
    }

    protected void setupDebugEntities() {
        this.fpsLoggerEntity = new FPSLoggerEntity(getSettings().systemFont);
        this.boxOverlay = new Box2dOverlayEntity();
        this.createEntity(this.fpsLoggerEntity);
        this.createEntity(this.boxOverlay);
    }

    public void createEntity(Entity entity) {
        PhysicsBody body = null;
        Integer updatePriority = null, renderPriority = null;

        if (entity instanceof PhysicsApplied)
            body = this.world.createBody((PhysicsApplied) entity);
        if (entity instanceof Updatable)
            updatePriority = ((Updatable)entity).getUpdatePriority();
        if (entity instanceof Renderable)
            renderPriority = ((Renderable) entity).getRenderPriority();

        Context context = new Context(this, body, updatePriority, renderPriority);
        this.contextLookup.put(entity, context);

        if (updatePriority != null) this.updateEntities.add((Updatable) entity);
        if (renderPriority != null) this.renderEntities.add((Renderable) entity);

    }
    public void destroyEntity(Entity entity) {
        Context context = this.contextLookup.get(entity);

        if (entity instanceof Updatable) this.updateEntities.remove((Updatable)entity);
        if (entity instanceof Renderable) this.renderEntities.remove((Renderable)entity);

        PhysicsBody body = context.getBody();
        if (body != null) context.destroyBody();

        this.contextLookup.remove(entity);
    }
    public void clear() {
        // TODO: prevent calling this method inside update loop etc.
        Entity[] entities = new Entity[getNumberOfEntities()];
        this.contextLookup.keySet().toArray(entities);
        for (Entity entity : entities) {
            this.destroyEntity(entity);
        }

        if (getSettings().debugModeOn)
            this.setupDebugEntities();
    }

    public void updateWorld(float deltaTime) {
        this.world.step(deltaTime);
    }
    public Collection<Updatable> iterateUpdatables() { return this.updateEntities; }
    public Collection<Renderable> iterateRenderables() { return this.renderEntities; }
    public int getNumberOfEntities() { return this.contextLookup.keySet().size(); }

    public Context getContext(Entity entity) { return this.contextLookup.get(entity); }
}