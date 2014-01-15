package com.dferens.libgdxes.entities;

import com.badlogic.gdx.physics.box2d.World;
import com.dferens.libgdxes.PhysicsBody;

public interface PhysicsApplied extends Entity {
    PhysicsBody createBody(World world);
}
