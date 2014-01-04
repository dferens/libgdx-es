package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class GameRenderer implements Disposable {
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public SpriteBatch getBatch() { return this.batch; }

    public GameRenderer(float visibleUnits) {
        this.batch = new SpriteBatch();
        this.camera = this.setupCamera(visibleUnits);
    }

    public void moveCamera(float x, float y) {
        this.camera.translate(x, y);
        this.camera.update();
        this.batch.setProjectionMatrix(camera.combined);
    }
    public void moveCamera(Vector2 pos) {
        this.moveCamera(pos.x, pos.y);
    }
    public void draw(Texture texture, PhysicsBody body) {
        Vector3 position = new Vector3(body.getX(), body.getY(), 0);
        camera.project(position);
        batch.draw(texture, position.x, position.y);
    }

    private OrthographicCamera setupCamera(float visibleUnits) {
        float widthPixels = Gdx.graphics.getWidth();
        float heightPixels = Gdx.graphics.getHeight();
        float viewportWidth, viewportHeight;

        if (widthPixels >= heightPixels) {
            viewportWidth = visibleUnits;
            viewportHeight = visibleUnits * heightPixels / widthPixels;
        }
        else {
            viewportHeight = visibleUnits;
            viewportWidth = visibleUnits * widthPixels / heightPixels;
        }
        return new OrthographicCamera(viewportWidth, viewportHeight);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
