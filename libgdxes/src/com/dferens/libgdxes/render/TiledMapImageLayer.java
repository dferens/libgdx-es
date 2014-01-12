package com.dferens.libgdxes.render;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.math.Vector2;

public class TiledMapImageLayer extends MapLayer {
    private final TextureRegion textureRegion;
    private final Vector2 startPosition;

    public TextureRegion getImageTexture() { return this.textureRegion; }
    public Vector2 getStartPosition() { return this.startPosition; }
    public void setPosition(int startX, int startY) { this.startPosition.set(startX, startY); }

    public TiledMapImageLayer(TextureRegion textureRegion, float startX, float startY) {
        this.textureRegion = textureRegion;
        this.startPosition = new Vector2(startX, startY);
    }

    public TiledMapImageLayer(TextureRegion textureRegion) {
        this(textureRegion, 0, 0);
    }
}
