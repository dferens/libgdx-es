package com.dferens.core;

import com.badlogic.gdx.physics.box2d.World;

public interface PhysicsApplied extends Entity {
    PhysicsBody createBody(World world);
}
