/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.Listener;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author jeffr
 */
public class Platformer
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
    private final OpenCharacterControl OPEN_CHARACTER_CONTROL;
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
    private float healthTimer = 0,
            healthTimerGoal = .35f;
    private boolean canChangeHealth = true;
        
    public Platformer(Player player, String name)
    {
        PLAYER = player;
        NAME = name;
        OPEN_CHARACTER_CONTROL = new OpenCharacterControl(RADIUS, HEIGHT, MASS);
        ASSET_MANAGER = Main.getMain().getAssetManager();
        ROOT_NODE = Main.getMain().getRootNode();
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        LISTENER = Main.getMain().getListener();
        initPhysics();
    }
    
    private void initPhysics()
    {
        model = ASSET_MANAGER.loadModel(MODEL_PATH);
        model.setName(NAME);
        ROOT_NODE.attachChild(model);
        model.addControl(OPEN_CHARACTER_CONTROL);
        OPEN_CHARACTER_CONTROL.setJumpForce(JUMP_FORCE);
        BULLET_APP_STATE.getPhysicsSpace().add(OPEN_CHARACTER_CONTROL);
    }
    
    public void update(float tpf)
    {
        Vector3f location = getLocation();
        
        healthTimer += tpf;
        if(healthTimer > healthTimerGoal)
        {
            canChangeHealth = true;
        }
        
        if(location.y < -5)
        {
            setPhysicsLocation(spawn);
            OPEN_CHARACTER_CONTROL.setWalkDirection(Vector3f.ZERO);
        }
        
        if(camera != null)
        {
            camera.setLocation(new Vector3f(location.x, 20, -100));
            camera.lookAt(location.add(new Vector3f(0, 30,  0)), Main.getUpVector());
            LISTENER.setLocation(camera.getLocation());
            LISTENER.setRotation(camera.getRotation());

            Vector3f camDir = new Vector3f(),
                    camLeft = new Vector3f(),
                    walkDirection = new Vector3f(0, 0, 0);

            camDir.set(camera.getDirection()).multLocal(.6f);
            camLeft.set(camera.getLeft()).multLocal(.4f);

            if(forward)
            {
                walkDirection.addLocal(camLeft.negate());
                walkDirection.normalizeLocal().multLocal(SPEED * tpf);
            }
            if(backward)
            {
                walkDirection.addLocal(camLeft);
                walkDirection.normalizeLocal().multLocal(SPEED * tpf);
            }

            OPEN_CHARACTER_CONTROL.setWalkDirection(walkDirection);
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) 
    {
        switch(name)
        {
            case Player.W:
            {
                OPEN_CHARACTER_CONTROL.jump();
            }
            break;
            case Player.A:
            {
                backward = isPressed;
            }
            break;
            case Player.S:
            {
                claimPowerUps();
            }
            break;
            case Player.D:
            {
                forward = isPressed;
            }
            break;
        }
    }
    
    public void claimPowerUps()
    {
        PLAYER.addPowerUps(Main.getMain().getPlatformerAppState().collectPowerUps(getLocation()));
    }
    
    public void modHealth(int value)
    {
        if(canChangeHealth)
        {
            PLAYER.modHealth(value);
            canChangeHealth = false;
            healthTimer = 0;
        }
    }
    
    public void stop()
    {
        OPEN_CHARACTER_CONTROL.setWalkDirection(new Vector3f(0, 0, 0));
    }
    
    public int getHealth()
    {
        return PLAYER.getHealth();
    }
    
    public void setSpawn(Vector3f location)
    {
        spawn = location;
    }
    
    public void respawn()
    {
        OPEN_CHARACTER_CONTROL.warp(spawn);
    }
    
    private void setPhysicsLocation(Vector3f location)
    {
        OPEN_CHARACTER_CONTROL.warp(location);
    }
    
    public Vector3f getLocation()
    {
        return ROOT_NODE.getChild(NAME).getLocalTranslation();
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