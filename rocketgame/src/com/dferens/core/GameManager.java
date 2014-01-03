package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

import java.util.Iterator;

public abstract class GameManager {
    private IEntityManager IEntityManager;
    private GameRenderer gameRenderer;
    private UIManager uiManager;

    public GameManager(float visibleUnits) {
        this.IEntityManager = new BaseEntityManager();
        this.gameRenderer = new GameRenderer(visibleUnits);
        this.uiManager = this.createUIManager();
    }

    public void process(float deltaTime) {
        this.updateEntities(deltaTime);
        this.render(deltaTime);
    }

    public abstract UIManager createUIManager();

    private void updateEntities(float deltaTime) {
        Iterator<GameContext> entities = IEntityManager.iterateEntities();
        while (entities.hasNext()) {
            GameContext gameContext = entities.next();
            IEntity entity = gameContext.getEntity();
            PhysicsBody body = gameContext.getBody();
            entity.update(deltaTime, body, uiManager);
        }
    }
    private void render(float deltaTime) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        Iterator<GameContext> renderables = IEntityManager.iterateRenderables();
        while (renderables.hasNext()) {
            GameContext gameContext = renderables.next();
            IEntity entity = gameContext.getEntity();
            PhysicsBody body = gameContext.getBody();
            entity.render(deltaTime, body, gameRenderer);
        }
        gameRenderer.end();

        this.uiManager.render(deltaTime);
    }
}
