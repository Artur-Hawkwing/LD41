/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

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
    private final Adventurer ADVENTURER;
    private final AssetManager ASSET_MANAGER;
    private final BulletAppState BULLET_APP_STATE;
    private final Node ROOT_NODE,
            GUI_NODE;
    private final Picture BLACKNESS;
    private final int WIDTH, 
        HEIGHT;
    private final String PREFIX;
    
    //Camera Configuration
    private final int FUSTRUM_FAR = 6000;
    
    //Map Data
    private TerrainQuad terrain;
   
    //Enemies
    private final List<RPGEnemy> ENEMIES = new ArrayList<>();
    private final float FAST_SPAWN_CHANGE = .1f,
           POWER_SPEED_SPAWN_CHANCE = .2f,
           POWER_INVINCIBLE_SPAWN_CHANCE = .05f;
    private float spawnTimer,
           spawnTimerGoal = 10;
    private boolean canSpawn = true;
    private int enemiesToSpawn = 3,
            waveNum;
   
    //Random
    private final Random GENERATOR = new Random();

    public RPGAppState(Camera camera, ViewPort viewPort, FlyByCamera flyCam, Player player, String prefix)
    {
        CAMERA = camera;
        VIEW_PORT = viewPort;
        FLY_CAM = flyCam;
        PLAYER = player;
        PREFIX = prefix;
        ADVENTURER = PLAYER.getAdventurer();
        ROOT_NODE = Main.getMain().getRootNode();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
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
        initAdventurer();
    }
    
    private void initCamera()
    {
        CAMERA.setFrustumFar(FUSTRUM_FAR);
    }
    
    private void initViewPort()
    {
        VIEW_PORT.setBackgroundColor(new ColorRGBA(25 / 255f, 25 / 255f, 80 / 255f, 255 / 255f));
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
        Texture heightMapImage = ASSET_MANAGER.loadTexture("Textures/heightMapv1.png");
        AbstractHeightMap heightmap = new ImageBasedHeightMap(heightMapImage.getImage());
        heightmap.load();

        int patchSize = 65;
        terrain = new TerrainQuad("terrain", patchSize, 1025, heightmap.getHeightMap());

        /** 4. We give the terrain its material, position & scale it, and attach it. */
        terrain.setMaterial(mat_terrain);
        terrain.setLocalTranslation(0, -100, 0);
        terrain.setLocalScale(8f, 6f, 8f);
        ROOT_NODE.attachChild(terrain);

        //Set up LOD
        TerrainLodControl control = new TerrainLodControl(terrain, CAMERA);
        terrain.addControl(control);
        
        //Attach terrain to world and physics space
        CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) terrain);
        RigidBodyControl mapBody = new RigidBodyControl(sceneShape, 0);
        terrain.addControl(mapBody);
        ROOT_NODE.attachChild(terrain);
        BULLET_APP_STATE.getPhysicsSpace().add(mapBody);
    }
    
    private void initAdventurer()
    {
        CAMERA.setLocation(coordinateOf(0,0));
        FLY_CAM.setMoveSpeed(0);
        ADVENTURER.setSpawn(CAMERA.getLocation());
        ADVENTURER.setCamera(CAMERA);
        
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
        ADVENTURER.update(tpf);

        spawnTimer += tpf;
        if(spawnTimer >= spawnTimerGoal)
        {
            canSpawn = true;
        }

        if(canSpawn)
        {
            waveNum++;
            if(waveNum % 5 == 0 && enemiesToSpawn < 5)
            {
                enemiesToSpawn++;
                spawnTimerGoal = enemiesToSpawn + 1;
            }
            spawnEnemies(enemiesToSpawn);
            canSpawn = false;
            spawnTimer = 0;
        }
        
        for(RPGEnemy e : ENEMIES)
        {
            e.update(tpf);
        }
    }
    
    private void spawnEnemies(int num)
    {
        for(int i = 0; i < num; i++)
        {
            //Random Enemy Type
            float f = GENERATOR.nextFloat();
            RPGEnemyType type;
            float cutoff = 0;
            if(f >= cutoff && f < cutoff + POWER_INVINCIBLE_SPAWN_CHANCE)
            {
                type = RPGEnemyType.POWER_INVINCIBLE;
                cutoff += POWER_INVINCIBLE_SPAWN_CHANCE;
            }
            else if(f >= cutoff && f < cutoff + POWER_SPEED_SPAWN_CHANCE)
            {
                type = RPGEnemyType.POWER_SPEED;
                cutoff += POWER_SPEED_SPAWN_CHANCE;
            }
            else if(f >= cutoff && f < cutoff + FAST_SPAWN_CHANGE)
            {
                type = RPGEnemyType.FAST;
                cutoff += FAST_SPAWN_CHANGE;
            }
            else
            {
                type = RPGEnemyType.COMMON;
            }
            
            //Random Location
            Vector3f loc = new Vector3f(GENERATOR.nextInt(100) - 50, GENERATOR.nextInt(50), GENERATOR.nextInt(100) - 50);
            ENEMIES.add(new RPGEnemy(ROOT_NODE, ADVENTURER.getLocation().add(loc), type));
        }
    }
    
    public void collision(Spatial a, Spatial b)
    {        
        //Killing enemies
        if(a.getName().startsWith(RPGEnemy.getPrefix()) && b.getName().equals(Spear.getName()))
        {
            ListIterator<RPGEnemy> it = ENEMIES.listIterator();
            while(it.hasNext())
            {
                RPGEnemy e = it.next();
                if(e.getName().equals(a.getName()))
                {
                    PLAYER.addPowerUp(e.getEnemyType().getPowerUp());
                    e.destroy();
                    it.remove();
                }
            }
        }
        else if(b.getName().startsWith(RPGEnemy.getPrefix()) && a.getName().equals(Spear.getName()))
        {
            ListIterator<RPGEnemy> it = ENEMIES.listIterator();
            while(it.hasNext())
            {
                RPGEnemy e = it.next();
                if(e.getName().equals(b.getName()))
                {
                    PLAYER.addPowerUp(e.getEnemyType().getPowerUp());
                    e.destroy();
                    it.remove();
                }
            }
        }
    }
}