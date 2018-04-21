/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author jeffr
 */
public class Adventurer
{
    //Base Data
    private final Player PLAYER;
            
    public Adventurer(Player player)
    {
        PLAYER = player;
    }

    public void onAction(String name, boolean isPressed, float tpf) 
    {
        if(isPressed)
        {
            switch(name)
            {
                case Player.W:
                {
                    
                }
                break;
                case Player.A:
                {
                    
                }
                break;
                case Player.S:
                {
                    
                }
                break;
                case Player.D:
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