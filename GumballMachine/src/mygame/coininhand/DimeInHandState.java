package mygame.coininhand;

import BussinessLogic.GumballMachine;

/**
 *
 * @author akshaybhasme
 */
public class DimeInHandState extends CoinAbstractState{
    
    
    public DimeInHandState(CoinStateMachine machine, GumballMachine gumballMachine){
        super(machine, gumballMachine);
    }
    
    public String getCoinInHand(){
        return "Dime in hand";
    }

    public String  insertCoin() {
        return gumballMachine.insertDime();
    }
    
    
}
