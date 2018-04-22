package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class FireBall
{
    public static final long MAX_TIME = 3;
    private ParticleEmitter fire;
    private final long CREATION_TIME;
    private final Node ROOT_NODE;
    private final BulletAppState BULLET_APP_STATE;
    private final AssetManager ASSET_MANAGER;
    private final Vector3f START_LOCATION, DIRECTION;
    private static final String NAME = "FIREBALL";
    
    public FireBall(Node rootNode, Vector3f location, Vector3f direction)
    {

        ROOT_NODE = rootNode;
        START_LOCATION = location;
        DIRECTION = direction;
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        CREATION_TIME = System.currentTimeMillis();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        
        launchFireBall();
    }
    
    private void launchFireBall()
    {
        fire = new ParticleEmitter(NAME, ParticleMesh.Type.Triangle, 30);
        Material mat = new Material(ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", ASSET_MANAGER.loadTexture("Effects/Explosion/flame.png"));
        fire.setMaterial(mat);
        fire.setImagesX(2); 
        fire.setImagesY(2); // 2x2 texture animation
        fire.setEndColor(ColorRGBA.Blue); 
        fire.setStartColor(ColorRGBA.Green);
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        fire.setStartSize(2);
        fire.setEndSize(2);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(1f);
        fire.setHighLife(3f);
        fire.getParticleInfluencer().setVelocityVariation(0.3f);

        //attach to rootNode
        ROOT_NODE.attachChild(fire);

        //Collision Shape
        SphereCollisionShape fireBallShape = new SphereCollisionShape(2);

        // Position and shoot the fire ball
        fire.setLocalTranslation(START_LOCATION);
        RigidBodyControl fireControl = new RigidBodyControl(fireBallShape, 1f);
        fire.addControl(fireControl);
        BULLET_APP_STATE.getPhysicsSpace().add(fireControl);
        fireControl.setLinearVelocity(DIRECTION.normalizeLocal().mult(250));
    }
    
    public void destroy()
    {
        ROOT_NODE.detachChild(fire);
        fire.removeControl(RigidBodyControl.class);
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