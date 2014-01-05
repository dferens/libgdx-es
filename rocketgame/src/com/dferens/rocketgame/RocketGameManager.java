package com.dferens.rocketgame;

import com.badlogic.gdx.math.Vector2;
import com.dferens.core.*;

public class RocketGameManager extends GameManager {

    public RocketGameManager() {
        super();
    }

    @Override
    protected GameConfig createGameConfig() {
        GameConfig config = new GameConfig();
        config.renderVisibleUnits = 20;
        config.worldGravity = new Vector2(0, -10);
        config.worldPositionIterations = 10;
        config.worldVelocityIterations = 2;
        return config;
    }

    @Override
    public UIManager createUIManager() {
        return new RockeGameUIManager();
    }

    @Override
    public IEntityManager createEntityManager(IGameConfigProvider configProvider) {
        return new RocketGameEntityManager(new RocketGameEntityPriorityResolver(), this);
    }
}
