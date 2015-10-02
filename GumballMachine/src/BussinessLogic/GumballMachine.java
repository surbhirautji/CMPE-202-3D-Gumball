/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import mygame.Facade;
import mygame.sounds.GameSoundFactory;
import mygame.sounds.GameSounds;

/**
 *
 * @author Christopher
 */


public class GumballMachine implements IGumballMachine {
 
	IState soldOutState;
	IState soldState;
	IState noMoneyState;
	IState hasInsufficientMoney;
	IState hasEnoughOrMoreMoney;
	
        AnimChannel channel;
        AnimControl control1;
        Facade f;
 
	IState state = soldOutState;
	int count = 0;
	int total = 0;
	Boolean gumballInSlot=false;
        
        GameSounds insertSound, dispenseSound;
 
	public GumballMachine(int numberGumballs, Node rootNode, AssetManager assetManager) {
		soldOutState = new SoldOutState(this);
		soldState = new SoldState(this);
		noMoneyState = new NoMoneyState(this);
		hasInsufficientMoney = new HasInsufficientMoney(this);
		hasEnoughOrMoreMoney = new HasEnoughOrMoreMoney(this);

		this.count = numberGumballs;
 		if (numberGumballs > 0) {
			state = noMoneyState;
		} 
                
                insertSound = GameSoundFactory.getInstance(rootNode, assetManager).getGameSounds(GameSoundFactory.COIN_INSERT);
                dispenseSound = GameSoundFactory.getInstance(rootNode, assetManager).getGameSounds(GameSoundFactory.COIN_DISPENSE);
	}
 
	public String insertQuarter() {
            insertSound.playSound();
		return state.insertQuarter();
	//	total+=25;
	}
 
	public String ejectQuarter() {
            
		return state.ejectQuarter();
	//	total-=25;
	}
	
	public String insertDime() {
            insertSound.playSound();
		return state.insertDime();
	//	total+=10;
	}
 
	public String ejectDime() {
		return state.ejectDime();
	//	total-=10;
	}
	
	public String insertNickel() {
            insertSound.playSound();
		return state.insertNickel();
	//	total+=5;
	}
 
	public String ejectNickel() {
		return state.ejectNickel();
	//	total-=5;
	}
 
	public String turnCrank() {
            
           
                String msg= state.turnCrank();
                msg = msg +". " + state.dispense();
                dispenseSound.playSound();
		return msg;	
	}

	void setState(IState state) {
		this.state = state;
	}
 
	void releaseBall() {
		if (count != 0) {
			count = count - 1;
		}
		total-=50;
		gumballInSlot=true;
	}
 
	int getCount() {
		return count;
	}
	
	int getTotal() {
		return total;
	}
	
	void addTotal(int amount){
		total+=amount;
	}
	
	void removeTotal(int amount){
		total-=amount;
	}
 
	void refill(int count) {
		this.count = count;
		state = noMoneyState;
		this.total=0;
	}

    public IState getState() {
        return state;
    }
    
    public IState calculateState(){
    	if(total==0)
    		state=noMoneyState;
    	else if(total>=50)
    		state=hasEnoughOrMoreMoney;
    	else
    		state=hasInsufficientMoney;

    	return state;
    }
    
    public IState getNoMoneyState() {
        return noMoneyState;
    }
    
    public IState getSoldOutState() {
        return soldOutState;
    }
    
    public IState getSoldState() {
        return soldState;
    }
    
        @Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("\nMy Gumball, Inc.");
		result.append("\nJava-enabled Standing Gumball Model #2014");
		result.append("\nInventory: " + count + " gumball");
		if (count != 1) {
			result.append("s");
		}
		result.append("\n");
		result.append("Machine is " + state + "\n");
		return result.toString();
	}

	@Override
	public boolean isGumballInSlot() {
		// TODO Auto-generated method stub
		return gumballInSlot;
	}

	@Override
	public void takeGumballFromSlot() {
		// TODO Auto-generated method stub
		gumballInSlot=false;
	}
}
