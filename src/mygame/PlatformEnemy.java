/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Random;

/**
 *
 * @author jeffr
 */
public class PlatformEnemy 
{
    //Base Data
    private final AssetManager ASSET_MANAGER;
    private ParticleEmitter enemyNode;
    private final Node ROOT_NODE;
    private final BulletAppState BULLET_APP_STATE;
    private final Vector3f LOCATION;
    private static final String PREFIX = Main.getMain().getPlatformPrefix() + "ENEMY";
    private final String NAME;
    private final PlatformEnemyType TYPE;
    private final OpenCharacterControl OPEN_CHARACTER_CONTROL;
    private final float RADIUS = 2,
            HEIGHT = 2,
            MASS = 5;
    private final int SPEED = 1000;
    private static int ID = 0;
    
    //Motion
    private final Random GENERATOR = new Random();
    private boolean backward;
    
    public PlatformEnemy(Node rootNode, Vector3f location, PlatformEnemyType type)
    {
        ROOT_NODE = rootNode;
        LOCATION = location;
        TYPE = type;
        NAME = PREFIX + type.name() + ID;
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        OPEN_CHARACTER_CONTROL = new OpenCharacterControl(RADIUS, HEIGHT, MASS);
        backward = GENERATOR.nextBoolean();
        ID++;
        
        loadModel();
    }
    
    private void loadModel()
    {
        enemyNode = new ParticleEmitter(NAME, ParticleMesh.Type.Triangle, 30);
        Material mat = new Material(ASSET_MANAGER, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", ASSET_MANAGER.loadTexture("Effects/Explosion/flame.png"));
        enemyNode.setMaterial(mat);
        enemyNode.setImagesX(2); 
        enemyNode.setImagesY(2);
        enemyNode.setEndColor(ColorRGBA.Red); 
        enemyNode.setStartColor(ColorRGBA.Yellow);
        enemyNode.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        enemyNode.setStartSize(2);
        enemyNode.setEndSize(2);
        enemyNode.setGravity(0, 0, 0);
        enemyNode.setLowLife(1f);
        enemyNode.setHighLife(3f);
        enemyNode.getParticleInfluencer().setVelocityVariation(0.3f);

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
    
    public PlatformEnemyType getEnemyType()
    {
        return TYPE;
    }
}
