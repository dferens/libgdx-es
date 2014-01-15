package com.dferens.libgdxes;

import com.dferens.libgdxes.entities.Entity;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.entities.utils.Box2dOverlayEntity;
import com.dferens.libgdxes.entities.utils.FPSLoggerEntity;
import com.dferens.libgdxes.render.AssetStorage;

import java.util.*;

public abstract class EntityManager implements SettingsProvider, Scope {
    private final Map<Entity, Context> contextLookup;
    private final SortedSet<Updatable> updateEntities;
    private final SortedSet<Renderable> renderEntities;
    protected final Collection<Entity> ownEntities;
    protected final GameManager gameManager;
    protected final GameWorld world;
    protected FPSLoggerEntity fpsLoggerEntity;
    protected Box2dOverlayEntity boxOverlay;

    @Override
    public Settings getSettings() { return this.gameManager.getSettings(); }
    public GameWorld getWorld() { return  this.world; }
    public GameManager getGameManager() { return this.gameManager; }

    protected EntityManager(GameManager gameManager, GameWorld world) {
        this.contextLookup = new HashMap<Entity, Context>();
        this.updateEntities = new TreeSet<Updatable>(new PriorityComparator<Updatable>(this.contextLookup) {
            @Override
            protected int getPriority(Context c) { return c.getUpdatePriority(); }
        });
        this.renderEntities = new TreeSet<Renderable>(new PriorityComparator<Renderable>(this.contextLookup) {
            @Override
            protected int getPriority(Context c) { return c.getRenderPriority(); }
        });
        this.ownEntities = new LinkedList<Entity>();
        this.gameManager = gameManager;
        this.world = world;
    }

    @Override
    public void initialize() {
        this.clear();
    }

    public void clear() {
        // TODO: prevent calling this method inside update loop etc.
        Entity[] entities = new Entity[getNumberOfEntities()];
        this.contextLookup.keySet().toArray(entities);
        for (Entity entity : entities) {
            if (this.ownEntities.contains(entity) == false) {
                this.destroyEntity(entity);
            }
        }
        setupOwnEntities();
    }

    protected void finishAssetsLoading() {
        this.gameManager.getAssetStorage().finishLoading();
    }

    protected void setupOwnEntities() {
        this.setupStandardEntities();

        if (getSettings().debugModeOn) {
            this.setupDebugEntities();
        }
    }
    protected void setupStandardEntities() {

    }

    protected void setupDebugEntities() {
        if (this.fpsLoggerEntity == null) {
            this.fpsLoggerEntity = new FPSLoggerEntity(getSettings().systemFont);
            this.ownEntities.add(fpsLoggerEntity);
            this.createEntity(this.fpsLoggerEntity);
        }
        if (this.boxOverlay == null) {
            this.boxOverlay = new Box2dOverlayEntity();
            this.ownEntities.add(boxOverlay);
            this.createEntity(this.boxOverlay);
        }
    }

    public void createEntity(Entity entity) {
        PhysicsBody[] bodies = null;
        Integer updatePriority = null, renderPriority = null;
        String[] assets = null;

        if (entity instanceof PhysicsApplied)
            bodies = this.world.createBodies((PhysicsApplied) entity);
        if (entity instanceof Updatable)
            updatePriority = ((Updatable)entity).getUpdatePriority();
        if (entity instanceof Renderable) {
            renderPriority = ((Renderable) entity).getRenderPriority();
            AssetStorage assetStorage = this.gameManager.getAssetStorage();
            Collection<String> assetsCollection = assetStorage.loadEntitiesAssets((Renderable) entity);
            if (assets != null) {
                assets = new String[assetsCollection.size()];
                assetsCollection.toArray(assets);
            }
        }
        Context context = new Context(this, updatePriority, renderPriority, bodies, assets);
        this.contextLookup.put(entity, context);

        if (updatePriority != null) this.updateEntities.add((Updatable) entity);
        if (renderPriority != null) this.renderEntities.add((Renderable) entity);
    }
    public void createEntities(Entity... entities) {
        for (Entity entity : entities) this.createEntity(entity);
    }
    public void destroyEntity(Entity entity) {
        Context context = this.contextLookup.get(entity);
        AssetStorage assetStorage = this.gameManager.getAssetStorage();

        if (entity instanceof Updatable) this.updateEntities.remove(entity);
        if (entity instanceof Renderable) this.renderEntities.remove(entity);

        context.destroyBodies();
        context.unloadAssets(assetStorage);
        this.contextLookup.remove(entity);
    }
    public void destroyEntities(Entity... entities) {
        for (Entity entity : entities) this.destroyEntity(entity);
    }
    public void updateWorld(float deltaTime) {
        this.world.step(deltaTime);
    }
    public Collection<Updatable> iterateUpdatables() { return this.updateEntities; }
    public Collection<Renderable> iterateRenderables() { return this.renderEntities; }
    public int getNumberOfEntities() { return this.contextLookup.keySet().size(); }

    public Context getContext(Entity entity) { return this.contextLookup.get(entity); }
}
