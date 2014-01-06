package com.dferens.rocketgame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.dferens.core.*;

public class RocketGameManager extends GameManager {

    private RocketGameLevel currentLevel = null;
    private OrthogonalTiledMapRenderer mapRenderer = null;

    public RocketGameManager() {
        super();

        this.loadLevel(new RocketGameLevel("assets/levels/demo.tmx"));
    }

    @Override
    protected GameConfig createGameConfig() {
        GameConfig config = new GameConfig();
        config.renderVisibleUnits = 20;
        config.worldGravity = new Vector2(0, -10);
        config.worldPositionIterations = 10;
        config.worldVelocityIterations = 2;
        return config;
    }

    @Override
    public InputScope createUIManager() {
        return new RockeGameInputScope();
    }

    @Override
    protected void renderAll(float deltaTime) {

        super.renderAll(deltaTime);
    }

    @Override
    public EntityManager createEntityManager(GameConfigProvider configProvider) {
        return new RocketGameEntityManager(new RocketGameEntityPriorityResolver(), this);
    }

    private void loadLevel(RocketGameLevel level) {
        if (this.currentLevel != null) {
            this.entityManager.clear();
        }

        this.currentLevel = level;
        this.currentLevel.loadIntoGame(this.entityManager);
        TiledMap tiledMap = this.currentLevel.getTiledMap();
        SpriteBatch batch = this.renderScope.getBatch();
        this.mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1, batch);
    }
}
