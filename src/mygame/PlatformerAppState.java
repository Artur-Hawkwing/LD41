/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
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
    private final Vector3f OFFSET;
    
    //Light
    private final DirectionalLight DIRECTIONAL_LIGHT;
    
    //Camera Configuration
    private final int FUSTRUM_FAR = 1000;
    
    //Platformer
    private final Platformer PLATFORMER;
    
    public PlatformerAppState(Camera camera, ViewPort viewPort, Vector3f offset, Player player)
    {
        CAMERA = camera;
        VIEW_PORT = viewPort;
        OFFSET = offset;
        PLAYER = player;
        ROOT_NODE = Main.getMain().getRootNode();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        GUI_NODE = Main.getMain().getGuiNode();
        BLACKNESS = new Picture("BLACKNESS");
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
        DIRECTIONAL_LIGHT = new DirectionalLight();
        PLATFORMER = PLAYER.getPlatformer();
    }
    
    @Override
    protected void initialize(Application main) 
    {
        initCamera();
        initViewPort();
        initLight();
        initDimming();
        initPlatformer();
        buildLevel();
    }
    
    private void initCamera()
    {
        CAMERA.setLocation(new Vector3f(0, 0, -10).add(OFFSET));
        CAMERA.setFrustumFar(FUSTRUM_FAR);
    }
    
    private void initViewPort()
    {
        VIEW_PORT.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    }
    
    private void initLight() 
    {
        DIRECTIONAL_LIGHT.setDirection(new Vector3f(-1, -1, 0));
        DIRECTIONAL_LIGHT.setColor(ColorRGBA.White);
        
        ROOT_NODE.addLight(DIRECTIONAL_LIGHT);
    }
    
    private void initDimming()
    {
        BLACKNESS.setImage(ASSET_MANAGER, "Interface/black.png", true);
        BLACKNESS.setHeight(HEIGHT);
        BLACKNESS.setWidth(WIDTH / 2);
        BLACKNESS.setPosition(0, 0);
    }
    
    private void initPlatformer()
    {
        PLATFORMER.setCamera(CAMERA);
    }
    
    private void buildLevel()
    {
        final int BLOCK_LENGTH = 40;
        Block b = new Block(ROOT_NODE, new Vector3f(0, 0, 0).add(OFFSET));
        Block c = new Block(ROOT_NODE, new Vector3f(40, 0, 0).add(OFFSET));
        PLATFORMER.setPhysicsLocation(new Vector3f(40, 80, 0).add(OFFSET));
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
    
    @Override
    public void update(float tpf)
    {
        
    }
}