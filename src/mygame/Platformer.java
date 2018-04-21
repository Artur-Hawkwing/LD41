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
public class Platformer implements ActionListener
{
    //Base Data
    private final Player PLAYER;
    
    //Input
    public final String LEFT = "LEFT",
        RIGHT = "RIGHT",
        JUMP = "JUMP";
        
    public Platformer(Player player)
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
                case LEFT:
                {
                    
                }
                break;
                case RIGHT:
                {
                    
                }
                break;
                case JUMP:
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