package com.dferens.libgdxes.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
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

    public void render(MapLayers layers) {
        beginRender();
        for (MapLayer layer : layers) {
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                } else if (layer instanceof  TiledMapImageLayer) {
                    renderImageLayer((TiledMapImageLayer) layer);
                }
            }
        }
        endRender();
    }

    protected void renderImageLayer(TiledMapImageLayer layer) {
        // TODO: fix scale
        spriteBatch.draw(layer.getImageTexture(), 0, 0);
    }

    @Override
    public void beginRender() { super.beginRender(); }
    @Override
    public void endRender() { super.endRender(); }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale, SpriteBatch batch) {
        super(map, unitScale);
    }
}
