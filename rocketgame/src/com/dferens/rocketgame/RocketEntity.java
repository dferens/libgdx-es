package com.dferens.rocketgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.core.*;

public class RocketEntity implements IPhysicsBody, IUpdatable, IRenderable {
    private static final float JUMP_IMPULSE = 10f;
    private static final float MOVE_SPEED = 5f;

    private final Texture rocketTexture;
    private final Sprite rocketSprite;

    public RocketEntity() {
        rocketTexture = new Texture(Gdx.files.internal("data/rocket.png"));
        rocketSprite = new Sprite(rocketTexture);
    }

    @Override
    public PhysicsBody createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        Body boxBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.shape = new CircleShape();
        boxBody.createFixture(fixtureDef);
        PhysicsBody result = new PhysicsBody(boxBody);
        return result;
    }

    @Override
    public void update(float deltaTime, GameContext context, UIManager input) {
        OnScreenUIManager screenInput = (OnScreenUIManager) input;
        PhysicsBody body = context.getBody();
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
    public void render(float deltaTime, GameContext context, GameRenderer renderer) {
        renderer.draw(rocketTexture, context.getBody());
    }
}
