package com.dferens.core;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public abstract class LevelGameManager extends GameManager {
    public LevelGameManager(GameConfig config) {
        super();
    }

    @Override
    protected void render(float deltaTime) {
        super.render(deltaTime);
        OrthogonalTiledMapRenderer renderer;

    }
}
