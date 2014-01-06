package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public abstract class GameManager implements IGameConfigProvider {
    protected final IEntityManager entityManager;
    protected final RenderScope renderScope;
    protected final UIManager uiManager;
    protected final GameConfig config;

    public final GameConfig getGameConfig() { return this.config; }

    public GameManager() {
        this.config = this.createGameConfig();
        this.entityManager = this.createEntityManager(this);
        this.renderScope = new RenderScope(config.renderVisibleUnits);
        this.uiManager = this.createUIManager();
    }

    protected abstract GameConfig createGameConfig();
    protected abstract UIManager createUIManager();
    protected abstract IEntityManager createEntityManager(IGameConfigProvider configProvider);

    public final void process(float deltaTime) {
        this.update(deltaTime);
        this.render(deltaTime);
    }
    protected final void render(float deltaTime) {
        clearScreen();
        renderAll(deltaTime);
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
    protected void renderAll(float deltaTime) {
        for (IEntity entity : entityManager.iterateRenderables()) {
            IRenderable renderableEntity = (IRenderable) entity;
            Context context = entityManager.getContext(entity);
            renderableEntity.render(deltaTime, context, renderScope);
        }
    }
    protected final void renderUI(float deltaTime) {
        uiManager.render(deltaTime);
    }
    protected void updateWorld(float deltaTime) {
        this.entityManager.updateWorld(deltaTime);
    }
    protected void updateEntities(float deltaTime) {
        for (IEntity entity : entityManager.iterateUpdatables()) {
            IUpdatable updatableEntity = (IUpdatable) entity;
            Context context = entityManager.getContext(entity);
            updatableEntity.update(deltaTime, context, uiManager);
        }
    }
}
