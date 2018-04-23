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
public enum PlatformEnemyType 
{
    SPIKEBALL
    {
        @Override
        public String getPath()
        {
            return "Models/spikeBall/spikeBall.j3o";
        }
        
        @Override
        public int getDamage()
        {
            return 10;
        }
    };
    public abstract String getPath();
    public abstract int getDamage();
}
