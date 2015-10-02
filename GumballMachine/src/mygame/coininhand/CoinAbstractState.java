package mygame.coininhand;

import BussinessLogic.GumballMachine;

/**
 *
 * @author akshaybhasme
 */
public abstract class CoinAbstractState implements CoinInHandState{
    
    protected CoinStateMachine machine;
    protected GumballMachine gumballMachine;

    public CoinAbstractState(CoinStateMachine machine, GumballMachine gumballMachine) {
        this.machine = machine;
        this.gumballMachine = gumballMachine;
    }
    
    public void onQuarterSelected(){
        machine.setState(machine.getQuarterInHandState());
    }
    
    public void onDimeSelected(){
        machine.setState(machine.getDimeInHandState());
    }
    
    public void onNickelSelected(){
        machine.setState(machine.getNickelInHandState());
    }
    
    public void onNothingSelected(){
        machine.setState(machine.getNothingInHandState());
    }

}
