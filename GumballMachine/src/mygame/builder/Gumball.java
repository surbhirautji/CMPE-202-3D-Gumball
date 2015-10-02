/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.builder;

import BussinessLogic.GumballMachine;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import mygame.Main;

public class Gumball implements GumballPlan{

        private BulletAppState bulletAppState;
        Main app;
        AssetManager assetManager;
        Node rootNode;
        GumballMachine gumballMachine;
        BitmapFont guiFont;
        AudioNode audio;
        AudioNode audioCoin;
        
 public Gumball(Main app)
 {
        assetManager=app.getAssetManager();
        rootNode=app.getRootNode();
        guiFont=app.getBitmapFont();
 }

    public void initMachineScreenText() {
            
            Box box2 = new Box(6, 0.5f, 0.2f);
            Geometry red = new Geometry("Box", box2);
            Material mat2 = new Material(assetManager,
                    "Common/MatDefs/Misc/Unshaded.j3md");
            mat2.setColor("Color", ColorRGBA.Black);
            red.setName("MachineScreen");
            red.setMaterial(mat2);
            red.setLocalScale(1.5f);
            red.setLocalTranslation(-0.0f, 5.0f, -8.7f);
            rootNode.attachChild(red);

            guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
            BitmapText inHand = new BitmapText(guiFont, false);
            inHand.setSize(guiFont.getCharSet().getRenderedSize());
            inHand.setText("Welcome");
            inHand.setName("MachineScreenText");
            inHand.setLocalScale(0.03f);
            inHand.setLocalTranslation(6.0f, 5.2f, -9.4f);
            inHand.rotate(0, 3.1f, 0);
            rootNode.attachChild(inHand);
    }
    
    
}
