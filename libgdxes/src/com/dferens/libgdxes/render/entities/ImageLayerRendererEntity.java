package com.dferens.libgdxes.render.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.render.TiledMapImageLayer;

public class ImageLayerRendererEntity extends LevelLayerRendererEntity<TiledMapImageLayer> {
    private float mapScale;

    public void setMapScale(float mapScale) { this.mapScale = mapScale; }

    public ImageLayerRendererEntity(TiledMapImageLayer layer, int renderPriority, float mapScale) {
        super(layer, renderPriority);

        this.mapScale = mapScale;
    }
    public ImageLayerRendererEntity(TiledMapImageLayer layer, int renderPriority) {
        this(layer, renderPriority, 1);
    }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderer) {
        TextureRegion texture = this.layer.getImageTexture();
        renderer.draw(texture)
                .worldCoords(this.layer.getStartPosition())
                .scale(this.mapScale, this.mapScale)
                .commit();
    }
}
