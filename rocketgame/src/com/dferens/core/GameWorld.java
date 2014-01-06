package com.dferens.core;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.core.entities.PhysicsApplied;

public class GameWorld implements Disposable {
    protected final World boxWorld;
    protected final GameConfigProvider configProvider;

    public GameWorld(GameConfigProvider configProvider) {
        this.configProvider = configProvider;
        this.boxWorld = new World(configProvider.getGameConfig().worldGravity, true);
    }

    public void step(float deltaTime) {
        GameConfig config = configProvider.getGameConfig();
        this.boxWorld.step(deltaTime, config.worldVelocityIterations,
                                      config.worldPositionIterations);
    }
    public PhysicsBody createBody(PhysicsApplied entity) {
        return entity.createBody(this.boxWorld);
    }

    @Override
    public void dispose() {  this.boxWorld.dispose(); }
}
