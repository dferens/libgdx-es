package com.dferens.libgdxes.render.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.render.AssetContainer;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.render.TiledMapImageLayer;

public class ImageLayerRendererEntity extends LevelLayerRendererEntity<TiledMapImageLayer> {
    private float mapScale;
    private boolean repeatedX;
    private boolean repeatedY;
    private float parallaxRateX;
    private float parallaxRateY;

    public boolean isRepeatedX() { return repeatedX; }
    public boolean isRepeatedY() { return repeatedY; }

    public void setRepeatedX(boolean repeatedX) { this.repeatedX = repeatedX; }
    public void setRepeatedY(boolean repeatedY) { this.repeatedY = repeatedY; }
    public void setMapScale(float mapScale) { this.mapScale = mapScale; }
    public void setParallaxRateX(float parallaxRateX) { this.parallaxRateX = parallaxRateX; }
    public void setParallaxRateY(float parallaxRateY) { this.parallaxRateY = parallaxRateY; }

    public ImageLayerRendererEntity(TiledMapImageLayer layer, int renderPriority,
                                    boolean repeatedX, boolean repeatedY,
                                    float mapScale) {
        super(layer, renderPriority);

        this.repeatedX = repeatedX;
        this.repeatedY = repeatedY;
        this.mapScale = mapScale;
        this.parallaxRateX = 0;
        this.parallaxRateY = 0;
    }
    public ImageLayerRendererEntity(TiledMapImageLayer layer, int renderPriority, boolean repeatedX, boolean repeatedY) {
        this(layer, renderPriority, repeatedX, repeatedY, 1);
    }
    public ImageLayerRendererEntity(TiledMapImageLayer layer, int renderPriority, float mapScale) {
        this(layer, renderPriority, false, false, mapScale);
    }
    public ImageLayerRendererEntity(TiledMapImageLayer layer, int renderPriority) {
        this(layer, renderPriority, false, false);
    }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderer) {
        TextureRegion texture = this.layer.getImageTextureRegion();
        float cameraWidthUnits = renderer.getViewportWidth();
        float cameraHeightUnits = renderer.getViewportHeight();
        float textureWidthUnits = texture.getRegionWidth() * this.mapScale;
        float textureHeightUnits = texture.getRegionHeight() * this.mapScale;
        Vector3 cameraPositionUnits = renderer.getCameraPosition()
                                              .sub(cameraWidthUnits / 2, cameraHeightUnits / 2, 0);
        float currentYUnits = (-cameraPositionUnits.y * this.parallaxRateY) % textureHeightUnits;
        float currentXUnits = (-cameraPositionUnits.x * this.parallaxRateX) % textureWidthUnits;

        if (this.repeatedX && this.repeatedY) {
            while (currentYUnits < (cameraHeightUnits + cameraPositionUnits.y)) {
                currentXUnits = (-cameraPositionUnits.x * this.parallaxRateX) % textureWidthUnits;
                while (currentXUnits < (cameraWidthUnits + cameraPositionUnits.x)) {
                    renderer.draw(texture)
                            .worldCoords(currentXUnits, currentYUnits)
                            .transformInUnits(textureWidthUnits, textureHeightUnits)
                            .commit();
                    currentXUnits += textureWidthUnits;
                }
                currentYUnits += textureHeightUnits;
            }
        } else if (this.repeatedX) {
            while (currentXUnits < (cameraWidthUnits + cameraPositionUnits.x)) {
                renderer.draw(texture)
                        .worldCoords(currentXUnits, currentYUnits)
                        .transformInUnits(textureWidthUnits, textureHeightUnits)
                        .commit();
                currentXUnits += textureWidthUnits;
            }
        } else if (this.repeatedY) {
            while (currentYUnits < (cameraHeightUnits + cameraPositionUnits.y)) {
                currentXUnits = (-cameraPositionUnits.x * this.parallaxRateX) % textureWidthUnits;
                renderer.draw(texture)
                        .worldCoords(currentXUnits, currentYUnits)
                        .transformInUnits(textureWidthUnits, textureHeightUnits)
                        .commit();
                currentYUnits += textureHeightUnits;
            }
        } else {
            renderer.draw(texture)
                    .worldCoords(currentXUnits, currentYUnits)
                    .transformInUnits(textureWidthUnits, textureHeightUnits)
                    .commit();
        }
    }


    @Override
    public void loadAssets(AssetContainer assetContainer) { }
}
