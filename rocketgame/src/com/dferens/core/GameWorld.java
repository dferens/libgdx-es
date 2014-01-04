package com.dferens.core;

import com.badlogic.gdx.physics.box2d.World;

public class GameWorld {
    private final World boxWorld;
    private final IGameConfigProvider configProvider;

    public World getBoxWorld() { return this.boxWorld; }

    public GameWorld(IGameConfigProvider configProvider) {
        this.configProvider = configProvider;
        this.boxWorld = new World(configProvider.getGameConfig().worldGravity, true);
    }

    public void step(float deltaTime) {
        GameConfig config = configProvider.getGameConfig();
        this.boxWorld.step(deltaTime, config.worldVelocityIterations, config.worldPositionIterations );
    }
    public PhysicsBody createBody(IPhysicsBody entity) {
        return entity.createBody(this.boxWorld);
    }
    public void destroyBody(PhysicsBody body) {
        body.destroy(this.boxWorld);
    }
}
