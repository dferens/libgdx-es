package com.dferens.rocketgame;

import com.badlogic.gdx.physics.box2d.World;
import com.dferens.core.GameConfig;
import com.dferens.core.GameWorld;
import com.dferens.core.IGameConfigProvider;

public class RocketGameWorld extends GameWorld {
    public RocketGameWorld(IGameConfigProvider configProvider) {
        super(configProvider);
    }
}
