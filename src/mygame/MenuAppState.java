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
public class MenuAppState extends BaseAppState implements ActionListener
{
    //Base Data
    private final Node GUI_NODE;
    private final AssetManager ASSET_MANAGER;
    private final InputManager INPUT_MANAGER;
    private final BitmapFont GUI_FONT;
    private final int WIDTH, 
            HEIGHT;
    
    //Text
    private final String[] INTRODUCTION = 
    {
        "You are lying on the ground in sleep. Slowly, you begin to awake from what seems like an eternity of darkness.",
        "Your enemy! You have finally beaten him! Now you begin to remember...the fight...then something. What put you \n"
            + "to sleep anyway?",
        "You promised your subjects you would unite the kingdom and exile Aeoln the Divider. Now he is dead...but why…?",
        "\"You may unite the kingdom, but you shall not find peace again!\" Those were his last words. Why?",
        "You open one eye and see your native land of Oiba.",
        "You open the other eye and see… WHAT???!!!!!",
        "You close it again. That can’t be right.",
        "You peak out again. It is still there. Abio, the reverse of your world. Two-dimmensional, devoid of life. You \n"
            + "see into each realm with your two eyes.",
        "So who are you? The platformer? The adventurer? The two should never have been forced to unite. But now...you must\n"
            + " live through the trials of both, or else fall to Aeoln’s final curse.",
        "You slowly stumble to your feet...twice. After all, you can only move in one world at a time. But if you get hurt in\n"
            + " one, you somehow know that you will feel the blow in both…",
        "The realms envelop you, a duality though which you now walk.",
        "Press Space or Right Arrow to begin..."
    };
    private int currentLoc = 0;
    private BitmapText currentText;
    private Picture background;
    
    //Input
    private final String NEXT_TEXT = "0",
            LAST_TEXT = "1";
    
    public MenuAppState(BitmapFont guiFont)
    {
        GUI_FONT = guiFont;
        GUI_NODE = Main.getMain().getGuiNode();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        INPUT_MANAGER = Main.getMain().getInputManager();
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
    }
    
    @Override
    protected void initialize(Application app) 
    {
        initBackgroundImage();
        displayText(INTRODUCTION[currentLoc]);
        initInput();
    }
    
    private void initBackgroundImage()
    {
        background = new Picture("HUD Picture");
        background.setImage(ASSET_MANAGER, "Interface/b.png", true);
        background.setWidth(WIDTH);
        background.setHeight(HEIGHT);
        background.setPosition(0, 0);
        GUI_NODE.attachChild(background);
    }
    
    private void displayText(String text)
    {
        if(currentText != null)
        {
            GUI_NODE.detachChild(currentText);
        }
        currentText = new BitmapText(GUI_FONT, false);
        currentText.setText(text);
        currentText.setSize(GUI_FONT.getCharSet().getRenderedSize());
        currentText.setColor(ColorRGBA.Red);
        currentText.setSize(20);
        currentText.setLocalTranslation(WIDTH / 4, 3 * HEIGHT / 4, 0);
        GUI_NODE.attachChild(currentText);
    }
    
    private void initInput()
    {
        INPUT_MANAGER.addMapping(NEXT_TEXT, new KeyTrigger(KeyInput.KEY_SPACE));
        INPUT_MANAGER.addMapping(NEXT_TEXT, new KeyTrigger(KeyInput.KEY_RIGHT));
        INPUT_MANAGER.addMapping(LAST_TEXT, new KeyTrigger(KeyInput.KEY_LEFT));
    }

    @Override
    protected void cleanup(Application app) 
    {
        
    }

    @Override
    protected void onEnable() 
    {
        INPUT_MANAGER.addListener(this, NEXT_TEXT);
        INPUT_MANAGER.addListener(this, LAST_TEXT);
    }

    @Override
    protected void onDisable() 
    {
        if(!Main.getMain().getInMenu())
        {
            GUI_NODE.detachChild(currentText);
            GUI_NODE.detachChild(background);
        }
        INPUT_MANAGER.removeListener(this);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) 
    {
        if(isPressed)
        {
            switch(name)
            {
                case NEXT_TEXT:
                {
                    if(currentLoc + 1 < INTRODUCTION.length)
                    {
                        currentLoc++;
                        displayText(INTRODUCTION[currentLoc]);
                    }
                    else
                    {
                        Main.getMain().loadGame();
                    }
                }
                break;
                case LAST_TEXT:
                {
                    if(currentLoc > 0)
                    {
                        currentLoc--;
                        displayText(INTRODUCTION[currentLoc]);
                    }
                }
                break;
            }
        }
    }
}