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
public class Adventurer implements ActionListener
{
    //Base Data
    private final Player PLAYER;
    
    //Input
    public final String FORWARDS = "FORWARDS",
            BACKWARDS = "BACKWARDS",
            LEFT = "LEFT",
            RIGHT = "RIGHT";
            
    
    public Adventurer(Player player)
    {
        PLAYER = player;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) 
    {
        if(isPressed)
        {
            switch(name)
            {
                case FORWARDS:
                {
                    
                }
                break;
                case BACKWARDS:
                {
                    
                }
                break;
                case LEFT:
                {
                    
                }
                break;
                case RIGHT:
                {
                    
                }
                break;
            }
        }
    }
    
    public void modHealth(int value)
    {
        PLAYER.modHealth(value);
    }
    
    public int getHealth()
    {
        return PLAYER.getHealth();
    }
}