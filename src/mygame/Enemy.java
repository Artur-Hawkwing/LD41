/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author jeffr
 */
public class Enemy 
{
    //Base Data
    private final AssetManager ASSET_MANAGER;
    private Node enemyNode;
    private final Node ROOT_NODE;
    private final BulletAppState BULLET_APP_STATE;
    private final Vector3f LOCATION;
    private static final String PREFIX = Main.getMain().getPlatformPrefix() + "ENEMY";
    private final String NAME;
    private final EnemyType TYPE;
    private final OpenCharacterControl OPEN_CHARACTER_CONTROL;
    private final float RADIUS = 2,
            HEIGHT = 2,
            MASS = 5;
    private final int SPEED = 1000;
    private static int ID = 0;
    
    //Motion
    private boolean backward = true;
    
    public Enemy(Node rootNode, Vector3f location, EnemyType type)
    {
        ROOT_NODE = rootNode;
        LOCATION = location;
        TYPE = type;
        NAME = PREFIX + type.name() + ID;
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        OPEN_CHARACTER_CONTROL = new OpenCharacterControl(RADIUS, HEIGHT, MASS);
        ID++;
        
        loadModel();
    }
    
    private void loadModel()
    {
        enemyNode = (Node) ASSET_MANAGER.loadModel(TYPE.getPath());
        enemyNode.setName(NAME);
        ROOT_NODE.attachChild(enemyNode);
        enemyNode.addControl(OPEN_CHARACTER_CONTROL);
        BULLET_APP_STATE.getPhysicsSpace().add(OPEN_CHARACTER_CONTROL);
        OPEN_CHARACTER_CONTROL.warp(LOCATION);
    }
    
    public Vector3f getLocation()
    {
        return OPEN_CHARACTER_CONTROL.getLocation();
    }
    
    public void update(float tpf)
    {
        Vector3f walkDirection = new Vector3f(backward ? 1 : -1, 0, 0),
                location = getLocation();
        walkDirection.multLocal(SPEED * tpf);
        
        if(location.z > .5f | location.z < -.5f)
        {
            OPEN_CHARACTER_CONTROL.warp(new Vector3f(location.x, location.y, 0));
        }
        
        System.out.println(walkDirection);
        OPEN_CHARACTER_CONTROL.setWalkDirection(walkDirection);
    }
    
    public void setBackward(boolean value)
    {
        backward = value;
    }
    
    public static String getPrefix()
    {
        return PREFIX;
    }
    
    public String getName()
    {
        return NAME;
    }
    
    public EnemyType getEnemyType()
    {
        return TYPE;
    }
}
