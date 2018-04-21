package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Random;

public class Tree 
{
    private final AssetManager ASSET_MANAGER;
    private final Node ROOT_NODE;
    private final BulletAppState BULLET_APP_STATE;
    private Node tree;
    private CollisionShape treeShape;
    private RigidBodyControl treeBody;
    private final Vector3f LOCATION;
    private static int wood = 100;
    public static int c = 0;
    private float scale = 1;
    private final boolean REPEL;
    private final Random GENERATOR = new Random();
    
    public Tree(Node rootNode, final Vector3f LOCATION)
    {
        this.ASSET_MANAGER = Main.getMain().getAssetManager();
        this.ROOT_NODE = rootNode;
        this.BULLET_APP_STATE = Main.getMain().getBulletAppState();
        this.LOCATION = LOCATION;
        this.scale = 4;
        this.REPEL = true;
        initTree();
        c++;
    }
    
    private void initTree()
    {
        switch(GENERATOR.nextInt(3))
        {
            case 0:
            {
                tree = (Node) ASSET_MANAGER.loadModel("Models/basicTree/basicTree.j3o");
                break;
            }
            case 1:
            {
                tree = (Node) ASSET_MANAGER.loadModel("Models/basicTree/basicTree.j3o");
                break;
            }
            case 2:
            {
                tree = (Node) ASSET_MANAGER.loadModel("Models/basicTree/basicTree.j3o");
                break;
            }
        }

        tree.setName("tree" + c);
        float rand1 = GENERATOR.nextFloat();
        float rand2 = GENERATOR.nextFloat();
        float vertScalar = (rand1 < .5f ? .5f : rand1) * 4;
        float horizScalar = (rand2 < .5f ? .5f : rand2) * 4;
        tree.setLocalScale(horizScalar * scale, vertScalar * scale, horizScalar * scale);
        treeShape = new BoxCollisionShape(new Vector3f(scale, 4 * scale, scale));
        treeBody = new RigidBodyControl(treeShape, 0);
        tree.addControl(treeBody);
        ROOT_NODE.attachChild(tree);
        treeBody.setPhysicsLocation(LOCATION);
        BULLET_APP_STATE.getPhysicsSpace().add(treeBody);
    }
    
    public boolean repel()
    {
        return REPEL;
    }
    
    public Vector3f loc()
    {
        return LOCATION;
    }
    
    public Vector2f loc2D()
    {
        return new Vector2f(treeBody.getPhysicsLocation().x, treeBody.getPhysicsLocation().z);
    }
    
    public float getScale() 
    {
        return scale;
    }
}

