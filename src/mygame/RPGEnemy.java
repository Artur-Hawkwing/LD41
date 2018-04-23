/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.Random;

/**
 *
 * @author jeffr
 */
public class RPGEnemy 
{
    //Base Data
    private final AssetManager ASSET_MANAGER;
    private ParticleEmitter enemyNode;
    private final Node ROOT_NODE;
    private final BulletAppState BULLET_APP_STATE;
    private final Vector3f LOCATION;
    private static final String PREFIX = Main.getMain().getRPGPrefix()+ "ENEMY_";
    private final String NAME;
    private final RPGEnemyType TYPE;
    private static int ID = 0;
    private RigidBodyControl enemyControl;
    
    //Motion
    private final Random GENERATOR = new Random();
    
    //Attacks
    private float attackTimer;
    private final float ATTACK_TIMER_GOAL;
    private boolean canAttack = false;
    private Fireball fireball;
    
    public RPGEnemy(Node rootNode, Vector3f location, RPGEnemyType type)
    {
        ROOT_NODE = rootNode;
        LOCATION = location;
        TYPE = type;
        NAME = PREFIX + type.name() + ID;
        BULLET_APP_STATE = Main.getMain().getBulletAppState();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        
        attackTimer = GENERATOR.nextInt(5) - 2;
        ATTACK_TIMER_GOAL = type.getAttackDelay();
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
        enemyNode.setEndColor(TYPE.getEndColor()); 
        enemyNode.setStartColor(TYPE.getStartColor());
        enemyNode.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        enemyNode.setStartSize(TYPE.getSize());
        enemyNode.setEndSize(TYPE.getSize());
        enemyNode.setGravity(0, 0, 0);
        enemyNode.setLowLife(1f);
        enemyNode.setHighLife(3f);
        enemyNode.getParticleInfluencer().setVelocityVariation(0.3f);

        ROOT_NODE.attachChild(enemyNode);
        
        //Collision Shape
        SphereCollisionShape fireBallShape = new SphereCollisionShape(TYPE.getSize());

        //Position
        enemyNode.setLocalTranslation(LOCATION);
        enemyControl = new RigidBodyControl(fireBallShape, 0);
        enemyNode.addControl(enemyControl);
        BULLET_APP_STATE.getPhysicsSpace().add(enemyControl);
    }
    
    public Vector3f getLocation()
    {
        return enemyControl.getPhysicsLocation();
    }
    
    public void update(float tpf)
    {
        attackTimer += tpf;
        if(attackTimer >= ATTACK_TIMER_GOAL)
        {
            canAttack = true;
            if(fireball != null)
            {
                fireball.destroy();
            }
        }
        
        if(canAttack)
        {
            Adventurer a = Main.getMain().getPlayer().getAdventurer();
            fireball = new Fireball(ROOT_NODE, getLocation(), a.getLocation());
            Main.getMain().playAudio(AudioType.FIREBALL);
            a.modHealth(-Fireball.getDamage());
            canAttack = false;
            attackTimer = 0;
        }
    }

    public static String getPrefix()
    {
        return PREFIX;
    }
    
    public String getName()
    {
        return NAME;
    }
    
    public RPGEnemyType getEnemyType()
    {
        return TYPE;
    }
    
    public void destroy()
    {
        if(fireball != null)
        {
            fireball.destroy();
        }
        ROOT_NODE.detachChild(enemyNode);
        BULLET_APP_STATE.getPhysicsSpace().remove(enemyControl);
    }
}
