package com.dferens.supervasyan;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Ellipse;
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
import com.dferens.supervasyan.entities.BallEntity;
import com.dferens.supervasyan.entities.BlockEntity;
import com.dferens.supervasyan.entities.SawEntity;

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
    private MapLayer getControlsLayer() { return this.getMapLayer(LAYER_CONTROLS); }
    private TiledMapTileLayer getCollisionLayer() { return this.getMapLayer(LAYER_COLLISION, TiledMapTileLayer.class); }
    @Override
    public MapLayers getBackgroundLayers() { return backgroundLayers; }
    @Override
    public TiledMapTileLayer getMainLayer() { return this.getCollisionLayer(); }
    @Override
    public MapLayers getForegroundLayers() { return foregroundLayers; }
    public TiledMapImageLayer getBackgroundFarLayer() { return (TiledMapImageLayer) this.backgroundLayers.get(0); }
    public TiledMapImageLayer getBackgroundNearLayer() { return (TiledMapImageLayer) this.backgroundLayers.get(1); }

    public SVLevel(String levelFilePath) throws LevelParseException {
        super(levelFilePath, new ImageLayerSupportedTmxMapLoader());

        this.backgroundLayers = new MapLayers();
        this.foregroundLayers = new MapLayers();
        this.spawnPoint = new Vector2();
        this.finishPoint = new Vector2();
        this.parse();
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

        if (this.backgroundLayers.getCount() < 1) {
            throw new LevelParseException("Should be at least 1 background layer");
        }

        // Additional objects

    }

    @Override
    public void loadEntities(EntityManager entityManager) {
        float unitScale = this.calculateMapScale();

        // Load collision blocks
        TiledMapTileLayer layer = this.collisionLayer;
        int layerHeight = layer.getHeight();
        for (int mapY = 0; mapY < layerHeight;  mapY++) {
            for (int mapX = 0; mapX < layer.getWidth(); mapX++) {
                if (layer.getCell(mapX, mapY) != null)
                    entityManager.createEntity(new BlockEntity(mapX, mapY));
            }
        }
        MapLayer objectsLayer = this.getLayers().get("OBJECTS");
        for (MapObject obj : objectsLayer.getObjects()) {
            String name = obj.getName();

            if ((name.equals("BALL")) || (name.equals("SAW"))) {
                Ellipse circle = ((EllipseMapObject) obj).getEllipse();
                float x = circle.x * unitScale;
                float y = circle.y * unitScale;
                float radius = circle.width * unitScale / 2;

                Entity entity;
                if (name.equals("BALL")) {
                    entity = new BallEntity(x, y, radius);
                } else {
                    entity = new SawEntity(x, y, radius);
                }
                entityManager.createEntities(entity);
            }
        }


        // Load layer renderers
        SeparateLayerRenderer mapRenderer = ((SVEntityManager) entityManager).getMapRenderer();

        Entity backgroundFarEntity = createImageLayerEntity(this.getBackgroundFarLayer(), Priority.BACKGROUND_FAR);
        Entity backgroundNearEntity = createImageLayerEntity(this.getBackgroundNearLayer(), Priority.BACKGROUND_NEAR);

        Entity collisionLayerRenderer = createMapLayerEntity(this.getCollisionLayer(),
                                                             Priority.BACKGROUND_BLOCKS_MAIN,
                                                             mapRenderer);
        entityManager.createEntities(backgroundFarEntity, backgroundNearEntity, collisionLayerRenderer);
    }

    private Entity createImageLayerEntity(TiledMapImageLayer layer, int priority) {
        boolean repeatByX = this.layerPropertyIsTrue(layer, "REPEAT_BY_X");
        boolean repeatByY = this.layerPropertyIsTrue(layer, "REPEAT_BY_Y");
        float mapScale = this.calculateMapScale();
        ImageLayerRendererEntity entity = new ImageLayerRendererEntity(layer, priority, repeatByX, repeatByY, mapScale);

        float parallaxRateX = this.getFloatProperty(layer, "PARALLAX_X", 0f);
        float parallaxRateY = this.getFloatProperty(layer, "PARALLAX_Y", 0f);
        entity.setParallaxRateX(parallaxRateX);
        entity.setParallaxRateY(parallaxRateY);
        return entity;
    }
    private Entity createMapLayerEntity(TiledMapTileLayer layer, int priority, SeparateLayerRenderer renderer) {
        return new TileLayerRendererEntity(layer, priority, renderer);
    }
    private boolean layerPropertyIsTrue(MapLayer layer, String property) {
        return layer.getProperties().get(property, "", String.class).equalsIgnoreCase("true");
    }
    private float getFloatProperty(MapLayer layer, String property, float defaultValue) {
        String floatValue = layer.getProperties().get(property, String.class);
        if (floatValue == null) {
            return defaultValue;
        } else {
            return Float.parseFloat(floatValue);
        }
    }

    @Override
    public void loadSettings(GameManager gameManager) {
        gameManager.getRenderScope().setBackgroundColor(this.backgroundColor);
    }
}