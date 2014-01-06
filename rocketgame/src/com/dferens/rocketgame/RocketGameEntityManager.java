package com.dferens.rocketgame;

import com.dferens.core.GameConfigProvider;
import com.dferens.core.GameWorld;
import com.dferens.core.levels.LevelEntityManager;

public class RocketGameEntityManager extends LevelEntityManager {
    private RocketEntity rocket;

    public RocketEntity getRocket() { return this.rocket; }

    public RocketGameEntityManager(GameConfigProvider configProvider, GameWorld world) {
        super(configProvider, world);
    }

    @Override
    protected void beforeLevelEntitiesClear() {
        rocket.dispose();
    }

    @Override
    protected void afterNewLevelEntitiesLoad() {
        RocketGameLevel level = (RocketGameLevel) this.currentLevel;
        RocketEntity rocket = new RocketEntity(level.getSpawnPoint());
        this.createEntity(rocket);
    }
}
