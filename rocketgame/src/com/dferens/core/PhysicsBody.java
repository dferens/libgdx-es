package com.dferens.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class PhysicsBody {

    private Body body;

    public PhysicsBody(Body body) {
        this.body = body;
    }

    public float getX() { return body.getPosition().x; }
    public float getY() { return body.getPosition().y; }
    public void setX(float value) { body.setTransform(value, getY(), body.getAngle()); }
    public void setY(float value) { body.setTransform(getX(), value, body.getAngle()); }

    public Vector2 getLinearVelocity() { return body.getLinearVelocity(); }
    public void applyLinearImpulse(float impulseX, float impulseY, float pointX, float pointY, Boolean wake) {
        body.applyLinearImpulse(impulseX, impulseY, pointX, pointY, wake);
    }
}
