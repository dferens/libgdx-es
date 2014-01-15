package com.dferens.libgdxes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsBody {

    private Body boxBody;

    public PhysicsBody(Body boxBody) {
        this.boxBody = boxBody;
    }

    public float getX() { return boxBody.getPosition().x; }
    public float getY() { return boxBody.getPosition().y; }
    public Vector2 getPosition() { return boxBody.getPosition(); }
    public void setX(float value) { boxBody.setTransform(value, getY(), boxBody.getAngle()); }
    public void setY(float value) { boxBody.setTransform(getX(), value, boxBody.getAngle()); }

    public Vector2 getLinearVelocity() { return boxBody.getLinearVelocity(); }
    public void setLinearVelocity(float vx, float vy) {
        this.boxBody.setLinearVelocity(vx, vy);
    }
    public void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY) {
        applyLinearImpulse(impulseX, impulseY, pointX, pointY, true);
    }
    public void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, boolean wake) {
        this.boxBody.applyLinearImpulse(impulseX, impulseY, pointX, pointY, wake);
    }
    public void setFixedRotation(boolean value) {
        this.boxBody.setFixedRotation(value);
    }
    public void setRotationRadians(float angle) {
        this.boxBody.setTransform(this.getPosition(), angle);
    }
    public void setRotationDegrees(float angle) {
        this.setRotationRadians((float)Math.toRadians(angle));
    }

    /**
     * @return bodies angle clockwise
     */
    public float getRotationRadians() {
        return this.boxBody.getAngle();
    }
    public float getRotationDegrees() { return (float) Math.toDegrees(this.getRotationRadians()); }

    public void destroy() {
        this.boxBody.getWorld().destroyBody(this.boxBody);
    }
}
