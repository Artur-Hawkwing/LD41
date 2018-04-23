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
public enum PowerUp 
{
    NONE
    {
        @Override
        public float getValue()
        {
            return 0;
        }
    },
    
    ADD_SPEARS
    {
        @Override
        public float getValue()
        {
            return 10;
        }
    },
    
    ADD_SPEED
    {
        @Override
        public float getValue()
        {
            return .1f;
        }
    },
    
    INVINCIBILITY
    {
        @Override
        public float getValue()
        {
            return 10f;
        }
    };
    
    public abstract float getValue();
}
