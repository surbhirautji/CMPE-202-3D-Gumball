package mygame.sounds;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author akshaybhasme
 */
public class GameSoundFactory {
    
    public static final String COIN_INSERT = "coininsert";
    public static final String COIN_DISPENSE = "coindispense";
    
    private Node rootNode;
    private AssetManager assetManager;
    
    private static GameSoundFactory INSTANCE;
    
    private GameSoundFactory(Node rootNode, AssetManager assetManager){
        this.rootNode = rootNode;
        this.assetManager = assetManager;
    }
    
    public static GameSoundFactory getInstance(Node rootNode, AssetManager assetManager){
        if(INSTANCE == null)
            INSTANCE = new GameSoundFactory(rootNode, assetManager);
        return INSTANCE;
    }
    
    public GameSounds getGameSounds(String name){
        if(name.equalsIgnoreCase(COIN_INSERT)){
            return new CoinInsertSound(assetManager, rootNode);
        }else if(name.equalsIgnoreCase(COIN_DISPENSE)){
            return new CoinDispenseSound(assetManager, rootNode);
        }
        throw new UnsupportedOperationException();
    }
    
    
}
