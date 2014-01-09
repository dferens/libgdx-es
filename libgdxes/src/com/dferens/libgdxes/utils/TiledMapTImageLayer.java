package com.dferens.libgdxes.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;

public class TiledMapTImageLayer extends MapLayer {
    private final TextureRegion textureRegion;

    public TextureRegion getImageTexture() { return this.textureRegion; }
    public TiledMapTImageLayer(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
