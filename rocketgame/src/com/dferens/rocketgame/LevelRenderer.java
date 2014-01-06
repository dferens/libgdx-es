package com.dferens.rocketgame;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dferens.core.Context;
import com.dferens.core.InputScope;
import com.dferens.core.PhysicsBody;
import com.dferens.core.RenderScope;
import com.dferens.core.entities.Entity;
import com.dferens.core.entities.Renderable;
import com.dferens.core.entities.Updatable;
import com.dferens.core.levels.TmxLevel;

public class LevelRenderer {
    class BackgroundRendererEntity implements Renderable, Updatable {

        @Override
        public void update(float deltaTime, Context context, InputScope input) {
            RocketGameEntityManager entityManager = (RocketGameEntityManager) context.getEntityManager();
            RocketEntity rocket = entityManager.getRocket();
            PhysicsBody rocketBody = entityManager.getContext(rocket).getBody();
            float totalLevelWidth = entityManager.getLevel().getWidth();
            float trackPointX = rocketBody.getX();
        }
        @Override
        public void render(float deltaTime, Context context, RenderScope renderer) {
            // TODO: parallax-like effect
        }

        @Override
        public int getUpdatePriority() { return Priority.BACKGROUND; }
        @Override
        public int getRenderPriority() { return Priority.BACKGROUND; }
    }
    class ForegroundRendererEntity implements Renderable, Updatable {

        @Override
        public void update(float deltaTime, Context context, InputScope input) { }
        @Override
        public void render(float deltaTime, Context context, RenderScope renderer) {
            // TODO: parallax-like effect
        }

        @Override
        public int getUpdatePriority() { return Priority.FOREGROUND; }
        @Override
        public int getRenderPriority() { return Priority.FOREGROUND; }
    }

    private OrthogonalTiledMapRenderer mapRenderer;

    public LevelRenderer(TiledMap tiledMap) {
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1);
    }

    public void onLevelChanged(TmxLevel level) {
        this.mapRenderer.setMap(level.getTiledMap());
    }

    public Entity createBackgroundRendererEntity() { return new BackgroundRendererEntity(); }
    public Entity createForegroundRendererEntity() { return new ForegroundRendererEntity(); }
}
