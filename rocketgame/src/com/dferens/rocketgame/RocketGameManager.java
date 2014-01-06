package com.dferens.rocketgame;

import com.badlogic.gdx.math.Vector2;
import com.dferens.core.*;
import com.dferens.core.levels.LevelParseException;

public class RocketGameManager extends GameManager {

    @Override
    public RocketGameEntityManager getEntityManager() {
        return (RocketGameEntityManager) this.entityManager;
    }

    public RocketGameManager() { }

    public void load() throws LevelParseException {
        this.getEntityManager().switchLevel(new RocketGameLevel("assets/levels/demo.tmx"));
    }

    @Override
    protected Settings createGameConfig() {
        Settings config = new Settings();
        config.renderVisibleUnits = 20;
        config.worldGravity = new Vector2(0, -10);
        config.worldPositionIterations = 10;
        config.worldVelocityIterations = 2;
        return config;
    }

    @Override
    public InputScope createUIManager() {
        return new RockeGameInputScope();
    }

    @Override
    public EntityManager createEntityManager(SettingsProvider configProvider, GameWorld world) {
        return new RocketGameEntityManager(this, world);
    }

}
