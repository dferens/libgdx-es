package com.dferens.core.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.dferens.core.PhysicsBody;

public interface PhysicsApplied extends Entity {
    PhysicsBody createBody(World world);
}
