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
    private final String MODEL_PATH = "Models/sphere/sphere.j3o",
            NAME = "Platformer";
    
    //Physics Data
    protected final BetterCharacterControl BETTER_CHARACTER_CONTROL;
    protected final float RADIUS = 1,
            HEIGHT = 1,
            MASS = 1;
    
    //Motion
    private boolean forward, backward;
        
    public Platformer(Player player)
    {
        PLAYER = player;
        BETTER_CHARACTER_CONTROL = new BetterCharacterControl(RADIUS, HEIGHT, MASS);
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
        model.addControl(BETTER_CHARACTER_CONTROL);
        BULLET_APP_STATE.getPhysicsSpace().add(BETTER_CHARACTER_CONTROL);
        
        //setGravity(Main.getGravity());
        //setJumpForce(new Vector3f(0, Main.getGravity().y + 5, 0));
    }
    
    public void update(float tpf)
    {
        if(camera != null)
        {
            Vector3f location = getLocation();
            camera.setLocation(new Vector3f(location.x, location.y, -20));
            System.out.println(camera.getLocation());
            LISTENER.setLocation(camera.getLocation());
            LISTENER.setRotation(camera.getRotation());

            Vector3f camDir = new Vector3f(),
                    camLeft = new Vector3f(),
                    walkDirection = Vector3f.ZERO;

            camDir.set(camera.getDirection()).multLocal(.6f);
            camLeft.set(camera.getLeft()).multLocal(.4f);

            if(forward)
            {
                walkDirection.addLocal(camDir);
            }
            if(backward)
            {
                walkDirection.addLocal(camDir.negate());
            }
            
            BETTER_CHARACTER_CONTROL.setWalkDirection(walkDirection);
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) 
    {
        switch(name)
        {
            case Player.W:
            {
                BETTER_CHARACTER_CONTROL.jump();
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
    
    public void modHealth(int value)
    {
        PLAYER.modHealth(value);
    }
    
    public int getHealth()
    {
        return PLAYER.getHealth();
    }
    
    public void setPhysicsLocation(Vector3f location)
    {
        model.setLocalTranslation(location);
    }
    
    public Vector3f getLocation()
    {
        return ROOT_NODE.getChild(NAME).getLocalTranslation();
    }
    
    public void setCamera(Camera c)
    {
        camera = c;
    }
}