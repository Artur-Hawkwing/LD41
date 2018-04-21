/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;

/**
 *
 * @author jeffr
 */
public class OpenCharacterControl extends BetterCharacterControl
{
    private final float STANDARD_FRICTION;
    
    public OpenCharacterControl()
    {
        super();
        STANDARD_FRICTION = rigidBody.getFriction();
    }
    
    public OpenCharacterControl(float radius, float height, float mass)
    {
        super(radius, height, mass);
        STANDARD_FRICTION = rigidBody.getFriction();
    }
    
    public Vector3f getLocation()
    {
        return location;
    }
    
    public PhysicsRigidBody getPhysicsRigidBody()
    {
        return rigidBody;
    }
    
    public void setFriction(int value)
    {
        rigidBody.setFriction(value);
    }
    
    public void resetFriction()
    {
        rigidBody.setFriction(STANDARD_FRICTION);
    }
}
