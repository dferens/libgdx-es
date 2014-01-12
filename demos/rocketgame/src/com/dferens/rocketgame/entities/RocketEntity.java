package com.dferens.rocketgame.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.InputScope;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.render.Position;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.rocketgame.Priority;
import com.dferens.rocketgame.RocketGameInputScope;

public class RocketEntity implements PhysicsApplied, Updatable, Renderable, Disposable {
    private static final float JUMP_IMPULSE = 10f;
    private static final float MOVE_SPEED = 30f;

    private final Texture rocketTexture;
    private final float spawnPositionX;
    private final float spawnPositionY;

    public RocketEntity(Vector2 spawnPosition) {
        this.spawnPositionX = spawnPosition.x;
        this.spawnPositionY = spawnPosition.y;

        this.rocketTexture = new Texture(Gdx.files.internal("data/character.png"));
    }

    @Override
    public PhysicsBody createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPositionX, spawnPositionY);
        Body boxBody = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.2f;
        CircleShape rocketShape = new CircleShape();
        rocketShape.setRadius(1f);
        fixtureDef.shape = rocketShape;
        boxBody.createFixture(fixtureDef);
        return new PhysicsBody(boxBody);
    }

    @Override
    public void update(float deltaTime, Context context, InputScope input, RenderScope renderScope) {
        RocketGameInputScope screenInput = (RocketGameInputScope) input;
        PhysicsBody body = context.getBody();

        float newVelocityX = (float)(MOVE_SPEED * screenInput.getMovingRate());
        float currentVelocityY = body.getLinearVelocity().y;
        body.setLinearVelocity(newVelocityX, currentVelocityY);

        if (screenInput.isJumping()) {
            body.applyLinearImpulse(0, JUMP_IMPULSE, body.getX(), body.getY());
        }
    }

    @Override
    public int getUpdatePriority() { return Priority.ENTITIES; }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderScope) {
        renderScope.draw(rocketTexture)
                   .bodyCoords(context.getBody())
                   .startAt(Position.CENTER)
                   .transformInUnits(2, 2)
                   .commit();
    }

    @Override
    public int getRenderPriority() { return Priority.ENTITIES; }

    @Override
    public void dispose() { rocketTexture.dispose(); }
}
