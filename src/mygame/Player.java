/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import java.util.List;

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
    private float healthTimer = 0,
            healthTimerGoal = .4f;
    private boolean canChangeHealth = true;
    
    //Name
    private final String BASE_NAME = "Player";
    
    //Input
    private final InputManager INPUT_MANAGER;
    public static final String W = "W",
            A = "A",
            S = "S",
            D = "D";

    public Player()
    {
        PLATFORMER = new Platformer(this);
        ADVENTURER = new Adventurer(this, Main.getMain().getRPGPrefix() + BASE_NAME);
        INPUT_MANAGER = Main.getMain().getInputManager();
        initInput();
    }
    
    private void initInput()
    {
        INPUT_MANAGER.addMapping(W, new KeyTrigger(KeyInput.KEY_W));
        INPUT_MANAGER.addMapping(A, new KeyTrigger(KeyInput.KEY_A));
        INPUT_MANAGER.addMapping(S, new KeyTrigger(KeyInput.KEY_S));
        INPUT_MANAGER.addMapping(D, new KeyTrigger(KeyInput.KEY_D));
        
        INPUT_MANAGER.addListener(this, W);
        INPUT_MANAGER.addListener(this, A);
        INPUT_MANAGER.addListener(this, S);
        INPUT_MANAGER.addListener(this, D);
    }
    
    public void addPowerUp(PowerUp p)
    {
        if(p != null && p != PowerUp.NONE)
        {
            Main.getMain().playAudio(AudioType.POWERUP);
        }
        switch(p)
        {
            case ADD_SPEARS:
            {
                ADVENTURER.modSpears((int) PowerUp.ADD_SPEARS.getValue());
            }
            break;
            case ADD_SPEED:
            {
                PLATFORMER.modSpeed(PowerUp.ADD_SPEED.getValue());
            }
            break;
            case INVINCIBILITY:
            {
                canChangeHealth = false;
                System.out.println(true);
                healthTimer = 0;
                healthTimerGoal = PowerUp.INVINCIBILITY.getValue();
            }
            break;
        }
    }
    
    public void update(float tpf)
    {
        healthTimer += tpf;
        if(healthTimer > healthTimerGoal)
        {
            canChangeHealth = true;
        }
    }
    
    public void addPowerUps(List<PowerUp> powerUps)
    {
        for(PowerUp p : powerUps)
        {
            addPowerUp(p);
        }
    }
    
    public void modHealth(int value)
    {
        if(canChangeHealth)
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

            if(health <= 0)
            {
                Main.getMain().endGame();
            }
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
        if(Main.getMain().getRunning())
        {
            if(Main.getMain().getInPlatformer())
            {
                PLATFORMER.onAction(name, isPressed, tpf);
            }
            else
            {
                ADVENTURER.onAction(name, isPressed, tpf);
            }
        }
    }
}