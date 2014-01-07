package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.dferens.core.entities.Renderable;
import com.dferens.core.entities.Updatable;

public abstract class GameManager implements SettingsProvider, Screen {
    private final GameWorld world;
    protected final EntityManager entityManager;
    protected final RenderScope renderScope;
    private final InputScope inputScope;
    private final Settings settings;

    public EntityManager getEntityManager() { return this.entityManager; }
    public RenderScope getRenderScope() { return this.renderScope; }
    public final Settings getSettings() { return this.settings; }

    public GameManager() {
        this.settings = this.createSettings();
        this.world = new GameWorld(this);
        this.entityManager = this.createEntityManager(this, world);
        this.renderScope = new RenderScope(settings.renderVisibleUnits);
        this.inputScope = this.createUIManager();
    }

    protected abstract Settings createSettings();
    protected abstract InputScope createUIManager();
    protected abstract EntityManager createEntityManager(SettingsProvider configProvider, GameWorld world);


    protected final void innerRender(float deltaTime) {
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

    public final void process(float deltaTime) {
        this.update(deltaTime);
        this.innerRender(deltaTime);
    }

    /** Begin of {@link Screen} interface */

    @Override
    public void render(float delta) { this.process(delta); }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void show() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() {
        this.world.dispose();
        this.renderScope.dispose();
    }
}
