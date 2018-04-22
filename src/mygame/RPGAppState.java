/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.input.FlyByCamera;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import com.jme3.ui.Picture;

/**
 *
 * @author jeffr
 */
public class RPGAppState extends BaseAppState 
{
    //Base Data
    private final Camera CAMERA;
    private final FlyByCamera FLY_CAM;
    private final ViewPort VIEW_PORT;
    private final Player PLAYER;
    private final AssetManager ASSET_MANAGER;
    private final Node ROOT_NODE,
            GUI_NODE;
    private final Picture BLACKNESS;
    private final int WIDTH, 
        HEIGHT;
    
    //Camera Configuration
    private final int FUSTRUM_FAR = 1000;
    
    //Map Data
   private TerrainQuad terrain;
    
    public RPGAppState(Camera camera, ViewPort viewPort, FlyByCamera flyCam, Player player)
    {
        CAMERA = camera;
        VIEW_PORT = viewPort;
        FLY_CAM = flyCam;
        PLAYER = player;
        ROOT_NODE = Main.getMain().getRootNode();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        GUI_NODE = Main.getMain().getGuiNode();
        BLACKNESS = new Picture("BLACKNESS");
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
    }
    
    @Override
    protected void initialize(Application app) 
    {
        initCamera();
        initViewPort();
        initDimming();
        initMap();
    }
    
    private void initCamera()
    {
        CAMERA.setFrustumFar(FUSTRUM_FAR);
    }
    
    private void initViewPort()
    {
        VIEW_PORT.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    }
    
    private void initDimming()
    {
        BLACKNESS.setImage(ASSET_MANAGER, "Interface/dim.png", true);
        BLACKNESS.setHeight(HEIGHT);
        BLACKNESS.setWidth(WIDTH / 2);
        BLACKNESS.setPosition(WIDTH / 2, 0);
    }
    
    private void initMap()
    {
        Material mat_terrain = new Material(ASSET_MANAGER, "Common/MatDefs/Terrain/Terrain.j3md");
        mat_terrain.setTexture("Alpha", ASSET_MANAGER.loadTexture("Textures/alphaMap.png"));

        //Grass on Red
        Texture grass = ASSET_MANAGER.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex1", grass);
        mat_terrain.setFloat("Tex1Scale", 64f);

        //Dirt on green
        Texture dirt = ASSET_MANAGER.loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex2", dirt);
        mat_terrain.setFloat("Tex2Scale", 32f);

        //Road on blue
        Texture rock = ASSET_MANAGER.loadTexture("Textures/Terrain/splat/road.jpg");
        rock.setWrap(WrapMode.Repeat);
        mat_terrain.setTexture("Tex3", rock);
        mat_terrain.setFloat("Tex3Scale", 128f);

        /** 2. Create the height map */
        AbstractHeightMap heightmap = null;
        Texture heightMapImage = ASSET_MANAGER.loadTexture("Textures/heightMap.png");
        heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();

        int patchSize = 65;
        terrain = new TerrainQuad("terrain", patchSize, 1025, heightmap.getHeightMap());

        /** 4. We give the terrain its material, position & scale it, and attach it. */
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, -100, 0);
        terrain.setLocalScale(2f, 1f, 2f);
        ROOT_NODE.attachChild(terrain);

        /** 5. The LOD (level of detail) depends on were the camera is: */
        TerrainLodControl control = new TerrainLodControl(terrain, CAMERA);
        terrain.addControl(control);
    }
    
    public Vector3f coordinateOf(float x, float z)
    {
        float y = terrain.getHeight(new Vector2f(x, z));
        if(Float.isNaN(y))
        {
            return null;
        }
        return new Vector3f(x, y, z);
    }

    @Override
    protected void cleanup(Application app) 
    {

    }

    @Override
    protected void onEnable() 
    {
        GUI_NODE.detachChild(BLACKNESS);
        FLY_CAM.setEnabled(true);
        FLY_CAM.setDragToRotate(false);
    }

    @Override
    protected void onDisable() 
    {
        GUI_NODE.attachChild(BLACKNESS);
        FLY_CAM.setEnabled(false);
    }
    
    @Override
    public void update(float tpf)
    {
        
    }
    
    public void collision(Spatial a, Spatial b)
    {
        Spatial collidedA = a;
        Spatial collidedB = b;
    }
}