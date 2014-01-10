package com.dferens.rocketgame;

import com.dferens.libgdxes.GameManager;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.levels.LevelEntityManager;
import com.dferens.libgdxes.levels.TmxLevel;
import com.dferens.rocketgame.entities.RocketEntity;

public class RocketGameEntityManager extends LevelEntityManager {
    private RocketEntity rocket;
    private RocketGameLevelRenderer levelRenderer;

    public RocketEntity getRocket() { return this.rocket; }

    public RocketGameEntityManager(GameManager gameManager, GameWorld world) {
        super(gameManager, world);
    }

    @Override
    public void initialize() {
        super.initialize();
        this.levelRenderer = new RocketGameLevelRenderer(this.gameManager.getRenderScope().createMapRenderer(null, 1));
    }

    @Override
    protected void beforeLevelEntitiesClear() {
        this.rocket.dispose();
        this.rocket = null;
    }

    @Override
    protected void afterNewLevelEntitiesLoad() {
        RocketGameLevel level = (RocketGameLevel) this.currentLevel;
        // Creating player
        this.rocket = new RocketEntity(level.getSpawnPoint());
        this.createEntity(this.rocket);
        // Triggering level render
        this.levelRenderer.onLevelChanged((TmxLevel) this.currentLevel);
        this.createEntity(levelRenderer.getForegroundRendererEntity());
        this.createEntity(levelRenderer.getBackgroundRendererEntity());
    }
}
