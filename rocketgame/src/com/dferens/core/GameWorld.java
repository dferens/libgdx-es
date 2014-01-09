package com.dferens.core;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.core.entities.PhysicsApplied;

public class GameWorld implements Disposable {
    protected final World boxWorld;
    protected final SettingsProvider configProvider;

    public GameWorld(SettingsProvider configProvider) {
        this.configProvider = configProvider;
        this.boxWorld = new World(configProvider.getSettings().worldGravity, true);
    }

    public void step(float deltaTime) {
        Settings config = configProvider.getSettings();
        this.boxWorld.step(deltaTime, config.worldVelocityIterations,
                                      config.worldPositionIterations);
    }
    public PhysicsBody createBody(PhysicsApplied entity) {
        return entity.createBody(this.boxWorld);
    }
    public void draw(Box2DDebugRenderer debugRenderer, Camera camera) {
        debugRenderer.render(this.boxWorld, camera.combined);
    }

    @Override
    public void dispose() {  this.boxWorld.dispose(); }
}
