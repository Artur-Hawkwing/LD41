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
public enum BlockType 
{
    STANDARD
    {
        @Override
        public String getPath()
        {
            return "Models/standardBlock/standardBlock.j3o";
        }
    },
    
    LEFT_END
    {
        @Override
        public String getPath()
        {
            return "Models/standardBlock/standardBlock.j3o";
        }
    },
    
    RIGHT_END
    {
        @Override
        public String getPath()
        {
            return "Models/standardBlock/standardBlock.j3o";
        }
    },
    
    POWER
    {
        @Override
        public String getPath()
        {
            return "Models/powerBlock/powerBlock.j3o";
        }
    },
    
    FINISH
    {
        @Override
        public String getPath()
        {
            return "Models/finishBlockv1/finishBlockv1.j3o";
        }
    };
    
    public abstract String getPath();
}