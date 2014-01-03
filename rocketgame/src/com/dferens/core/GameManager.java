package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;

public abstract class GameManager {
    private final IEntityPriorityResolver priorityResolver;
    private final IEntityManager entityManager;
    private final GameRenderer gameRenderer;
    private final UIManager uiManager;

    public GameManager(Vector2 gravity, float visibleUnits) {
        this.priorityResolver = this.createPriorityResolver();
        this.entityManager = new EntityManager(this.priorityResolver, this.createWorldConfig());
        this.gameRenderer = new GameRenderer(visibleUnits);
        this.uiManager = this.createUIManager();
    }

    public void process(float deltaTime) {
        this.updateEntities(deltaTime);
        this.render(deltaTime);
    }

    public abstract UIManager createUIManager();
    public abstract IEntityPriorityResolver createPriorityResolver();
    public abstract WorldConfig createWorldConfig();

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
        Iterator<GameContext> renderables = entityManager.iterateRenderables();
        while (renderables.hasNext()) {
            GameContext gameContext = renderables.next();
            IRenderable entity = (IRenderable) gameContext.getEntity();
            entity.render(deltaTime, gameContext, gameRenderer);
        }
        gameRenderer.end();
    }
    protected void renderUI(float deltaTime) {
        this.uiManager.render(deltaTime);
    }
    protected void updateEntities(float deltaTime) {
        Iterator<GameContext> entities = entityManager.iterateUpdatables();
        while (entities.hasNext()) {
            GameContext gameContext = entities.next();
            IUpdatable updatableEntity = (IUpdatable) gameContext.getEntity();
            updatableEntity.update(deltaTime, gameContext, this.uiManager);
        }
    }
    protected void updateWorld(float deltaTime) {
        this.entityManager.updateWorld(deltaTime);
    }
}
