package com.dferens.libgdxes.render.drawable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dferens.libgdxes.render.Position;

public class TextureDrawable extends Drawable {
    private final int textureHeight;
    private final int textureWidth;
    private TextureRegion textureRegion;

    public TextureDrawable(Texture texture) {
        this.textureWidth = texture.getWidth();
        this.textureHeight = texture.getHeight();
        this.textureRegion = new TextureRegion(texture, textureWidth, textureHeight);
    }

    @Override
    public void execute(SpriteBatch batch,
                        float x, float y, Position position,
                        float width, float height,
                        float scaleX, float scaleY, float angle) {
        if (width == -1) width = this.textureWidth;
        if (height == -1) height = this.textureHeight;
        x -= this.calculateOffsetOfX(position, width);
        y -= this.calculateOffsetOfY(position, width);
        batch.draw(this.textureRegion, x, y, width/2, height/2, width * scaleX, height * scaleY, 1, 1, angle);
    }
}
