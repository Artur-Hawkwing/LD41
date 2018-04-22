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
    private final BitmapText HEALTH_TEXT, HEALTH_TEXT_2; 
    
    public HUDAppState(BitmapFont guiFont, Player player)
    {
        GUI_FONT = guiFont;
        PLAYER = player;
        GUI_NODE = Main.getMain().getGuiNode();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        INPUT_MANAGER = Main.getMain().getInputManager();
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
        HEALTH_TEXT = new BitmapText(GUI_FONT, false);
        HEALTH_TEXT_2 = new BitmapText(GUI_FONT, false);
    }
    
    private void updateLabels(int health, float time)
    {
        HEALTH_TEXT.setText("Health: " + health + "\nTime: " + (int) time);
        HEALTH_TEXT.setSize(GUI_FONT.getCharSet().getRenderedSize());
        HEALTH_TEXT.setColor(ColorRGBA.Red);
        HEALTH_TEXT.setSize(20);
        HEALTH_TEXT.setLocalTranslation((WIDTH - (.039f * WIDTH)) / 4, HEIGHT, 0);
        
        HEALTH_TEXT_2.setText("Health: " + health + "\nTime: " + (int) time);
        HEALTH_TEXT_2.setSize(GUI_FONT.getCharSet().getRenderedSize());
        HEALTH_TEXT_2.setColor(ColorRGBA.Red);
        HEALTH_TEXT_2.setSize(20);
        HEALTH_TEXT_2.setLocalTranslation((3 * (WIDTH - (.039f * WIDTH))) / 4, HEIGHT, 0);
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
        GUI_NODE.attachChild(HEALTH_TEXT);
        GUI_NODE.attachChild(HEALTH_TEXT_2);
    }

    @Override
    protected void onDisable() 
    {
        GUI_NODE.detachChild(HEALTH_TEXT);
        GUI_NODE.detachChild(HEALTH_TEXT_2);
    }
    
    @Override
    public void update(float tpf)
    {
        updateLabels(PLAYER.getHealth(), Main.getMain().getTime());
    }
}