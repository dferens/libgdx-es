package com.dferens.libgdxes.render;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.entities.PhysicsApplied;

public abstract class DrawChain<T, B extends DrawChain> {
    protected final RenderScope renderScope;
    protected float posPixelsX;
    protected float posPixelsY;
    protected float rotationAngleDegrees;
    protected Vector3 temp;

    public DrawChain(RenderScope renderScope) {
        this.renderScope = renderScope;
        this.posPixelsX = 0;
        this.posPixelsY = 0;
        this.rotationAngleDegrees = 0;
        this.temp = new Vector3();
    }

    public B screenCoords(float x, float y) {
        this.posPixelsX = x;
        this.posPixelsY = y;
        return (B)this;
    }
    public B screenCoords(Vector2 coords) {
        return this.screenCoords(coords.x, coords.y);
    }
    public B worldCoords(float x, float y) {
        this.temp.set(x, y, 0);
        this.renderScope.projectCoordinates(this.temp);
        return this.screenCoords(this.temp.x, this.temp.y);
    }
    public B worldCoords(Vector2 coords) {
        return worldCoords(coords.x, coords.y);
    }
    public B bodyCoords(PhysicsBody body) {
        return this.worldCoords(body.getX(), body.getY());
    }
    public B bodyCoords(PhysicsApplied entity) {
        return this.bodyCoords(renderScope.getContext(entity).getBody());
    }

    public B rotateDegrees(float angle) {
        this.rotationAngleDegrees = angle;
        return (B)this;
    }
    public B rotateRadians(float angle) {
        return this.rotateDegrees((float)Math.toDegrees(angle));
    }
    public B rotateAsBody(PhysicsBody body) {
        return this.rotateRadians(body.getRotationRadians());
    }

    public void commit() { this.renderScope.render(this); }

    public abstract void execute(T renderObject, float deltaTime);
}