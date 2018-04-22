/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.ui.Picture;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;

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
    private final AmbientLight AMBIENT_LIGHT;
    
    //Camera Configuration
    private final int FUSTRUM_FAR = 1000;
    
    //Map
    private final Map<Color, BlockType> BLOCK_COLOR_MAPPING = new HashMap<>();
    private final Map<Color, EnemyType> ENEMY_COLOR_MAPPING = new HashMap<>();
    private final List<Enemy> ENEMIES = new ArrayList<>();
    private final List<Block> LEFT_BLOCKS = new ArrayList<>(),
            RIGHT_BLOCKS = new ArrayList<>(),
            POWER_BLOCKS = new ArrayList<>(),
            STANDARD_BLOCKS = new ArrayList<>();
    private Block finishBlock,
            firstBlock;
    
    
    //Random
    private final Random GENERATOR = new Random();
    
    //Platformer
    private final Platformer PLATFORMER;
    
    //Collision
    private final String PREFIX;
    
    public PlatformerAppState(Camera camera, ViewPort viewPort, Vector3f offset, Player player, String prefix)
    {
        CAMERA = camera;
        VIEW_PORT = viewPort;
        OFFSET = offset;
        PLAYER = player;
        PREFIX = prefix;
        ROOT_NODE = Main.getMain().getRootNode();
        ASSET_MANAGER = Main.getMain().getAssetManager();
        GUI_NODE = Main.getMain().getGuiNode();
        BLACKNESS = new Picture("BLACKNESS");
        WIDTH = Main.getDimensions().width;
        HEIGHT = Main.getDimensions().height;
        DIRECTIONAL_LIGHT = new DirectionalLight();
        AMBIENT_LIGHT = new AmbientLight();
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
        
        AMBIENT_LIGHT.setColor(ColorRGBA.White.mult(.5f));
        
        ROOT_NODE.addLight(DIRECTIONAL_LIGHT);
        ROOT_NODE.addLight(AMBIENT_LIGHT);
    }
    
    private void initDimming()
    {
        BLACKNESS.setImage(ASSET_MANAGER, "Interface/dim.png", true);
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
        initBlockColorMapping();
        initEnemyColorMappping();
        generatePlatformsAndEnemies();
    }
    
    private void initBlockColorMapping()
    {
        BLOCK_COLOR_MAPPING.put(Color.BLACK, BlockType.STANDARD);
        BLOCK_COLOR_MAPPING.put(Color.YELLOW, BlockType.LEFT_END);
        BLOCK_COLOR_MAPPING.put(Color.RED, BlockType.FINISH);
        BLOCK_COLOR_MAPPING.put(Color.BLUE, BlockType.RIGHT_END);
        BLOCK_COLOR_MAPPING.put(Color.GREEN, BlockType.POWER);
    }
    
    private void initEnemyColorMappping()
    {
        ENEMY_COLOR_MAPPING.put(Color.MAGENTA, EnemyType.SPIKEBALL);
    }
    
    private void generatePlatformsAndEnemies()
    {
        final int LENGTH = 5;
        boolean first = true;
        int level = GENERATOR.nextInt(2) + 1;
        File file= new File("Assets/Maps/lvl" + level + ".png");
        try
        {
            BufferedImage image = ImageIO.read(file);
            for(int x = 0; x < image.getWidth(); x++)
            {
                for(int y = 0; y < image.getHeight(); y++)
                {
                    Color c = new Color(image.getRGB(x, y));
                    BlockType blockType = BLOCK_COLOR_MAPPING.get(c);
                    EnemyType enemyType = ENEMY_COLOR_MAPPING.get(c);
                    
                    if(blockType != null)
                    {
                        Block block = new Block(ROOT_NODE, new Vector3f((image.getWidth() - x) * LENGTH, (image.getHeight() - y), 0).add(OFFSET), LENGTH, PREFIX + blockType.name(), blockType);
                        if(blockType.name().equals(BlockType.LEFT_END.name()))
                        {
                            LEFT_BLOCKS.add(block);
                        }
                        else if(blockType.name().equals(BlockType.RIGHT_END.name()))
                        {
                            RIGHT_BLOCKS.add(block);
                        }
                        else if(blockType.name().equals(BlockType.POWER.name()))
                        {
                            POWER_BLOCKS.add(block);
                        }
                        else if(blockType.name().equals(BlockType.STANDARD.name()))
                        {
                            STANDARD_BLOCKS.add(block);
                        }
                        else if(blockType.name().equals(BlockType.FINISH.name()))
                        {
                            finishBlock = block;
                        }
                        
                        if(first)
                        {
                            PLATFORMER.setSpawn(block.getLocation().add(new Vector3f(0, 5, 0)));
                            PLATFORMER.respawn();
                            first = false;
                        }
                    }
                    else if(enemyType != null)
                    {
                        ENEMIES.add(new Enemy(ROOT_NODE, new Vector3f((image.getWidth() - x) * LENGTH, (image.getHeight() - y), 0).add(OFFSET).add(new Vector3f(0, 5, 0)), enemyType));
                    }
                }
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    @Override
    protected void cleanup(Application main) 
    {
        ROOT_NODE.removeLight(DIRECTIONAL_LIGHT);
        ROOT_NODE.removeLight(AMBIENT_LIGHT);
        for(Block b : POWER_BLOCKS)
        {
            b.destroy();
        }
        for(Block b : LEFT_BLOCKS)
        {
            b.destroy();
        }
        for(Block b : RIGHT_BLOCKS)
        {
            b.destroy();
        }
        for(Block b : STANDARD_BLOCKS)
        {
            b.destroy();
        }
        finishBlock.destroy(); 
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
        PLATFORMER.stop();
    }
    
    public List<PowerUp> collectPowerUps(Vector3f location)
    {
        List<PowerUp> powerUps = new ArrayList<>();
        ListIterator<Block> it = POWER_BLOCKS.listIterator();
        while(it.hasNext())
        {
            Block b = it.next();
            if(b.getLocation().distance(location) < 20)
            {
                PowerUp[] values = PowerUp.values();
                powerUps.add(values[GENERATOR.nextInt(values.length)]);
                b.destroy();
                it.remove();
            }
        }
        return powerUps;
    }
    
    @Override
    public void update(float tpf)
    {
        PLATFORMER.update(tpf);
        
        if(finishBlock != null)
        {
            if(PLATFORMER.getLocation().distance(finishBlock.getLocation()) < 5)
            {
                Main.getMain().resetPlatformerAppState();
            }
        }
        
        for(Enemy e : ENEMIES)
        {
            e.update(tpf);
            
            if(e.getLocation().distance(PLATFORMER.getLocation()) < 3)
            {
                PLATFORMER.modHealth(-e.getEnemyType().getDamage());
            }
            
            for(Block b : LEFT_BLOCKS)
            {
                if(e.getLocation().distance(b.getLocation()) < 10)
                {
                    e.setBackward(false);
                }
            }
            for(Block b : RIGHT_BLOCKS)
            {
                if(e.getLocation().distance(b.getLocation()) < 10)
                {
                    e.setBackward(true);
                }
            }
        }
    }
    
    public void collision(Spatial a, Spatial b)
    {        
        //Reverse enemy motion at end of blocks
        if(a.getName().startsWith(Enemy.getPrefix()) && (b.getName().equals(BlockType.LEFT_END.name())))
        {
            for(Enemy e : ENEMIES)
            {
                if(a.getName().equals(e.getName()))
                {
                    e.setBackward(false);
                }
            }
        }
        else if(a.getName().startsWith(Enemy.getPrefix()) && (b.getName().equals(BlockType.RIGHT_END.name())))
        {
            for(Enemy e : ENEMIES)
            {
                if(a.getName().equals(e.getName()))
                {
                    e.setBackward(false);
                }
            }
        }
        else if(b.getName().startsWith(Enemy.getPrefix()) && (a.getName().equals(BlockType.LEFT_END.name())))
        {
            for(Enemy e : ENEMIES)
            {
                if(b.getName().equals(e.getName()))
                {
                    e.setBackward(false);
                }
            }
        }
        else if(b.getName().startsWith(Enemy.getPrefix()) && (a.getName().equals(BlockType.RIGHT_END.name())))
        {
            for(Enemy e : ENEMIES)
            {
                if(b.getName().equals(e.getName()))
                {
                    e.setBackward(false);
                }
            }
        }
    }
}