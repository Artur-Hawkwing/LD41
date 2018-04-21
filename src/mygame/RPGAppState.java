/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 *
 * @author jeffr
 */
public class RPGAppState extends BaseAppState 
{
    //Base Data
    private final Camera CAMERA;
    private final ViewPort VIEW_PORT;
    private final Player PLAYER;
    private final AssetManager ASSET_MANAGER;
    private final Node ROOT_NODE,
            GUI_NODE;
    private final Picture BLACKNESS;
    private final int WIDTH, 
        HEIGHT;
    
    //Camera Configuration
    private final int FUSTRUM_FAR = 1000;
    
    public RPGAppState(Camera camera, ViewPort viewPort, Player player)
    {
        CAMERA = camera;
        VIEW_PORT = viewPort;
        PLAYER = player;
        ROOT_NODE = Main.getMain().getRootNode();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        GUI_NODE = Main.getMain().getGuiNode();
        BLACKNESS = new Picture("BLACKNESS");
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
    }
    
    @Override
    protected void initialize(Application app) 
    {
        initCamera();
        initViewPort();
        initDimming();
    }
    
    private void initCamera()
    {
        CAMERA.setFrustumFar(FUSTRUM_FAR);
    }
    
    private void initViewPort()
    {
        VIEW_PORT.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    }
    
    private void initDimming()
    {
        BLACKNESS.setImage(ASSET_MANAGER, "Interface/black.png", true);
        BLACKNESS.setHeight(HEIGHT);
        BLACKNESS.setWidth(WIDTH / 2);
        BLACKNESS.setPosition(WIDTH / 2, 0);
    }

    @Override
    protected void cleanup(Application app) 
    {

    }

    @Override
    protected void onEnable() 
    {
        GUI_NODE.detachChild(BLACKNESS);
    }

    @Override
    protected void onDisable() 
    {
        GUI_NODE.attachChild(BLACKNESS);
    }
}