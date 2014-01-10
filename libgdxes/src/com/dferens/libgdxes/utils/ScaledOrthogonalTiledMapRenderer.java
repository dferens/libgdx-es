package com.dferens.libgdxes.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Original map renderer will probably change matrices of passed SpriteBatch (yes, WTF???)
 * i think we should disable this feature and left renderer with its own SpriteBatch.
 */
public class ScaledOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {
    public void setUnitScale(float value) { this.unitScale = value; }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    @Override
    public void renderTileLayer(TiledMapTileLayer layer) {
        if (this.ownsSpriteBatch) this.spriteBatch.begin();
        super.renderTileLayer(layer);
        if (this.ownsSpriteBatch) this.spriteBatch.end();
    }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale, SpriteBatch batch) {
        super(map, unitScale);
    }
}
