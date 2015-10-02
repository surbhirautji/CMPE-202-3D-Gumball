package mygame.coininhand;

import BussinessLogic.GumballMachine;

/**
 *
 * @author akshaybhasme
 */
public class QuarterInHand extends  CoinAbstractState{
    
    public QuarterInHand(CoinStateMachine machine, GumballMachine gumballMachine) {
        super(machine, gumballMachine);
    }
    
    public String getCoinInHand(){
        return "Quarter in hand";
    }

    public String insertCoin() {
        return gumballMachine.insertQuarter();
    }
    
}
