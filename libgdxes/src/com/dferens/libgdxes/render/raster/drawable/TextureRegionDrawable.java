package com.dferens.libgdxes.render.raster.drawable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dferens.libgdxes.render.Position;

public class TextureRegionDrawable extends RasterDrawable{
    private final TextureRegion textureRegion;

    public TextureRegionDrawable(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
    @Override
    public void execute(SpriteBatch renderObject, float deltaTime,
                        float x, float y, Position position,
                        float width, float height, float scaleX, float scaleY,
                        float angle, boolean flipX, boolean flipY) {
        if (width == -1) width = this.textureRegion.getRegionWidth();
        if (height == -1) height = this.textureRegion.getRegionHeight();
        x -= this.calculateOffsetOfX(position, width);
        y -= this.calculateOffsetOfY(position, height);
        renderObject.draw(this.textureRegion, x ,y, width, height);
    }
}
