/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.ColorRGBA;

/**
 *
 * @author jeffr
 */
public enum RPGEnemyType 
{
    COMMON
    {
        @Override
        public PowerUp getPowerUp()
        {
            return PowerUp.NONE;
        }
        
        @Override
        public float getSize()
        {
            return 2;
        }
        
        @Override
        public ColorRGBA getStartColor()
        {
            return ColorRGBA.Green;
        }
        
        @Override
        public ColorRGBA getEndColor()
        {
            return ColorRGBA.Black;
        }
        
        @Override
        public float getAttackDelay()
        {
            return 5;
        }
    },
    
    FAST
    {
        @Override
        public PowerUp getPowerUp()
        {
            return PowerUp.NONE;
        }
        
        @Override
        public float getSize()
        {
            return 2;
        }
        
        @Override
        public ColorRGBA getStartColor()
        {
            return ColorRGBA.Blue;
        }
        
        @Override
        public ColorRGBA getEndColor()
        {
            return ColorRGBA.Black;
        }
        
        @Override
        public float getAttackDelay()
        {
            return 2;
        }
    },
    
    POWER_SPEED
            {
        @Override
        public PowerUp getPowerUp()
        {
            return PowerUp.ADD_SPEED;
        }
        
        @Override
        public float getSize()
        {
            return 2;
        }
        
        @Override
        public ColorRGBA getStartColor()
        {
            return ColorRGBA.DarkGray;
        }
        
        @Override
        public ColorRGBA getEndColor()
        {
            return ColorRGBA.LightGray;
        }
        
        @Override
        public float getAttackDelay()
        {
            return 5;
        }
    },
    
   POWER_INVINCIBLE
    {
        @Override
        public PowerUp getPowerUp()
        {
            return PowerUp.INVINCIBILITY;
        }
        
        @Override
        public float getSize()
        {
            return 2;
        }
        
        @Override
        public ColorRGBA getStartColor()
        {
            return ColorRGBA.Yellow;
        }
        
        @Override
        public ColorRGBA getEndColor()
        {
            return ColorRGBA.Black;
        }
        
        @Override
        public float getAttackDelay()
        {
            return 5;
        }
    };
    
    public abstract PowerUp getPowerUp();
    public abstract float getSize();
    public abstract ColorRGBA getStartColor();
    public abstract ColorRGBA getEndColor();
    public abstract float getAttackDelay();
}
