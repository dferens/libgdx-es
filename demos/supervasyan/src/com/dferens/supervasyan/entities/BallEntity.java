package com.dferens.supervasyan.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.render.AssetContainer;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.render.chains.ShapeDrawChain;
import com.dferens.supervasyan.Priority;

public class BallEntity extends BlockEntity implements Renderable {
    private static final float BLOCK_FRICTION = 0.8f;
    private static final float BLOCK_RESTITUTION = 0f;
    private static final float BLOCK_DENSITY = 0.5f;

    protected final float radius;

    public BallEntity(float gridX, float gridY, float radius) {
        super(gridX, gridY);

        this.radius = radius;
    }

    @Override
    public PhysicsBody createBody(World world) {
        float spawnX = gridX + this.radius;
        float spawnY = gridY + this.radius;
        // TODO: optimize memory usage
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(spawnX, spawnY);
        Body boxBody = world.createBody(bodyDef);

        CircleShape sawShape = new CircleShape();
        sawShape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = sawShape;
        fixtureDef.friction = BLOCK_FRICTION;
        fixtureDef.restitution = BLOCK_RESTITUTION;
        fixtureDef.density = BLOCK_DENSITY;

        boxBody.createFixture(fixtureDef);
        return new PhysicsBody(boxBody);
    }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderer) {
        renderer.drawShape()
                .shapeFigure(ShapeDrawChain.ShapeFigure.CIRCLE)
                .shapeType(ShapeRenderer.ShapeType.Filled)
                .setRadiusUnits(this.radius)
                .bodyCoords(context.getBody())
                .setColor(Color.GRAY)
                .commit();
    }

    @Override
    public int getRenderPriority() { return Priority.ENTITIES; }
    @Override
    public void loadAssets(AssetContainer assetContainer) { }
}
