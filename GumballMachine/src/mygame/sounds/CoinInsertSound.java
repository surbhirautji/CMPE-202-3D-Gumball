/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.sounds;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

/**
 *
 * @author akshaybhasme
 */
public class CoinInsertSound implements GameSounds{

    private AudioNode audio;
    
    public CoinInsertSound(AssetManager assetManager, Node rootNode){
        audio = new AudioNode(assetManager, "Sounds/coin-insert.wav", false);
        audio.setPositional(false);
        audio.setLooping(false);
        audio.setVolume(10);
        rootNode.attachChild(audio);
    }

    public void playSound() {
        audio.playInstance();
    }
}
