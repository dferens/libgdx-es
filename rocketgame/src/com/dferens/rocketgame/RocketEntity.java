package com.dferens.rocketgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.core.Context;
import com.dferens.core.InputScope;
import com.dferens.core.PhysicsBody;
import com.dferens.core.RenderScope;
import com.dferens.core.entities.PhysicsApplied;
import com.dferens.core.entities.Renderable;
import com.dferens.core.entities.Updatable;

public class RocketEntity implements PhysicsApplied, Updatable, Renderable, Disposable {
    private static final float JUMP_IMPULSE = 10f;
    private static final float MOVE_SPEED = 5f;

    private final Texture rocketTexture;
    private final float spawnPositionX;
    private final float spawnPositionY;

    public RocketEntity(Vector2 spawnPosition) {
        this.spawnPositionX = spawnPosition.x;
        this.spawnPositionY = spawnPosition.y;

        rocketTexture = new Texture(Gdx.files.internal("data/rocket.png"));
    }

    @Override
    public PhysicsBody createBody(World world) {
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
        return new PhysicsBody(boxBody);
    }

    @Override
    public void update(float deltaTime, Context context, InputScope input) {
        RockeGameInputScope screenInput = (RockeGameInputScope) input;
        PhysicsBody body = context.getBody();

        if (screenInput.isJumping()) {
            float deltaSpeed = 0;

            if (screenInput.isMovingLeft()) {
                deltaSpeed = - MOVE_SPEED;
            }
            else if (screenInput.isMovingRight()) {
                deltaSpeed = MOVE_SPEED;
            }
            body.getLinearVelocity().x = deltaSpeed;
            body.applyLinearImpulse(0, JUMP_IMPULSE, body.getX(), body.getY());
        }
    }

    @Override
    public int getUpdatePriority() { return Priority.ENTITIES; }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderScope) {
        renderScope.draw(rocketTexture, context.getBody());
    }

    @Override
    public int getRenderPriority() { return Priority.ENTITIES; }

    @Override
    public void dispose() {
        rocketTexture.dispose();
    }
}
