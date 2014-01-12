package com.dferens.libgdxes.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;

public class TiledMapImageLayer extends MapLayer {
    private final TextureRegion textureRegion;

    public TextureRegion getImageTexture() { return this.textureRegion; }
    public TiledMapImageLayer(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
}
