package com.dferens.rocketgame;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.dferens.core.EntityManager;
import com.dferens.core.levels.LevelParseException;
import com.dferens.core.levels.TmxLevel;

public class RocketGameLevel extends TmxLevel {
    private static String LAYER_COLLISION = "BLOCKS";
    private static String LAYER_CONTROLS = "CONTROLS";
    private static String POINT_SPAWN = "POINT_SPAWN";
    private static String POINT_FINISH = "POINT_FINISH";

    private final MapLayers backgroundLayers;
    private final MapLayers foregroundLayers;
    private TiledMapTileLayer collisionLayer;
    private MapLayer controlLayer;
    private Vector2 spawnPoint;
    private Vector2 finishPoint;

    public Vector2 getSpawnPoint() { return spawnPoint; }
    public Vector2 getFinishPoint() { return finishPoint; }

    public RocketGameLevel(String levelFilePath) throws LevelParseException {
        super(levelFilePath);

        this.backgroundLayers = new MapLayers();
        this.foregroundLayers = new MapLayers();
        this.spawnPoint = new Vector2();
        this.finishPoint = new Vector2();
        this.parse();
    }

    @Override
    protected TiledMapTileLayer getMainLayer() { return this.getCollisionLayer(); }

    @Override
    public void parse() throws LevelParseException {
        this.collisionLayer = this.getCollisionLayer();
        this.controlLayer = this.getControlsLayer();

        if (collisionLayer == null) {
            throw new LevelParseException("No collision layer found");
        } else if (controlLayer == null) {
            throw new LevelParseException("No control layer found");
        } else if (collisionLayer.getHeight() <= 1 || collisionLayer.getWidth() <= 1) {
            throw new LevelParseException("Map is too small");
        }

        MapObject spawnPointObject = controlLayer.getObjects().get(POINT_SPAWN);
        MapObject finishPointObject = controlLayer.getObjects().get(POINT_FINISH);

        if (spawnPointObject == null) {
            throw new LevelParseException("No spawn point specified");
        } else if (!(spawnPointObject instanceof RectangleMapObject)) {
            throw new LevelParseException("Spawn point should be specified with rectangle object");
        } else if (finishPointObject == null) {
            throw new LevelParseException("No finish point specified");
        } else if (!(finishPointObject instanceof RectangleMapObject)) {
            throw new LevelParseException("Finish point should be specified with rectangle object");
        }

        // Spawn & finish points
        ((RectangleMapObject)spawnPointObject).getRectangle().getCenter(this.getSpawnPoint());
        ((RectangleMapObject)finishPointObject).getRectangle().getCenter(this.getFinishPoint());

        // Load background & foreground layers
        MapLayers targetMapList = this.backgroundLayers;
        for (MapLayer layer : this.tiledMap.getLayers()) {
            if (layer == collisionLayer) targetMapList = this.foregroundLayers;
            if (layer.getName() != "")
                targetMapList.add(layer);
        }
    }

    private MapLayer getControlsLayer() {
        return this.getMapLayer(LAYER_CONTROLS);
    }

    @Override
    public void loadEntities(EntityManager entityManager) {
        // Load collision blocks
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
        // TODO: move to entityManager creation layer
        RocketEntity rocketEntity = new RocketEntity(this.getSpawnPoint());
        entityManager.createEntity(rocketEntity);
    }

    private TiledMapTileLayer getCollisionLayer() {
        return this.getMapLayer(LAYER_COLLISION, TiledMapTileLayer.class);
    }
}
