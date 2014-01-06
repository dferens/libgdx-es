package com.dferens.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsBody {

    private Body boxBody;

    public PhysicsBody(Body boxBody) {
        this.boxBody = boxBody;
    }

    public float getX() { return boxBody.getPosition().x; }
    public float getY() { return boxBody.getPosition().y; }
    public void setX(float value) { boxBody.setTransform(value, getY(), boxBody.getAngle()); }
    public void setY(float value) { boxBody.setTransform(getX(), value, boxBody.getAngle()); }

    public Vector2 getLinearVelocity() { return boxBody.getLinearVelocity(); }
    public void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY) {
        applyLinearImpulse(impulseX, impulseY, pointX, pointY, true);
    }
    public void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake) {
        this.boxBody.applyLinearImpulse(impulseX, impulseY, pointX, pointY, wake);
    }

    public void destroy() {
        this.boxBody.getWorld().destroyBody(this.boxBody);
    }
}
