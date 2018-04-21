/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.input.controls.ActionListener;

/**
 *
 * @author jeffr
 */
public class Player implements ActionListener
{
    //Characters in both worlds
    private final Platformer PLATFORMER;
    private final Adventurer ADVENTURER;
    
    //Data common to all forms of the character
    private final int MAX_HEALTH = 100;
    private int health = MAX_HEALTH;
    
    //Input
    public static final String W = "W",
            A = "A",
            S = "S",
            D = "D";
    
    public Player()
    {
        PLATFORMER = new Platformer(this);
        ADVENTURER = new Adventurer(this);
    }
    
    public void modHealth(int value)
    {
        int newHealthTemp = health + value;
        if(newHealthTemp > MAX_HEALTH)
        {
            health = MAX_HEALTH;
        }
        else
        {
            health += value;
        }
    }
    
    public int getHealth()
    {
        return health;
    }
    
    public Adventurer getAdventurer()
    {
        return ADVENTURER;
    }
    
    public Platformer getPlatformer()
    {
        return PLATFORMER;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) 
    {
        if(Main.getMain().getInPlatformer())
        {
            PLATFORMER.onAction(name, isPressed, tpf);
        }
        else
        {
            PLATFORMER.onAction(name, isPressed, tpf);
        }
    }
}