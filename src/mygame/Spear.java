package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Spear
{
    public static final long MAX_TIME = 3;
    private Spatial spearModel;
    private final long CREATION_TIME;
    private final Node ROOT_NODE;
    private final BulletAppState BULLET_APP_STATE;
    private final AssetManager ASSET_MANAGER;
    private final Vector3f START_LOCATION, DIRECTION;
    private static final String NAME = Main.getMain().getRPGPrefix() + "SPEAR";
    private RigidBodyControl spearControl;
    
    public Spear(Node rootNode, Vector3f location, Vector3f direction)
    {

        ROOT_NODE = rootNode;
        START_LOCATION = location;
        DIRECTION = direction;
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        CREATION_TIME = System.currentTimeMillis();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        
        launch();
    }
    
    private void launch()
    {
        spearModel = (Node) ASSET_MANAGER.loadModel("Models/spear/spear.j3o");
        spearModel.lookAt(DIRECTION, Main.getUpVector());
        spearModel.rotate(0, FastMath.PI, 0);
        spearModel.setLocalTranslation(START_LOCATION);
        spearModel.setName(NAME);
        spearModel.setLocalScale(1, .5f, 1);
        CollisionShape spearShape = CollisionShapeFactory.createMeshShape(spearModel);
        spearControl = new RigidBodyControl(spearShape, .1f);
        spearModel.addControl(spearControl);
        ROOT_NODE.attachChild(spearModel);
        spearControl.setPhysicsLocation(START_LOCATION);
        BULLET_APP_STATE.getPhysicsSpace().add(spearControl);
        spearControl.setLinearVelocity(DIRECTION.normalizeLocal().mult(100));
    }
    
    public void destroy()
    {
        ROOT_NODE.detachChild(spearModel);
        spearModel.removeControl(spearControl);
        BULLET_APP_STATE.getPhysicsSpace().remove(spearControl);
    }
    
    public long getCreationTime()
    {
        return CREATION_TIME;
    }
    
    public static String getName()
    {
        return NAME;
    }
}