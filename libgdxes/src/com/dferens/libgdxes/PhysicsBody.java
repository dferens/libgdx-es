package com.dferens.libgdxes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.dferens.libgdxes.entities.PhysicsApplied;

import java.util.LinkedList;
import java.util.List;

public class PhysicsBody {
    private static List<PhysicsBody> lastTimeCreated = new LinkedList<PhysicsBody>();
    public static PhysicsBody[] makeEntityCreateBodies(PhysicsApplied entity, World world) {
        lastTimeCreated.clear();
        entity.createBodies(world);
        PhysicsBody[] result = new PhysicsBody[lastTimeCreated.size()];
        return lastTimeCreated.toArray(result);
    }

    private Body boxBody;
    private String alias;

    public String getAlias() { return this.alias; }

    public PhysicsBody(String alias, Body boxBody) {
        this.boxBody = boxBody;
        this.alias = alias;

        lastTimeCreated.add(this);
    }
    public PhysicsBody(Body boxBody) { this(null, boxBody);}

    public float getX() { return boxBody.getPosition().x; }
    public float getY() { return boxBody.getPosition().y; }
    public void setX(float value) { boxBody.setTransform(value, getY(), boxBody.getAngle()); }
    public void setY(float value) { boxBody.setTransform(getX(), value, boxBody.getAngle()); }
    public Vector2 getPosition() { return boxBody.getPosition(); }
    public void setPosition(Vector2 position) { boxBody.setTransform(position, boxBody.getAngle()); }

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
    public void applyForce(float forceX, float forceY, float pointX, float pointY) {
        this.applyForce(forceX, forceY, pointX, pointY, true);
    }
    public void applyForce(float forceX, float forceY, float pointX, float pointY, boolean wake) {
        this.boxBody.applyForce(forceX, forceY, pointX, pointY, wake);
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
    public float getRotationRadians() { return this.boxBody.getAngle();}
    public float getRotationDegrees() { return (float) Math.toDegrees(this.getRotationRadians()); }

    public void destroy() {
        lastTimeCreated.remove(this);
        this.boxBody.getWorld().destroyBody(this.boxBody);
    }
}
