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
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;

/**
 *
 * @author jeffr
 */
public class PauseAppState extends BaseAppState implements ActionListener
{
    //Base Data
    private final Node GUI_NODE;
    private final BitmapFont GUI_FONT;
    private final InputManager INPUT_MANAGER;
    private final AssetManager ASSET_MANAGER;
    private final int WIDTH, 
        HEIGHT;
    
    //Dimming overlay
    private final Picture DIM;
    
    //Input
    public final String CONTINUE = "CONTINUE",
            QUIT = "QUIT";
    
    //Text
    private final BitmapText PAUSE_TEXT;
    
    public PauseAppState(BitmapFont guiFont)
    {
        GUI_FONT = guiFont;
        GUI_NODE = Main.getMain().getGuiNode();
        INPUT_MANAGER = Main.getMain().getInputManager();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
        DIM = new Picture("DIM");
        PAUSE_TEXT = new BitmapText(GUI_FONT, false);
    }
    
    @Override
    protected void initialize(Application app) 
    {
        initDimming();
        initInput();
        initPauseText();
    }
    
    private void initDimming()
    {
        DIM.setImage(ASSET_MANAGER, "Interface/dim.png", true);
        DIM.setHeight(HEIGHT);
        DIM.setWidth(WIDTH);
        DIM.setPosition(0, 0);
    }

    private void initInput()
    {
        INPUT_MANAGER.addMapping(QUIT, new KeyTrigger(KeyInput.KEY_Q));
        
        INPUT_MANAGER.addListener(this, QUIT);
    }
    
    private void initPauseText()
    {
        PAUSE_TEXT.setText("Paused\nPress Q to quit");
        PAUSE_TEXT.setSize(GUI_FONT.getCharSet().getRenderedSize());
        PAUSE_TEXT.setColor(ColorRGBA.Red);
        PAUSE_TEXT.setSize(20);
        PAUSE_TEXT.setLocalTranslation((WIDTH - (.039f * WIDTH)) / 2, HEIGHT, 0);
    }
    
    @Override
    protected void cleanup(Application app) 
    {
        
    }

    @Override
    protected void onEnable() 
    {
        GUI_NODE.attachChild(DIM);
        GUI_NODE.attachChild(PAUSE_TEXT);
    }

    @Override
    protected void onDisable() 
    {
        INPUT_MANAGER.removeListener(this);
        GUI_NODE.detachAllChildren();
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) 
    {
        if(isPressed)
        {
            switch(name)
            {
                case QUIT:
                {
                    Runtime.getRuntime().exit(0);
                }
            }
        }
    }
}