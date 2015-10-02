package mygame.movement;

import BussinessLogic.GumballMachine;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import mygame.coininhand.CoinInHandState;
import mygame.coininhand.CoinStateMachine;
import mygame.coininhand.NothingInHandState;

/**
 *
 * @author akshaybhasme
 */
public class GumballPlayer implements Player{
    
    private CoinStateMachine coinStateMachine;
    
    
    
    public GumballPlayer(InputManager inputManager, GumballMachine gumballMachine){
        coinStateMachine = new CoinStateMachine(inputManager, gumballMachine);
        //initKeys();
    }
    
   
    public Vector3f moveAhead(Vector3f walkDirection, Vector3f camDirection){
        walkDirection.addLocal(camDirection);
        return walkDirection;
    }
    
    public Vector3f moveBack(Vector3f walkDirection, Vector3f camDirection){
         walkDirection.addLocal(camDirection.negate()); 
         return walkDirection;
    }
    
    public Vector3f moveLeft(Vector3f walkDirection, Vector3f camDirection){
        walkDirection.addLocal(camDirection);
        return walkDirection;
    }
    
    public Vector3f moveRight(Vector3f walkDirection, Vector3f camDirection){
        walkDirection.addLocal(camDirection.negate());
        return walkDirection;
    }
    
    public Vector3f jump(Vector3f walkDirection, Vector3f camDirection){
        return walkDirection;
    }
    
    public void quarterInHand(){
        coinStateMachine.setState(coinStateMachine.getQuarterInHandState());
    }
    
    public void nickelInHand(){
        coinStateMachine.setState(coinStateMachine.getNickelInHandState());
    }
    
    public void dimeInHand(){
        coinStateMachine.setState(coinStateMachine.getDimeInHandState());
    }
    
    public void nothingInHand(){
        coinStateMachine.setState(coinStateMachine.getNothingInHandState());
    }
    
    public String insertCoin(){
        System.out.println(coinStateMachine.getCurrentState().getClass().getName());
        return coinStateMachine.getCurrentState().insertCoin();
    }
    
    public void turnCrank(){
        if(coinStateMachine.getCurrentState() instanceof NothingInHandState)
            coinStateMachine.getCurrentState().insertCoin();
        else{
            //TODO switch user to nothing in hand state
        }
    }
    
    public CoinInHandState getCoinInHand(){
        return coinStateMachine.getCurrentState();
    }
}
