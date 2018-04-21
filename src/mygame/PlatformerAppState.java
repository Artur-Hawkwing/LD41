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
public class PlatformerAppState extends BaseAppState
{
    //Base Data
    private final Camera CAMERA;
    private final ViewPort VIEW_PORT;
    private final AssetManager ASSET_MANAGER;
    private final Node ROOT_NODE,
            GUI_NODE;
    private final Player PLAYER;
    private final Picture BLACKNESS;
    private final int WIDTH, 
        HEIGHT;
    
    //Camera Configuration
    private final int FUSTRUM_FAR = 1000;
    
    public PlatformerAppState(Camera camera, ViewPort viewPort, Node rootNode, Player player)
    {
        CAMERA = camera;
        VIEW_PORT = viewPort;
        ROOT_NODE = rootNode;
        PLAYER = player;
        ASSET_MANAGER = Main.getMain().getAssetManager();
        GUI_NODE = Main.getMain().getGuiNode();
        BLACKNESS = new Picture("BLACKNESS");
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
    }
    
    @Override
    protected void initialize(Application main) 
    {
        initCamera();
        initViewPort();
        initDimming();
        buildLevel();
    }
    
    private void initCamera()
    {
        CAMERA.setLocation(new Vector3f(0, -20, 0));
        CAMERA.setFrustumFar(FUSTRUM_FAR);
    }
    
    private void initViewPort()
    {
        VIEW_PORT.setBackgroundColor(ColorRGBA.Blue);
    }
    
    private void initDimming()
    {
        BLACKNESS.setImage(ASSET_MANAGER, "Interface/black.png", true);
        BLACKNESS.setHeight(HEIGHT);
        BLACKNESS.setWidth(WIDTH / 2);
        BLACKNESS.setPosition(0, 0);
    }
    
    private void buildLevel()
    {
        for(int x = 0; x < 50; x++)
        {
            Block b = new Block(ROOT_NODE, new Vector3f(x, 0, 0));
        }
    }

    @Override
    protected void cleanup(Application main) 
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