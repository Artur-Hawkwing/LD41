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
public class EndGameAppState extends BaseAppState implements ActionListener
{
    //Base Data
    private final Node GUI_NODE;
    private final AssetManager ASSET_MANAGER;
    private final InputManager INPUT_MANAGER;
    private final BitmapFont GUI_FONT;
    private final int WIDTH, 
            HEIGHT;
    
    //Display
    private BitmapText bitmapText;
    private Picture mainScreen,
            background;
    private float time;
    private String message;
    private final int TEXT_SIZE;
    
    //Input
    private final String QUIT = "QUIT",
            RESTART = "RESTART";

    public EndGameAppState(BitmapFont guiFont)
    {
        GUI_FONT = guiFont;
        GUI_NODE = Main.getMain().getGuiNode();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        INPUT_MANAGER = Main.getMain().getInputManager();
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
        TEXT_SIZE = WIDTH / 96;
    }
    
    @Override
    protected void initialize(Application app) 
    {
        initBackgroundImage();
        initText();
        displayText(message);
        initInput();
    }
    
    private void initBackgroundImage()
    {
        background = new Picture("Background Picture");
        background.setImage(ASSET_MANAGER, "Interface/kingCircleDim.png", true);
        background.setWidth(WIDTH);
        background.setHeight(HEIGHT);
        background.setPosition(0, 0);
        GUI_NODE.attachChild(background);
    }
    
    private void initText()
    {
        message = "Game over! You survived for " + time + " seconds!\n"
                + "To play again, press space\n"
                + "To quit, press Q.";
    }
    
    private void displayText(String text)
    {
        if(bitmapText != null)
        {
            GUI_NODE.detachChild(bitmapText);
        }
        bitmapText = new BitmapText(GUI_FONT, false);
        bitmapText.setText(text);
        bitmapText.setSize(GUI_FONT.getCharSet().getRenderedSize());
        bitmapText.setColor(ColorRGBA.Blue);
        bitmapText.setSize(TEXT_SIZE);
        bitmapText.setLocalTranslation(WIDTH / 4, HEIGHT / 2, 0);
        GUI_NODE.attachChild(bitmapText);
    }
    
    private void initInput()
    {
        INPUT_MANAGER.addMapping(QUIT, new KeyTrigger(KeyInput.KEY_Q));
        INPUT_MANAGER.addMapping(RESTART, new KeyTrigger(KeyInput.KEY_SPACE));
    }

    public void setTime(float t)
    {
        time = t;
    }
    
    @Override
    protected void cleanup(Application app) 
    {
        
    }

    @Override
    protected void onEnable() 
    {
        INPUT_MANAGER.addListener(this, QUIT);
        INPUT_MANAGER.addListener(this, RESTART);
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
        switch(name)
        {
            case QUIT:
            {
                Runtime.getRuntime().exit(0);
            }
            break;
            case RESTART:
            {
                Main.getMain().beginNew();
            }
            break;
        }
    }
}