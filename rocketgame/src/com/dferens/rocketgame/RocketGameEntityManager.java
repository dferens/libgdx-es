package com.dferens.rocketgame;

import com.dferens.core.GameManager;
import com.dferens.core.GameWorld;
import com.dferens.core.levels.LevelEntityManager;
import com.dferens.core.levels.TmxLevel;

public class RocketGameEntityManager extends LevelEntityManager {
    private RocketEntity rocket;
    private RocketGameLevelRenderer levelRenderer;

    public RocketEntity getRocket() { return this.rocket; }

    public RocketGameEntityManager(GameManager gameManager, GameWorld world) {
        super(gameManager, world);

        this.levelRenderer = new RocketGameLevelRenderer(1);
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
