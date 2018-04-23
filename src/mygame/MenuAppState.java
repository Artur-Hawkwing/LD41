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
    private final int TEXT_SIZE;
    
    //Text
    private final String[] INTRODUCTION = 
    {
        "You are King Circle, lord of the two-dimensional universe! Unfortunately, you have a lot of enemies who think you are not "
            + "\nfit for kingship on account of your poor logic. (You see, you were very prone to *circular reasoning*). These enemies have "
            + "\nconspired to eliminate you by casting you into a third dimension; only your fast reflexes let you *roll* out of the way of "
            + "\nthe spell. Sadly for you, however, you still feel some *tangential* after effects. You now exist into both dimensions! And "
            + "\nin both, enemies formed from fire are consumed with a *burning* desire to kill you. In order to live, you will have to draw "
            + "\nupon your knowledge of shifting between dimensions to stay alive and get *around* the assassination attempt. Can you survive "
            + "\nthe attack, or will your attempts fall *flat*?",
        "Controls:\n" + "General:\n" + "\tEsc: Pause\n" + "\tSpace: change games\n" + "Platformer:\n" + "\tA: Backwards\n" + "\tD: Forwards\n" + "\tW: Jump\n" + "\tS: Collect power-ups\n" 
            + "Shooter:\n" + "\tW: Shoot\n" + "\t[Mouse]: Move view",
        "Rules:\nHealth is shared between the two games, but all other effects are independent. You lose when you are reduced to 0 "
            + "\nhealth. Your goal is to survive for as long as you can."
            + "\n\nPlatformer: Your goal is to get to the final portal, represented by a green tile. Throughout the map are blue "
            + "\ntiles; these are power ups that apply to the shooter part of the game (They replenish your weapons). When you "
            + "\ncollect them, their tile disappears. The longer you stay in the realm, the more enemies spawn. You also take "
            + "\nconstant damage while in the platformer, so more quickly!"
            + "\n\nShooter: Your goal is to kill all the enemies that spawn. Over time, more and more enemies will come. There are four "
            + "\ntypes of enemies, some of which grant power-ups when killed:\n"
            + "\tGreen: these are your basic enemies. They have no special traits. (common)\n"
            + "\tBlue: these attack much more quickly than any other enemies. (uncommon)\n"
            + "\tGray: these increase the speed of the platformer when killed (rare)\n"
            + "\tYellow: these make both you invincible for a time. (very rare)"
            + "\n\nPress Space to begin!"
    };
    private int currentLoc = 0;
    private BitmapText currentText;
    private Picture mainScreen,
            background;
    
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
        TEXT_SIZE = WIDTH / 96;
    }
    
    @Override
    protected void initialize(Application app) 
    {
        initBackgroundImage();
        displayText(INTRODUCTION[currentLoc]);
        initInput();
    }
    
    private void initMainScreen()
    {
        /*mainScreen = new Picture("Main Picture");
        mainScreen.setImage(ASSET_MANAGER, "Interface/b.png", true);
        mainScreen.setWidth(WIDTH);
        mainScreen.setHeight(HEIGHT);
        mainScreen.setPosition(0, 0);
        GUI_NODE.attachChild(mainScreen);*/
    }
    
    private void initBackgroundImage()
    {
        background = new Picture("Background Picture");
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
        currentText.setSize(TEXT_SIZE);
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