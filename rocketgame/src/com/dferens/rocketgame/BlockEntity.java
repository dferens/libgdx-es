package com.dferens.rocketgame;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.*;
import com.dferens.core.entities.PhysicsApplied;
import com.dferens.core.PhysicsBody;

/**
 * As blocks should be rendered with specific map renderer, block has no render method
 * @see OrthogonalTiledMapRenderer;
 */
public class BlockEntity implements PhysicsApplied {
    private static final float BLOCK_FRICTION = 0.2f;
    private static final float BLOCK_RESTITUTION = 0f;
    private static final float BLOCK_DENSITY = 1f;

    private final int gridX;
    private final int gridY;

    public BlockEntity(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
    }
    @Override
    public PhysicsBody createBody(World world) {
        // TODO: optimize memory usage
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(gridX + 0.5f, gridY + 0.5f);

        PolygonShape blockShape = new PolygonShape();
        blockShape.setAsBox(0.5f, 0.5f, bodyDef.position, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = blockShape;
        fixtureDef.friction = BLOCK_FRICTION;
        fixtureDef.restitution = BLOCK_RESTITUTION;
        fixtureDef.density = BLOCK_DENSITY;

        Body boxBody = world.createBody(bodyDef);
        boxBody.createFixture(fixtureDef);
        return new PhysicsBody(boxBody);
    }
}
