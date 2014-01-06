package com.dferens.rocketgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.core.*;

public class RocketEntity implements IPhysicsBody, Updatable, Renderable {
    private static final float JUMP_IMPULSE = 10f;
    private static final float MOVE_SPEED = 5f;

    private final Texture rocketTexture;
    private final Sprite rocketSprite;
    private final float spawnPositionX;
    private final float spawnPositionY;

    public RocketEntity(Vector2 spawnPosition) {
        this.spawnPositionX = spawnPosition.x;
        this.spawnPositionY = spawnPosition.y;

        rocketTexture = new Texture(Gdx.files.internal("data/rocket.png"));
        rocketSprite = new Sprite(rocketTexture);
    }

    @Override
    public PhysicsApplied createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPositionX, spawnPositionY);
        Body boxBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.shape = new CircleShape();
        boxBody.createFixture(fixtureDef);
        PhysicsApplied result = new PhysicsApplied(boxBody);
        return result;
    }

    @Override
    public void update(float deltaTime, Context context, InputScope input) {
        RockeGameInputScope screenInput = (RockeGameInputScope) input;
        PhysicsApplied body = context.getBody();
        RocketGameWorld world = (RocketGameWorld) context.getBoxWorld();
        float deltaSpeed = 0;

        if (screenInput.isMovingLeft()) {
            deltaSpeed = - MOVE_SPEED;
        }
        else if (screenInput.isMovingRight()) {
            deltaSpeed = MOVE_SPEED;
        }
        body.getLinearVelocity().x = deltaSpeed;

        if (screenInput.isJumping()) {
            body.applyLinearImpulse(0, JUMP_IMPULSE, body.getX(), body.getY(), true);
        }
    }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderer) {
        renderer.draw(rocketTexture, context.getBody());
    }
}
