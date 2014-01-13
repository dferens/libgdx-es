package com.dferens.libgdxes.render.drawable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dferens.libgdxes.render.Position;

public abstract class Drawable {
    protected float calculateOffsetOfX(Position position, float widthInPixels) {
        return (position.getDx() + 1) * widthInPixels / 2;
    }
    protected float calculateOffsetOfY(Position position, float heightInPixels) {
        return (position.getDy() + 1) * heightInPixels / 2;
    }
    public abstract void execute(SpriteBatch batch, float deltaTime,
                                 float x, float y, Position position,
                                 float width, float height, float scaleX, float scaleY,
                                 float angle);
}
