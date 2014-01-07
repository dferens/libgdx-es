package com.dferens.rocketgame;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dferens.core.Context;
import com.dferens.core.InputScope;
import com.dferens.core.RenderScope;
import com.dferens.core.entities.Renderable;
import com.dferens.core.entities.Updatable;
import com.dferens.core.levels.TmxLevel;

// TODO: refactor this shit

public class LevelRenderer {
    class BackgroundRendererEntity implements Renderable, Updatable {
        private final LevelRenderer levelRenderer;

        BackgroundRendererEntity(LevelRenderer levelRenderer) {
            this.levelRenderer = levelRenderer;
        }

        @Override
        public void update(float deltaTime, Context context, InputScope input) {
//            RocketGameEntityManager entityManager = (RocketGameEntityManager) context.getEntityManager();
//            RocketEntity rocket = entityManager.getRocket();
//            PhysicsBody rocketBody = entityManager.getContext(rocket).getBody();
//            float totalLevelWidth = entityManager.getLevel().getWidth();
//            float trackPointX = rocketBody.getX();
        }

        @Override
        public void render(float deltaTime, Context context, RenderScope renderer) {
            RocketGameEntityManager entityManager = (RocketGameEntityManager) context.getEntityManager();
            RocketGameLevel level = (RocketGameLevel) entityManager.getLevel();

            renderer.syncronize(this.levelRenderer.mapRenderer);
            this.levelRenderer.mapRenderer.getSpriteBatch().begin();

            for (MapLayer layer : level.getBackgroundLayers())
                this.levelRenderer.mapRenderer.renderTileLayer((TiledMapTileLayer) layer);

            this.levelRenderer.mapRenderer.renderTileLayer(level.getMainLayer());
        }
        @Override
        public int getUpdatePriority() { return Priority.BACKGROUND; }

        @Override
        public int getRenderPriority() { return Priority.BACKGROUND; }
    }
    class ForegroundRendererEntity implements Renderable, Updatable {
        private final LevelRenderer levelRenderer;

        ForegroundRendererEntity(LevelRenderer levelRenderer) {
            this.levelRenderer = levelRenderer;
        }

        @Override
        public void update(float deltaTime, Context context, InputScope input) { }

        @Override
        public void render(float deltaTime, Context context, RenderScope renderer) {
            RocketGameEntityManager entityManager = (RocketGameEntityManager) context.getEntityManager();
            RocketGameLevel level = (RocketGameLevel) entityManager.getLevel();

            renderer.syncronize(this.levelRenderer.mapRenderer);

            for (MapLayer layer : level.getForegroundLayers())
                this.levelRenderer.mapRenderer.renderTileLayer((TiledMapTileLayer) layer);

            this.levelRenderer.mapRenderer.getSpriteBatch().end();
        }
        @Override
        public int getUpdatePriority() { return Priority.FOREGROUND; }

        @Override
        public int getRenderPriority() { return Priority.FOREGROUND; }
    }
    private OrthogonalTiledMapRenderer mapRenderer;
    private BackgroundRendererEntity backgroundRendererEntity;
    private ForegroundRendererEntity foregroundRendererEntity;

    public BackgroundRendererEntity getBackgroundRendererEntity() { return backgroundRendererEntity; }
    public ForegroundRendererEntity getForegroundRendererEntity() { return foregroundRendererEntity; }

    public LevelRenderer(TiledMap tiledMap) {
        // TODO: use batch from render scope
        // TODO: calculate unit scale
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 0.015f);
        this.backgroundRendererEntity = new BackgroundRendererEntity(this);
        this.foregroundRendererEntity = new ForegroundRendererEntity(this);
    }
    public LevelRenderer() { this(null); }

    public void onLevelChanged(TmxLevel level) {
        this.mapRenderer.setMap(level.getTiledMap());
    }
}
