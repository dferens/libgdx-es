package com.dferens.libgdxes.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ScaledOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {
    public void setUnitScale(float value) { this.unitScale = value; }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, SpriteBatch batch) {
        super(map, batch);
    }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale, SpriteBatch batch) {
        super(map, unitScale, batch);
    }
}
