package com.dferens.rocketgame;

import com.dferens.core.GameWorld;
import com.dferens.core.SettingsProvider;
import com.dferens.core.levels.LevelEntityManager;
import com.dferens.core.levels.TmxLevel;

public class RocketGameEntityManager extends LevelEntityManager {
    private RocketEntity rocket;
    private LevelRenderer levelRenderer;

    public RocketEntity getRocket() { return this.rocket; }

    public RocketGameEntityManager(SettingsProvider settingsProvider, GameWorld world) {
        super(settingsProvider, world);

        this.levelRenderer = new LevelRenderer();
    }

    @Override
    protected void beforeLevelEntitiesClear() {
        this.rocket.dispose();
        this.rocket = null;
    }

    @Override
    protected void afterNewLevelEntitiesLoad() {
        RocketGameLevel level = (RocketGameLevel) this.currentLevel;
        this.rocket = new RocketEntity(level.getSpawnPoint());
        this.levelRenderer.onLevelChanged((TmxLevel) this.currentLevel);
        this.createEntity(this.rocket);
        this.createEntity(levelRenderer.getForegroundRendererEntity());
        this.createEntity(levelRenderer.getBackgroundRendererEntity());
    }
}
