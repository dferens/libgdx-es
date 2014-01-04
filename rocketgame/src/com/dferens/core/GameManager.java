package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

import java.util.Iterator;
import java.util.Map;

public abstract class GameManager {
    private final IEntityPriorityResolver priorityResolver;
    private final IEntityManager entityManager;
    private final GameRenderer gameRenderer;
    private final UIManager uiManager;

    public GameManager(GameConfig config) {
        this.priorityResolver = this.createPriorityResolver();
        this.entityManager = new EntityManager(this.priorityResolver, config);
        this.gameRenderer = new GameRenderer(config.renderVisibleUnits);
        this.uiManager = this.createUIManager();
    }

    public void process(float deltaTime) {
        this.updateEntities(deltaTime);
        this.render(deltaTime);
    }

    public abstract UIManager createUIManager();
    public abstract IEntityPriorityResolver createPriorityResolver();
    public abstract GameConfig createGameConfig();

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
        for (Map.Entry<IEntity, GameContext> keyValue : entityManager.iterateRenderables()) {
            IRenderable entity = (IRenderable) keyValue.getKey();
            entity.render(deltaTime, keyValue.getValue(), gameRenderer);
        }
    }
    protected void renderUI(float deltaTime) {
        this.uiManager.render(deltaTime);
    }
    protected void updateEntities(float deltaTime) {
        for (Map.Entry<IEntity, GameContext> keyValue : entityManager.iterateUpdatables()) {
            IUpdatable entity = (IUpdatable) keyValue.getKey();
            entity.update(deltaTime, keyValue.getValue(), uiManager);
        }
    }
    protected void updateWorld(float deltaTime) {
        this.entityManager.updateWorld(deltaTime);
    }
}
