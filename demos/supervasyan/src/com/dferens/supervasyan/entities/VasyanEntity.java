package com.dferens.supervasyan.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
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
    private static final float MAX_ANGLE_DEGREES = 40;
    private static final float JETPACK_SHIFT_BY_X = -0.5f;
    private static final float JOINT_SHIFT_BY_Y = 0.8f;

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
    public void createBodies(World world) {
        /**
         *    Body consists of two parts: collidable part (1) and control part (2), which are connected
         * by joint. All forces/impulses should be applied to control part so it will affect player's
         * body automatically.
         *    +---------+
         *    | +-----+ |
         *    | |  o  | |
         *    | | (2) | |
         *    | +-----+ |
         *    |         |
         *    |     (1) |
         *    +---------+
         *
         */
        // Player body
        BodyDef playerBodyDef = new BodyDef();
        playerBodyDef.type = BodyDef.BodyType.DynamicBody;
        playerBodyDef.position.set(spawnPositionX, spawnPositionY);
        Body playerBody = world.createBody(playerBodyDef);
        playerBody.setAngularDamping(10f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 5f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;
        PolygonShape playerShape = new PolygonShape();
        playerShape.setAsBox(0.80f, 1.05f, Vector2.Zero, 0);
        fixtureDef.shape = playerShape;
        playerBody.createFixture(fixtureDef);

        // Player's control body
        BodyDef controlBodyDef = new BodyDef();
        controlBodyDef.type = BodyDef.BodyType.DynamicBody;
        controlBodyDef.position.set(spawnPositionX, spawnPositionY + JOINT_SHIFT_BY_Y);
        Body controlBody = world.createBody(controlBodyDef);
        controlBody.setFixedRotation(true);

        FixtureDef controlFixtureDef = new FixtureDef();
        controlFixtureDef.density = 0;
        controlFixtureDef.friction = 0;
        controlFixtureDef.restitution = 0;
        PolygonShape controlShape = new PolygonShape();
        controlShape.setAsBox(0.25f, 0.25f, Vector2.Zero, 0);
        controlFixtureDef.shape = controlShape;
        controlBody.createFixture(controlFixtureDef);

        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.enableLimit = true;
        jointDef.lowerAngle = (float) Math.toRadians(-1 * MAX_ANGLE_DEGREES);
        jointDef.upperAngle = (float) Math.toRadians(+1 * MAX_ANGLE_DEGREES);
        jointDef.initialize(playerBody, controlBody, controlBody.getWorldCenter());
        world.createJoint(jointDef);

        new PhysicsBody("player", playerBody);
        new PhysicsBody("control", controlBody);
    }

    @Override
    public void update(float deltaTime, Context context, SVInputScope input) {
        final PhysicsBody controlBody = context.getBody("control");
        final float movingRate = input.getMovingRate();

        // Set up horizontal velocity
        final float newVelocityX = MOVE_SPEED * movingRate;
        final float currentVelocityY = controlBody.getLinearVelocity().y;
        controlBody.setLinearVelocity(newVelocityX, currentVelocityY);

        this.isRightHandled = movingRate >= 0;

        // Activate jetpack & animation
        if (input.isJumping()) {
            this.jetpackEnabled = true;
            controlBody.applyLinearImpulse(0, JUMP_IMPULSE, controlBody.getX(), controlBody.getY());
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
