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
            return ColorRGBA.Green;
        }
        
        @Override
        public ColorRGBA getEndColor()
        {
            return ColorRGBA.Black;
        }
    },
    FAST
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
            return ColorRGBA.Blue;
        }
        
        @Override
        public ColorRGBA getEndColor()
        {
            return ColorRGBA.Black;
        }
    },
    SPECIAL
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
    };
    
    public abstract PowerUp getPowerUp();
    public abstract float getSize();
    public abstract ColorRGBA getStartColor();
    public abstract ColorRGBA getEndColor();
}
