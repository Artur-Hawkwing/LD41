/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.Listener;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.FastMath;
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
    private final String MODEL_PATH = "Models/player/player.j3o",
            NAME;
    
    //Physics Data
    private final OpenCharacterControl OPEN_CHARACTER_CONTROL;
    private final float RADIUS = 1,
            HEIGHT = 2,
            MASS = 5;
    private final Vector3f JUMP_FORCE = new Vector3f(0, 60, 0);
    private Vector3f spawn;
    
    //Motion
    private boolean forward,
            backward;
    private final int SPEED = 1500;
    private final float MIN_SPEED = .5f, MAX_SPEED = 1.5f;
    private float speedFactor = MIN_SPEED;
    
    //Health
    private float healthTimer = 0,
            healthTimerGoal = 1f;
    private boolean canChangeHealth = true;
        
    public Platformer(Player player)
    {
        PLAYER = player;
        NAME = Main.getMain().getPlatformPrefix() + "PLATFORMER";
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
        model.rotate(0, FastMath.PI / 2
                , 0);
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
            respawn();
            modHealth(-10);
            OPEN_CHARACTER_CONTROL.setWalkDirection(Vector3f.ZERO);
        }
        
        if(camera != null)
        {
            camera.setLocation(new Vector3f(location.x, location.y + 20, -100));
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
                walkDirection.normalizeLocal().multLocal(SPEED * speedFactor * tpf);
            }
            if(backward)
            {
                walkDirection.addLocal(camLeft);
                walkDirection.normalizeLocal().multLocal(SPEED * speedFactor * tpf);
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
                if(isPressed)
                {
                    OPEN_CHARACTER_CONTROL.jump();
                    Main.getMain().playAudio(AudioType.JUMP);
                }
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
            modSpeed(-.1f);
        }
    }
    
    public void modSpeed(float value)
    {
        speedFactor += value;
        if(speedFactor < MIN_SPEED)
        {
            speedFactor = MIN_SPEED;
        }
        else if(speedFactor > MAX_SPEED)
        {
            speedFactor = MAX_SPEED;
        }
    }
    
    public void stop()
    {
        OPEN_CHARACTER_CONTROL.setWalkDirection(new Vector3f(0, 0, 0));
        forward = false;
        backward = false;
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
    
    public float getSpeedFactor()
    {
        return speedFactor;
    }
}