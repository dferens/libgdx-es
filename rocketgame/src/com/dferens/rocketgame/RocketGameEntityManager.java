package com.dferens.rocketgame;

import com.dferens.core.SettingsProvider;
import com.dferens.core.GameWorld;
import com.dferens.core.levels.LevelEntityManager;

public class RocketGameEntityManager extends LevelEntityManager {
    private RocketEntity rocket;

    public RocketEntity getRocket() { return this.rocket; }

    public RocketGameEntityManager(SettingsProvider settingsProvider, GameWorld world) {
        super(settingsProvider, world);
    }

    @Override
    protected void beforeLevelEntitiesClear() {
        this.rocket.dispose();
        this.rocket = null;
    }

    @Override
    protected void afterNewLevelEntitiesLoad() {
        RocketGameLevel level = (RocketGameLevel) this.currentLevel;
        rocket = new RocketEntity(level.getSpawnPoint());
        this.createEntity(rocket);
    }
}
