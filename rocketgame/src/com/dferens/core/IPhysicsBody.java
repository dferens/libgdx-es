package com.dferens.core;

import com.badlogic.gdx.physics.box2d.World;

public interface IPhysicsBody extends IEntity {
    PhysicsBody createBody(World world);
}
