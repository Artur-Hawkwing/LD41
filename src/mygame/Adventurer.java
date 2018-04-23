/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.Listener;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author jeffr
 */
public class Adventurer
{
    //Base Data
    private final Player PLAYER;
    private final AssetManager ASSET_MANAGER;
    private final BulletAppState BULLET_APP_STATE;
    private final Node ROOT_NODE;
    private final Listener LISTENER;
    private Spatial model;
    private Camera camera;
    private final String MODEL_PATH = "Models/spherev1/spherev1.j3o",
            NAME;
    
    //Physics Data
    private final float RADIUS = 1,
            HEIGHT = 2,
            MASS = 5;
    private final Vector3f JUMP_FORCE = new Vector3f(0, 50, 0);
    private Vector3f spawn;
    
    //Motion
    private boolean forward,
            backward;
    private final int SPEED = 1500;
    
    //Health
    private float spearTimer = 0;
    private final float SPEAR_DELAY = .25f;
    private boolean canThrowSpear = true;
    
    //Attacks
    private final List<Spear> SPEARS = new ArrayList<>();
    private int spearNum = 10;
        
    public Adventurer(Player player, String name)
    {
        PLAYER = player;
        NAME = name;
        ASSET_MANAGER = Main.getMain().getAssetManager();
        ROOT_NODE = Main.getMain().getRootNode();
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        LISTENER = Main.getMain().getListener();
    }
    
    public void update(float tpf)
    {
        spearTimer += tpf;
        
        camera.setLocation(getLocation());
        LISTENER.setLocation(getLocation());
        
        if(spearTimer >= SPEAR_DELAY)
        {
            canThrowSpear = true;
        }
        
        ListIterator<Spear> it = SPEARS.listIterator();
        while(it.hasNext())
        {
            if(it.next().getCreationTime() - System.currentTimeMillis() > 5000)
            {
                it.remove();
            }
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) 
    {
        switch(name)
        {
            case Player.W:
            {
                spear();
            }
            break;
            case Player.A:
            {
                backward = isPressed;
            }
            break;
            case Player.S:
            {
                
            }
            break;
            case Player.D:
            {
                forward = isPressed;
            }
            break;
        }
    }
    
    public void spear()
    {
        if(canThrowSpear && spearNum > 0)
        {
            SPEARS.add(new Spear(ROOT_NODE, camera.getLocation(), camera.getDirection()));
            spearTimer = 0;
            canThrowSpear = false;
            spearNum--;
        }
    }
    
    public void modHealth(int value)
    {
        PLAYER.modHealth(value);
    }
    
    public int getHealth()
    {
        return PLAYER.getHealth();
    }
    
    public int getSpearNum()
    {
        return spearNum;
    }
    
    public void modSpears(int value)
    {
        spearNum += value;
    }
    
    public void setSpawn(Vector3f location)
    {
        spawn = location;
    }
    
    public Vector3f getLocation()
    {
        return camera.getLocation();
    }
    
    public void setCamera(Camera c)
    {
        camera = c;
    }
    
    public String getName()
    {
        return NAME;
    }
}