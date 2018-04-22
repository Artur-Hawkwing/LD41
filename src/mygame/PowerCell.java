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
public class PowerCell 
{
    //Base Data
    private final AssetManager ASSET_MANAGER;
    private Node blockNode;
    private final Node ROOT_NODE;
    private final BulletAppState BULLET_APP_STATE;
    private final Vector3f LOCATION;
    private final float LENGTH = 5,
            WIDTH = 5;
    private final String NAME;
    private static int ID = 0;
    private final Adventurer ADVENTURER;
    
    //Physics data
    CollisionShape blockShape;
    RigidBodyControl blockBody;
    
    public PowerCell(Node rootNode, Vector3f location, String name, Adventurer adventurer)
    {
        ROOT_NODE = rootNode;
        LOCATION = location;
        NAME = name + ID;
        ADVENTURER = adventurer;
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        ID++;
        
        loadModel();
    }
    
    private void loadModel()
    {
        blockNode = (Node) ASSET_MANAGER.loadModel("Models/powerCellv1/powerCellv1.j3o");
        blockNode.setName(NAME);
        blockNode.setLocalScale(1, 1, 1);
        blockShape = CollisionShapeFactory.createMeshShape(blockNode);
        blockBody = new RigidBodyControl(blockShape, 0);
        blockNode.addControl(blockBody);
        ROOT_NODE.attachChild(blockNode);
        blockBody.setPhysicsLocation(LOCATION);
        BULLET_APP_STATE.getPhysicsSpace().add(blockBody);
    }
    public void modHealth(int val)
    {
        ADVENTURER.modHealth(val);
    }
    
    public void destroy()
    {
        BULLET_APP_STATE.getPhysicsSpace().remove(blockBody);
        ROOT_NODE.detachChild(blockNode);
    }
    
    public Vector3f getLocation()
    {
        return blockBody.getPhysicsLocation();
    }
}