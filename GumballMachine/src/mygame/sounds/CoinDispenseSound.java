package mygame.sounds;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 *
 * @author akshaybhasme
 */
public class CoinDispenseSound implements GameSounds{
    
    private AudioNode audio;
    
    public CoinDispenseSound(AssetManager assetManager, Node rootNode){
        audio = new AudioNode(assetManager, "Sounds/bounce.wav", false);
        audio.setPositional(false);
        audio.setLooping(false);
        audio.setVolume(10);
        rootNode.attachChild(audio);
    }

    public void playSound() {
        audio.playInstance();
    }
}
