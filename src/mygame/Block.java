/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author jeffr
 */
public class Block 
{
    //Base Data
    private final AssetManager ASSET_MANAGER;
    private Node blockNode;
    private final Node ROOT_NODE;
    private final BulletAppState BULLET_APP_STATE;
    private final Vector3f LOCATION;
    private final float LENGTH,
            WIDTH = 5;
    
    //Physics data
    CollisionShape blockShape;
    RigidBodyControl blockBody;
    
    public Block(Node rootNode, Vector3f location, float length)
    {
        ROOT_NODE = rootNode;
        LOCATION = location;
        LENGTH = length;
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        
        loadModel();
    }
    
    private void loadModel()
    {
        blockNode = (Node) ASSET_MANAGER.loadModel("Models/cubev4/cubev4.j3o");
        blockNode.setLocalScale(LENGTH, 1, WIDTH);
        blockShape = CollisionShapeFactory.createMeshShape(blockNode);
        blockBody = new RigidBodyControl(blockShape, 0);
        blockNode.addControl(blockBody);
        ROOT_NODE.attachChild(blockNode);
        blockBody.setPhysicsLocation(LOCATION);
        BULLET_APP_STATE.getPhysicsSpace().add(blockBody);
    }
    
    public Vector3f getLocation()
    {
        return blockBody.getPhysicsLocation();
    }
}
