/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.coininhand;

import BussinessLogic.GumballMachine;

/**
 *
 * @author akshaybhasme
 */
public class NothingInHandState extends CoinAbstractState{
    
    public NothingInHandState(CoinStateMachine machine, GumballMachine gumballMachine){
        super(machine, gumballMachine);
    }
    
    public String getCoinInHand(){
        return "Nothing in hand";
    }

    public String insertCoin() {
        return gumballMachine.turnCrank();
    }
    
}
