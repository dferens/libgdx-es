package com.dferens.core.utils;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class ScaledOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {
    public void setUnitScale(float value) { this.unitScale = value; }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }
}
