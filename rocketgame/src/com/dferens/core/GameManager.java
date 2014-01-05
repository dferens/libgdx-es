package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

import java.util.Map;

public abstract class GameManager implements IGameConfigProvider {
    protected final IEntityManager entityManager;
    protected final RenderScope renderScope;
    protected final UIManager uiManager;
    protected final GameConfig config;

    public GameConfig getGameConfig() { return this.config; }

    public GameManager() {
        this.config = this.createGameConfig();
        this.entityManager = this.createEntityManager(this);
        this.renderScope = new RenderScope(config.renderVisibleUnits);
        this.uiManager = this.createUIManager();
    }

    public void process(float deltaTime) {
        this.update(deltaTime);
        this.render(deltaTime);
    }

    protected abstract GameConfig createGameConfig();
    protected abstract UIManager createUIManager();
    protected abstract IEntityManager createEntityManager(IGameConfigProvider configProvider);

    protected void render(float deltaTime) {
        clearScreen();
        renderEntities(deltaTime);
        renderUI(deltaTime);
    }
    protected void update(float deltaTime) {
        updateWorld(deltaTime);
        updateEntities(deltaTime);
    }

    protected void clearScreen() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
    protected void renderEntities(float deltaTime) {
        for (Map.Entry<IEntity, Context> keyValue : entityManager.iterateRenderables()) {
            IRenderable entity = (IRenderable) keyValue.getKey();
            entity.render(deltaTime, keyValue.getValue(), renderScope);
        }
    }
    protected void renderUI(float deltaTime) {
        this.uiManager.render(deltaTime);
    }
    protected void updateEntities(float deltaTime) {
        for (Map.Entry<IEntity, Context> keyValue : entityManager.iterateUpdatables()) {
            IUpdatable entity = (IUpdatable) keyValue.getKey();
            entity.update(deltaTime, keyValue.getValue(), uiManager);
        }
    }
    protected void updateWorld(float deltaTime) {
        this.entityManager.updateWorld(deltaTime);
    }
}
