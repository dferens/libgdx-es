package com.dferens.libgdxes.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Original map mapRenderer will probably change matrices of passed SpriteBatch (yes, WTF???)
 * i think we should disable this feature and left mapRenderer with its own SpriteBatch.
 */
public class ScaledOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer implements SeparateLayerRenderer {
    public void setUnitScale(float value) { this.unitScale = value; }

    public ScaledOrthogonalTiledMapRenderer() {
        super(null);
    }
    public ScaledOrthogonalTiledMapRenderer(TiledMap map) {
        super(map);
    }
    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public void render(MapLayers layers) {
        beginRender();
        for (MapLayer layer : layers) renderLayer(layer);
        endRender();
    }
    @Override
    public void render(MapLayer... layers) {
        beginRender();
        for (MapLayer layer : layers) renderLayer(layer);
        endRender();
    }

    private void renderLayer(MapLayer layer) {
        if (layer.isVisible()) {
            if (layer instanceof TiledMapTileLayer) {
                renderTileLayer((TiledMapTileLayer)layer);
            } else if (layer instanceof TiledMapImageLayer) {
                renderImageLayer((TiledMapImageLayer) layer);
            }
        }
    }

    protected void renderImageLayer(TiledMapImageLayer layer) {
        // TODO: fix scale
        spriteBatch.draw(layer.getImageTextureRegion(), 0, 0);
    }

    @Override
    public void beginRender() { super.beginRender(); }
    @Override
    public void endRender() { super.endRender(); }

    public ScaledOrthogonalTiledMapRenderer(TiledMap map, float unitScale, SpriteBatch batch) {
        super(map, unitScale);
    }
}
