package com.dferens.libgdxes.render;

public abstract class Drawable<T> {
    protected float calculateOffsetOfX(Position position, float widthInPixels) {
        return (position.getDx() + 1) * widthInPixels / 2;
    }
    protected float calculateOffsetOfY(Position position, float heightInPixels) {
        return (position.getDy() + 1) * heightInPixels / 2;
    }
    public abstract void execute(T renderObject, float deltaTime,
                                 float x, float y, Position position,
                                 float width, float height, float scaleX, float scaleY,
                                 float angle);
}
