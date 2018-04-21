/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/**
 *
 * @author jeffr
 */
public class HUDAppState extends BaseAppState
{
    //Base Data
    private final Node GUI_NODE;
    private final AssetManager ASSET_MANAGER;
    private final InputManager INPUT_MANAGER;
    private final BitmapFont GUI_FONT;
    private final int WIDTH, 
            HEIGHT;
    
    //Player Data
    private final Player PLAYER;
    
    //Display
    private final BitmapText HEALTH_TEXT; 
    
    public HUDAppState(Node guiNode, BitmapFont guiFont, Player player)
    {
        GUI_NODE = guiNode;
        GUI_FONT = guiFont;
        PLAYER = player;
        ASSET_MANAGER = Main.getMain().getAssetManager();
        INPUT_MANAGER = Main.getMain().getInputManager();
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
        HEALTH_TEXT = new BitmapText(GUI_FONT, false);
    }
    
    private void updateHealth(int health)
    {
        HEALTH_TEXT.setText("Health: " + health);
        HEALTH_TEXT.setSize(GUI_FONT.getCharSet().getRenderedSize());
        HEALTH_TEXT.setColor(ColorRGBA.Red);
        HEALTH_TEXT.setSize(20);
        HEALTH_TEXT.setLocalTranslation((WIDTH - (.039f * WIDTH)) / 2, HEIGHT, 0);
        GUI_NODE.attachChild(HEALTH_TEXT);
    }
    
    @Override
    protected void initialize(Application app) 
    {
        
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
    
    @Override
    public void update(float tpf)
    {
        updateHealth(PLAYER.getHealth());
    }
}