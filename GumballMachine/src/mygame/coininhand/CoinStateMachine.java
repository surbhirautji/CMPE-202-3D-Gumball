package mygame.coininhand;

import BussinessLogic.GumballMachine;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

/**
 *
 * @author akshaybhasme
 */
public class CoinStateMachine {
    
    private CoinInHandState quarterInHandState;
    private CoinInHandState dimeInHandState;
    private CoinInHandState nickelInHandState;
    private CoinInHandState nothingInHandState;
    
    private CoinInHandState currentState;
    
    //private InputManager inputManager;
    
    public CoinStateMachine(InputManager inputManager, GumballMachine gumballMachine){
        quarterInHandState = new QuarterInHand(this, gumballMachine);
        dimeInHandState = new DimeInHandState(this, gumballMachine);
        nickelInHandState = new NickelInHandState(this, gumballMachine);
        nothingInHandState = new NothingInHandState(this, gumballMachine);
        
        currentState = nothingInHandState;
        
        //this.inputManager = inputManager;
        inputManager.addMapping("Quarter", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Nickel", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("Dime", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_4));
        inputManager.addListener(actionListener, "Quarter", "Nickel", "Dime", "Reset");
    }
    
    public void setState(CoinInHandState state){
        currentState = state;
    }
    
    public CoinInHandState getQuarterInHandState(){
        return quarterInHandState;
    }
    
    public CoinInHandState getDimeInHandState(){
        return dimeInHandState;
    }
    
    public CoinInHandState getNickelInHandState(){
        return nickelInHandState;
    }
    
    public CoinInHandState getNothingInHandState(){
        return nothingInHandState;
    }
    
    public CoinInHandState getCurrentState(){
        return currentState;
    }
    
    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Nickel") && !keyPressed) {
                currentState = nickelInHandState;
            }

            if (name.equals("Dime") && !keyPressed) {
                currentState = dimeInHandState;
            }

            if (name.equals("Quarter") && !keyPressed) {
                currentState = quarterInHandState;
            }

            if (name.equals("Reset") && !keyPressed) {
                currentState = nothingInHandState;
            }
            if(name.equals("Shoot") && !keyPressed){
                currentState.insertCoin();
            }
        }
    };
    
    public ActionListener getActionListener(){
        return actionListener;
    }
    
}
