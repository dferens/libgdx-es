package com.dferens.libgdxes.render.drawable;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dferens.libgdxes.render.Position;

public class BitmapFontDrawable extends Drawable {
    private final BitmapFont font;
    private final String text;

    public BitmapFontDrawable(BitmapFont font, String text) {
        this.font = font;
        this.text = text;
    }

    @Override
    public void execute(SpriteBatch batch, float x, float y, Position position, float width, float height, float scaleX, float scaleY, float angle) {
        BitmapFont.TextBounds bounds = this.font.getBounds(this.text);
        x -= this.calculateOffsetOfX(position, bounds.width);
        y -= this.calculateOffsetOfY(position, bounds.height);
        this.font.draw(batch, this.text, x, y);
    }
}
