package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector3;

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
        Iterator<ActiveObject> entities = IEntityManager.iterateEntities();
        while (entities.hasNext()) {
            ActiveObject activeObject = entities.next();
            IEntity entity = activeObject.getEntity();
            PhysicsBody body = activeObject.getBody();
            entity.update(deltaTime, body, uiManager);
        }
    }
    private void render(float deltaTime) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        Iterator<ActiveObject> renderables = IEntityManager.iterateRenderables();
        while (renderables.hasNext()) {
            ActiveObject activeObject = renderables.next();
            IEntity entity = activeObject.getEntity();
            PhysicsBody body = activeObject.getBody();
            entity.render(deltaTime, body, gameRenderer);
        }
        gameRenderer.end();

        this.uiManager.render(deltaTime);
    }
}
