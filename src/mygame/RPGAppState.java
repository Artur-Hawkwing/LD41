/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;

/**
 *
 * @author jeffr
 */
public class RPGAppState extends BaseAppState 
{
    //Base Data
    private final Camera CAMERA;
    private final ViewPort VIEW_PORT;
    private final Node ROOT_NODE;
    private final Player PLAYER;
    
    //Camera Configuration
    private final int FUSTRUM_FAR = 1000;
    
    public RPGAppState(Camera camera, ViewPort viewPort, Node rootNode, Player player)
    {
        CAMERA = camera;
        VIEW_PORT = viewPort;
        ROOT_NODE = rootNode;
        PLAYER = player;
    }
    
    @Override
    protected void initialize(Application app) 
    {
        initCamera();
        initViewPort();
    }
    
    private void initCamera()
    {
        CAMERA.setFrustumFar(FUSTRUM_FAR);
    }
    
    private void initViewPort()
    {
        VIEW_PORT.setBackgroundColor(ColorRGBA.Yellow);
    }

    @Override
    protected void cleanup(Application app) 
    {

    }

    @Override
    protected void onEnable() 
    {
        
    }

    @Override
    protected void onDisable() 
    {
        
    }
}