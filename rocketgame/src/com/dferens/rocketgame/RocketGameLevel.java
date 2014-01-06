package com.dferens.rocketgame;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.dferens.core.EntityManagerContract;
import com.dferens.core.TmxLevel;

public class RocketGameLevel extends TmxLevel {

    public RocketGameLevel(String levelFilePath) {
        super(levelFilePath);
    }

    @Override
    public void loadIntoGame(EntityManagerContract entityManager) {
        // Load collision blocks
        TiledMapTileLayer collisionLayer = this.getCollisionLayer();
        int layerHeight = collisionLayer.getHeight();

        for (int mapY = 0; mapY < collisionLayer.getHeight(); mapY++) {
            for (int mapX = 0; mapX < collisionLayer.getWidth(); mapX++) {
                TiledMapTileLayer.Cell cell = collisionLayer.getCell(mapX, mapY);
                // Converting top-left coordinates to bottom-left
                int blockX = mapX, blockY = (layerHeight - 1 - mapY);
                if (cell != null) {
                    entityManager.createEntity(new BlockEntity(blockX, blockY));
                }
            }
        }
        // Initialize player
        MapLayer controlLayer = this.getControlLayer();
        RectangleMapObject start = (RectangleMapObject) controlLayer.getObjects().get("start");
        Vector2 spawnPosition = new Vector2();
        spawnPosition = start.getRectangle().getCenter(spawnPosition);
        RocketEntity rocketEntity = new RocketEntity(spawnPosition);
        entityManager.createEntity(rocketEntity);
    }

    private TiledMapTileLayer getCollisionLayer() {
        return (TiledMapTileLayer) this.tiledMap.getLayers().get("blocks");
    }
    private MapLayer getControlLayer() {
        return (TiledMapTileLayer) this.tiledMap.getLayers().get("control");
    }
}
