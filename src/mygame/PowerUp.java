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
    };
    
    public abstract float getValue();
}
