package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.dferens.core.entities.Renderable;
import com.dferens.core.entities.Updatable;

public abstract class GameManager implements GameConfigProvider {
    protected final GameWorld world;
    protected final EntityManager entityManager;
    protected final RenderScope renderScope;
    protected final InputScope inputScope;
    protected final GameConfig config;

    public EntityManager getEntityManager() { return this.entityManager; }
    public final GameConfig getGameConfig() { return this.config; }

    public GameManager() {
        this.world = new GameWorld(this);
        this.config = this.createGameConfig();
        this.entityManager = this.createEntityManager(this, world);
        this.renderScope = new RenderScope(config.renderVisibleUnits);
        this.inputScope = this.createUIManager();
    }

    protected abstract GameConfig createGameConfig();
    protected abstract InputScope createUIManager();
    protected abstract EntityManager createEntityManager(GameConfigProvider configProvider, GameWorld world);

    public final void process(float deltaTime) {
        this.update(deltaTime);
        this.render(deltaTime);
    }

    protected final void render(float deltaTime) {
        clearScreen();
        renderEntities(deltaTime);
        renderUI(deltaTime);
        this.renderScope.drawingDone();
    }
    protected final void update(float deltaTime) {
        updateWorld(deltaTime);
        updateEntities(deltaTime);
    }

    protected final void clearScreen() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
    protected void renderEntities(float deltaTime) {
        for (Renderable entity : entityManager.iterateRenderables()) {
            Context context = entityManager.getContext(entity);
            entity.render(deltaTime, context, renderScope);
        }
    }
    protected final void renderUI(float deltaTime) {
        inputScope.render(deltaTime);
    }
    protected void updateWorld(float deltaTime) {
        this.entityManager.updateWorld(deltaTime);
    }
    protected void updateEntities(float deltaTime) {
        for (Updatable entity : entityManager.iterateUpdatables()) {
            Context context = entityManager.getContext(entity);
            entity.update(deltaTime, context, inputScope);
        }
    }
}
