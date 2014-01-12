package com.dferens.supervasyan;

import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.levels.TmxLevel;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.utils.ScaledOrthogonalTiledMapRenderer;

// TODO: refactor this shit

public class SVLevelRenderer {
    class BackgroundRendererEntity implements Renderable, Updatable {
        private final SVLevelRenderer levelRenderer;

        BackgroundRendererEntity(SVLevelRenderer levelRenderer) {
            this.levelRenderer = levelRenderer;
        }

        @Override
        public void update(float deltaTime, Context context, InputScope input, RenderScope renderScope) {
            // TODO: make camera track player
            /*SVEntityManager entities = (SVEntityManager) context.getEntityManager();
            VasyanEntity rocket = entities.getPlayer();
            PhysicsBody rocketBody = entities.getContext(rocket).getBody();
            float totalLevelWidth = entities.getLevel().getWidth();
            float trackPointX = rocketBody.getX();*/
        }

        @Override
        public void render(float deltaTime, Context context, RenderScope renderer) {
            SVEntityManager entityManager = (SVEntityManager) context.getEntityManager();
            RocketGameLevel level = (RocketGameLevel) entityManager.getLevel();

            renderer.synchronise(this.levelRenderer.mapRenderer);
            renderer.commitDraw(false);
            this.levelRenderer.mapRenderer.render(level.getBackgroundLayers());
            renderer.beginDraw();
        }
        @Override
        public int getUpdatePriority() { return Priority.BACKGROUND; }

        @Override
        public int getRenderPriority() { return Priority.BACKGROUND; }
    }
    class ForegroundRendererEntity implements Renderable, Updatable {
        private final SVLevelRenderer levelRenderer;

        ForegroundRendererEntity(SVLevelRenderer levelRenderer) {
            this.levelRenderer = levelRenderer;
        }

        @Override
        public void update(float deltaTime, Context context, InputScope input, RenderScope renderScope) { }

        @Override
        public void render(float deltaTime, Context context, RenderScope renderer) {
            SVEntityManager entityManager = (SVEntityManager) context.getEntityManager();
            RocketGameLevel level = (RocketGameLevel) entityManager.getLevel();

//            renderer.synchronise(this.levelRenderer.mapRenderer);
//            renderer.commitDraw(true);

//            for (MapLayer layer : level.getForegroundLayers())
//                this.levelRenderer.mapRenderer.renderTileLayer((TiledMapTileLayer) layer);

//            renderer.commitDraw(true);
        }
        @Override
        public int getUpdatePriority() { return Priority.FOREGROUND; }
        @Override
        public int getRenderPriority() { return Priority.FOREGROUND; }
    }
    private ScaledOrthogonalTiledMapRenderer mapRenderer;
    private BackgroundRendererEntity backgroundRendererEntity;
    private ForegroundRendererEntity foregroundRendererEntity;

    public BackgroundRendererEntity getBackgroundRendererEntity() { return backgroundRendererEntity; }
    public ForegroundRendererEntity getForegroundRendererEntity() { return foregroundRendererEntity; }

    public SVLevelRenderer(ScaledOrthogonalTiledMapRenderer mapRenderer) {
        // TODO: use batch from render scope
        this.mapRenderer = mapRenderer;
        this.backgroundRendererEntity = new BackgroundRendererEntity(this);
        this.foregroundRendererEntity = new ForegroundRendererEntity(this);
    }

    public void onLevelChanged(TmxLevel level) {
        this.mapRenderer.setMap(level.getTiledMap());
        this.mapRenderer.setUnitScale(level.calculateMapScale());
    }
}
