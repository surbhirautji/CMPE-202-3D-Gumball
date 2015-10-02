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
public class NickelInHandState extends CoinAbstractState{
    
    public NickelInHandState(CoinStateMachine machine, GumballMachine gumballMachine) {
        super(machine, gumballMachine);
    }
    
    public String getCoinInHand(){
        return "Nickel in hand";
    }

    public String insertCoin() {
        return gumballMachine.insertNickel();
    }
    
    
    
}
