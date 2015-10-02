/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

/**
 *
 * @author Vinay
 */
public class BuilderEngg {
    private BuilderGumball builderGumball;
	
	// OldRobotBuilder specification is sent to the engineer
	
	public BuilderEngg(BuilderGumball builderGumball){
		
		this.builderGumball = builderGumball;
		
	}
	
	// Return the Robot made from the OldRobotBuilder spec
	
	/*public Gumball getRobot(){
		
		return this.builderGumball.getRobot();
		
	}*/
	
	// Execute the methods specific to the RobotBuilder 
	// that implements RobotBuilder (OldRobotBuilder)
	
	public void makeMachineText() {
		
		//this.builderGumball.buildPlayerAndCollision();
		//this.builderGumball.buildAudio();
		//this.builderGumball.buildButtonCrank();
		//this.builderGumball.buildVariables();
               this.builderGumball.buildMachineScreenText();
		
	}
}
