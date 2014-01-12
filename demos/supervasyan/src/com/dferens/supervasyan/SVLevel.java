package com.dferens.supervasyan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dferens.libgdxes.EntityManager;
import com.dferens.libgdxes.GameManager;
import com.dferens.libgdxes.entities.Entity;
import com.dferens.libgdxes.levels.LevelParseException;
import com.dferens.libgdxes.levels.TmxLevel;
import com.dferens.libgdxes.render.SeparateLayerRenderer;
import com.dferens.libgdxes.render.TiledMapImageLayer;
import com.dferens.libgdxes.render.entities.ImageLayerRendererEntity;
import com.dferens.libgdxes.render.entities.TileLayerRendererEntity;
import com.dferens.libgdxes.utils.loaders.ImageLayerSupportedTmxMapLoader;
import com.dferens.supervasyan.entities.BlockEntity;

public class SVLevel extends TmxLevel {
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
    private Color backgroundColor;

    public Vector2 getSpawnPoint() { return spawnPoint; }
    public Vector2 getFinishPoint() { return finishPoint; }
    public Color getBackgroundColor() { return backgroundColor; }

    @Override
    public MapLayers getBackgroundLayers() { return backgroundLayers; }
    @Override
    public TiledMapTileLayer getMainLayer() { return this.getCollisionLayer(); }
    @Override
    public MapLayers getForegroundLayers() { return foregroundLayers; }
    public TiledMapImageLayer getBackgroundLayer1() { return (TiledMapImageLayer) this.backgroundLayers.get(0); }

    public SVLevel(String levelFilePath) throws LevelParseException {
        super(levelFilePath, new ImageLayerSupportedTmxMapLoader());

        this.backgroundLayers = new MapLayers();
        this.foregroundLayers = new MapLayers();
        this.spawnPoint = new Vector2();
        this.finishPoint = new Vector2();
        this.parse();
    }

    private MapLayer getControlsLayer() {
        return this.getMapLayer(LAYER_CONTROLS);
    }

    private TiledMapTileLayer getCollisionLayer() {
        return this.getMapLayer(LAYER_COLLISION, TiledMapTileLayer.class);
    }

    @Override
    public void parse() throws LevelParseException {
        String backgroundcolor = this.tiledMap.getProperties().get("backgroundcolor", String.class);
        this.backgroundColor = Color.valueOf(backgroundcolor.substring(1));

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
        Rectangle spawnBounds = ((RectangleMapObject) spawnPointObject).getRectangle();
        Rectangle finishBounds = ((RectangleMapObject) finishPointObject).getRectangle();
        Vector2 positionPixels = new Vector2();
        spawnBounds.getCenter(positionPixels);
        this.spawnPoint.set(positionPixels.x / this.getTileSizePixels(),
                            positionPixels.y / this.getTileSizePixels());
        finishBounds.getCenter(positionPixels);
        this.finishPoint.set(positionPixels.x / this.getTileSizePixels(),
                             positionPixels.y / this.getTileSizePixels());

        // Load background & foreground layers
        MapLayers targetMapList = this.backgroundLayers;
        for (MapLayer layer : this.tiledMap.getLayers()) {
            if ((layer instanceof TiledMapTileLayer) ||
                (layer instanceof TiledMapImageLayer)) {
                targetMapList.add(layer);
            }
            if (layer == collisionLayer) {
                targetMapList = this.foregroundLayers;
            }
        }

        Array<TiledMapImageLayer> imageLayers = this.getLayers().getByType(TiledMapImageLayer.class);
        for (TiledMapImageLayer layer : imageLayers) {
            MapProperties properties = layer.getProperties();
            Integer startX = properties.get("X", 0, Integer.class);
            Integer startY = properties.get("Y", 0, Integer.class);
            layer.setPosition(startX, startY);
        }

        if (this.backgroundLayers.getCount() != 2) {
            throw new LevelParseException("2 background layers are required.");
        } else if (this.foregroundLayers.getCount() != 1) {
            throw new LevelParseException("1 foreground layers are required.");
        }
    }

    @Override
    public void loadEntities(EntityManager entityManager) {
        // Load collision blocks
        int layerHeight = collisionLayer.getHeight();
        for (int mapY = 0; mapY < layerHeight;  mapY++) {
            for (int mapX = 0; mapX < collisionLayer.getWidth(); mapX++) {
                if (collisionLayer.getCell(mapX, mapY) != null)
                    entityManager.createEntity(new BlockEntity(mapX, mapY));
            }
        }
        // Load layer renderers
        SeparateLayerRenderer mapRenderer = ((SVEntityManager) entityManager).getMapRenderer();
        Entity backgroundRenderer1 = new ImageLayerRendererEntity(
                this.getBackgroundLayer1(), Priority.BACKGROUND_FAR, this.calculateMapScale());
        Entity collisionLayerRenderer = new TileLayerRendererEntity(
                this.getMainLayer(), Priority.BACKGROUND_BLOCKS, mapRenderer);
        entityManager.createEntities(backgroundRenderer1, collisionLayerRenderer);
    }

    @Override
    public void loadSettings(GameManager gameManager) {
        gameManager.getRenderScope().setBackgroundColor(this.backgroundColor);
    }
}
