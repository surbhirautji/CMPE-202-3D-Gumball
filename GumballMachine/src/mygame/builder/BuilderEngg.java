/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.builder;

/**
 *
 * @author Vinay
 */
public class BuilderEngg {
    private BuilderGumball builderGumball;

	public BuilderEngg(BuilderGumball builderGumball){
		
		this.builderGumball = builderGumball;
		
	}

	public void makeBuilder() {
		
		//this.builderGumball.buildPlayerAndCollision();
		//this.builderGumball.buildAudio();
		//this.builderGumball.buildButtonCrank();
		//this.builderGumball.buildVariables();
               this.builderGumball.buildMachineScreenText();
		
	}
}
