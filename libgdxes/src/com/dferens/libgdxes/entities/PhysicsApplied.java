package com.dferens.libgdxes.entities;

import com.badlogic.gdx.physics.box2d.World;

public interface PhysicsApplied extends Entity {
    void createBodies(World world);
}
