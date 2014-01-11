package com.dferens.libgdxes.render.drawable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dferens.libgdxes.render.Position;

public class TextureRegionDrawable extends Drawable{
    private final TextureRegion textureRegion;

    public TextureRegionDrawable(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }
    @Override
    public void execute(SpriteBatch batch, float x, float y, Position position, float width, float height, float angle) {
        if (width == -1) width = this.textureRegion.getRegionWidth();
        if (height == -1) height = this.textureRegion.getRegionHeight();
        x -= this.calculateOffsetOfX(position, width);
        y -= this.calculateOffsetOfY(position, height);
        batch.draw(this.textureRegion, x ,y, width, height);
    }
}
