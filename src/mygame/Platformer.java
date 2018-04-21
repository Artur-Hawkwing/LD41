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
public class Platformer extends BetterCharacterControl
{
    //Base Data
    private final Player PLAYER;
    private final AssetManager ASSET_MANAGER;
    private final BulletAppState BULLET_APP_STATE;
    private final Node ROOT_NODE;
    private final Listener LISTENER;
    private Spatial model;
    private Camera camera;
    
    //Motion
    private boolean forward, backward;
        
    public Platformer(Player player)
    {
        super(20, 40, 5);
        PLAYER = player;
        ASSET_MANAGER = Main.getMain().getAssetManager();
        ROOT_NODE = Main.getMain().getRootNode();
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        LISTENER = Main.getMain().getListener();
        initPhysics();
    }
    
    private void initPhysics()
    {
        
        model = (Node) ASSET_MANAGER.loadModel("Models/sphere/sphere.j3o");
        model.setName("Player");
        model.setLocalScale(5);
        model.addControl(this);
        model.setCullHint(Spatial.CullHint.Always);
        model.move(0, 0, 0);
        BULLET_APP_STATE.getPhysicsSpace().add(this);
        ROOT_NODE.attachChild(model);
        //  setGravity(Main.getGravity());
        setJumpForce(new Vector3f(0, Main.getGravity().y + 5, 0));
    }
    
    @Override
    public void update(float tpf)
    {
        if(camera != null)
        {
            camera.setLocation(new Vector3f(location.x, location.y, -20));
            System.out.println(camera.getLocation());
            LISTENER.setLocation(camera.getLocation());
            LISTENER.setRotation(camera.getRotation());

            Vector3f camDir = new Vector3f(),
                    camLeft = new Vector3f();

            camDir.set(camera.getDirection()).multLocal(.6f);
            camLeft.set(camera.getLeft()).multLocal(.4f);
            setWalkDirection(new Vector3f(0,0,0));

            if(forward)
            {
                walkDirection.addLocal(camDir);
            }
            if(backward)
            {
                walkDirection.addLocal(camDir.negate());
            }
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) 
    {
        switch(name)
        {
            case Player.W:
            {
                jump();
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
    
    public void setPhysicsLocation(Vector3f loc)
    {
        super.setPhysicsLocation(loc);
    }
    
    public Vector3f getPhysicsLocation()
    {
        return location;
    }
    
    public void setCamera(Camera c)
    {
        camera = c;
    }
}