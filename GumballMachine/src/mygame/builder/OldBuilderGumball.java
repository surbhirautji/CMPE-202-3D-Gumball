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

/**
 *
 * @author Vinay
 */
public class OldBuilderGumball implements BuilderGumball
{
        
    GumballPlan gumballplan;
    
	
	public OldBuilderGumball(Main app) {
		gumballplan=new Gumball(app);
            
	}

	public void buildMachineScreenText() {
                gumballplan.initMachineScreenText();
		
	}

}
