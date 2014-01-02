package com.dferens.core;

import com.badlogic.gdx.physics.box2d.World;

public interface IEntity {
    PhysicsBody createBody(World world);
    void update(float deltaTime, PhysicsBody body, UIManager input);
    void render(float deltaTime, PhysicsBody body, GameRenderer renderer);
}
