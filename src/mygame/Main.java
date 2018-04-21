package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.system.AppSettings;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Main extends SimpleApplication implements ActionListener
{
    //Base Data
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private final static int MAX_FRAMERATE = 100;
    private static final Main MAIN = new Main();
    
    //Second view
    private Camera cam2;
    private ViewPort viewPort2;
    private final Vector3f PLATFORMER_OFFSET = new Vector3f(100_000, 0, 0);
    
    //App States
    private PlatformerAppState platformerAppState;
    private RPGAppState rpgAppState;
    private BulletAppState bulletAppState;
    private MenuAppState menuAppState;
    private HUDAppState hudAppState;
    private PauseAppState pauseAppState;
    private boolean inPlatformer = false,
            inMenu = true,
            running = true;
    
    //Input
    private final String SWITCH_GAME = "SWITCH_GAME",
            PAUSE = "PAUSE";
    
    //Player Data
    private Player player;
    
    //Physics
    private static final Vector3f GRAVITY = new Vector3f(0, -30, 0),
            UP_VECTOR = new Vector3f(0, 1, 0);
    
    public static void main(String[] args) 
    {
        initSettings();
        MAIN.start();
    }
    
    private static void initSettings()
    {
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(MAX_FRAMERATE);
        settings.setTitle("Split");
        settings.setFullscreen(true);
        settings.setResolution(SCREEN_SIZE.width, SCREEN_SIZE.height);
        MAIN.setSettings(settings);
        MAIN.setDisplayFps(false);
        MAIN.setDisplayStatView(false);
    }

    @Override
    public void simpleInitApp() 
    {
        initViewPorts();
        initPlayer();
        initAppStates();
        initInput();
        loadMenu();
    }
    
    private void initViewPorts()
    {
        //View 1
        cam.setViewPort(0f, .5f, 0f, 1f);
        flyCam.setEnabled(false);
        
        //View 2
        cam2 = cam.clone();
        viewPort2 = renderManager.createMainView("Cam2Viewport", cam2);
        viewPort2.setClearFlags(true, true, true);
        viewPort2.attachScene(rootNode);
        cam2.setViewPort(.5f, 1f, 0f, 1f);
    }

    private void initPlayer()
    {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        player = new Player();
    }
    
    private void initAppStates()
    {
        platformerAppState = new PlatformerAppState(cam, viewPort, PLATFORMER_OFFSET, player);
        rpgAppState = new RPGAppState(cam2, viewPort2, player);
        menuAppState = new MenuAppState(guiFont);
        hudAppState = new HUDAppState(guiFont, player);
        pauseAppState = new PauseAppState(guiFont);
        
        stateManager.attach(rpgAppState);
        stateManager.attach(platformerAppState);
        stateManager.attach(hudAppState);
        stateManager.attach(menuAppState);
        stateManager.attach(pauseAppState);
        
        rpgAppState.setEnabled(false);
        platformerAppState.setEnabled(false);
        hudAppState.setEnabled(false);
        menuAppState.setEnabled(false);
        pauseAppState.setEnabled(false);
    }
    
    private void initInput()
    {
        inputManager.deleteMapping(INPUT_MAPPING_EXIT);
        inputManager.addMapping(SWITCH_GAME, new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping(PAUSE, new KeyTrigger(KeyInput.KEY_ESCAPE));
        
        inputManager.addListener(this, SWITCH_GAME);
        inputManager.addListener(this, PAUSE);
    }
    
    private void loadMenu()
    {
        menuAppState.setEnabled(true);
    }
    
    public void loadGame()
    {
        inMenu = false;
        
        //Remove Menu
        menuAppState.setEnabled(false);
        
        //Load HUD
        hudAppState.setEnabled(true);
        
        //Start one of the games
        changeActiveGame();
    }
    
    @Override
    public void simpleUpdate(float tpf) 
    {
        
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) 
    {
        if(isPressed)
        {
            switch(name)
            {
                case SWITCH_GAME:
                {
                    if(!inMenu)
                    {
                        changeActiveGame();
                    }
                }
                break;
                case PAUSE:
                {
                    pause();
                }
                break;
            }
        }
    }
    
    private void changeActiveGame()
    {
        if(inPlatformer)
        {
            platformerAppState.setEnabled(false);
            rpgAppState.setEnabled(true);
            inPlatformer = false;
        }
        else
        {
            rpgAppState.setEnabled(false);
            platformerAppState.setEnabled(true);
            inPlatformer = true;
        }
    }
    
    private void pause()
    {
        if(running)
        {    
            rpgAppState.setEnabled(false);
            platformerAppState.setEnabled(false);
            bulletAppState.setEnabled(false);
            menuAppState.setEnabled(false);
            pauseAppState.setEnabled(true);
        }
        else
        {  
            pauseAppState.setEnabled(false);
            bulletAppState.setEnabled(true);

            if(inMenu)
            {
                menuAppState.setEnabled(true);
            }
            else if(inPlatformer)
            {
                platformerAppState.setEnabled(true);
            }
            else
            {
                rpgAppState.setEnabled(true);
            }
        }
        running = !running;
    }
    
    public static Dimension getDimensions()
    {
        return SCREEN_SIZE;
    }
    
    public static Main getMain()
    {
        return MAIN;
    }
    
    public BulletAppState getBulletAppState()
    {
        return bulletAppState;
    }
    
    public boolean getInMenu()
    {
        return inMenu;
    }
    
    public boolean getRunning()
    {
        return running;
    }
    
    public boolean getInPlatformer()
    {
        return inPlatformer;
    }
    
    public static Vector3f getGravity()
    {
        return GRAVITY;
    }
        
    public static Vector3f getUpVector()
    {
        return UP_VECTOR;
    }
    
}