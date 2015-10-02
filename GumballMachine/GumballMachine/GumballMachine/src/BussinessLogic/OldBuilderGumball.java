/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

import mygame.Main;

/**
 *
 * @author Vinay
 */
public class OldBuilderGumball implements BuilderGumball
{
    //Main app;
    private Gumball gumball;
	
	public OldBuilderGumball(Main app) {
		
		this.gumball = new Gumball(app);
		
	}
	/*
	public void buildPlayerAndCollision() {
		
		gumball.initPlayerAndCollision();
		
	}

	public void buildButtonCrank() {
		
		gumball.initButtonCrank();
		
	}

	public void buildVariables() {
		
		gumball.initVariables();
		
	}*/

	public void buildMachineScreenText() {
		
		gumball.initMachineScreenText();
		
	}
        
        /*
        public void buildAudio(){
                gumball.initAudio();
        }*/

	public Gumball getRobot() {
		
		return this.gumball;
	}
	
    
}
