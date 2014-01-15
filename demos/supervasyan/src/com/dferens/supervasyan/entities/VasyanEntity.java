package com.dferens.supervasyan.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.entities.PhysicsApplied;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.entities.Updatable;
import com.dferens.libgdxes.render.AssetContainer;
import com.dferens.libgdxes.render.Position;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.render.raster.RasterDrawChain;
import com.dferens.supervasyan.Priority;
import com.dferens.supervasyan.SVInputScope;

public class VasyanEntity implements PhysicsApplied, Updatable<SVInputScope>, Renderable {
    private static final float JUMP_IMPULSE = 5f;
    private static final float MOVE_SPEED = 50f;
    private static final float MAX_ANGLE_DEGREES = 60;
    private static final float JETPACK_SHIFT_BY_X = -0.5f;

    private final float spawnPositionX;
    private final float spawnPositionY;
    private boolean jetpackEnabled;
    private boolean isRightHandled;

    public VasyanEntity(Vector2 spawnPosition) {
        this.spawnPositionX = spawnPosition.x;
        this.spawnPositionY = spawnPosition.y;
        this.jetpackEnabled = false;
    }

    @Override
    public PhysicsBody createBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnPositionX, spawnPositionY);
        Body boxBody = world.createBody(bodyDef);
        boxBody.setFixedRotation(true);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.80f, 1.05f, Vector2.Zero, 0);
        fixtureDef.shape = playerShape;

        boxBody.createFixture(fixtureDef);
        return new PhysicsBody(boxBody);
    }

    @Override
    public void update(float deltaTime, Context context, SVInputScope input) {
        final PhysicsBody body = context.getBody();
        final float movingRate = input.getMovingRate();

        // Set up horizontal velocity
        final float newVelocityX = MOVE_SPEED * movingRate;
        final float currentVelocityY = body.getLinearVelocity().y;
        body.setLinearVelocity(newVelocityX, currentVelocityY);

        // Set up rotation angle
        final float angle = movingRate * MAX_ANGLE_DEGREES;
        body.setRotationDegrees(-1 * angle);
        this.isRightHandled = movingRate >= 0;

        // Activate jetpack & animation
        if (input.isJumping()) {
            this.jetpackEnabled = true;
            body.applyLinearImpulse(0, JUMP_IMPULSE, body.getX(), body.getY());
        } else {
            this.jetpackEnabled = false;
        }
    }

    @Override
    public int getUpdatePriority() { return Priority.ENTITIES; }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderScope) {
        if (this.jetpackEnabled) {
            float shiftByX = JETPACK_SHIFT_BY_X * (isRightHandled ? 1 : -1);
            renderScope.draw("player", ParticleEffect.class)
                       .bodyCoords(this)
                       .shiftUnits(shiftByX, -0.5f)
                       .rotateDegrees(180)
                       .startAt(Position.CENTER)
                       .commit();
        } else {
            renderScope.getAssetStorage().get("player", ParticleEffect.class).reset();
        }

        RasterDrawChain playerDraw = renderScope.draw("player", Texture.class);
        if (isRightHandled == false) {
            playerDraw = playerDraw.flipByX();
        }
        playerDraw.bodyCoords(context.getBody())
                  .startAt(Position.CENTER)
                  .transformInUnits(1.7f, 2.2f)
                  .shiftUnits(0, -0.25f)
                  .rotateAsBody(context.getBody())
                  .commit();
    }

    @Override
    public int getRenderPriority() { return Priority.ENTITIES; }

    @Override
    public void loadAssets(AssetContainer assetContainer) {
        assetContainer.load("player", "data/character.png", Texture.class);
        assetContainer.load("player", "data/particles/character.p", ParticleEffect.class);
    }
}
