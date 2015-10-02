/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

/**
 *
 * @author Christopher
 */


public class SoldState implements IState {
 
    GumballMachine gumballMachine;
 
    public SoldState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
       
	public String insertQuarter() {
		return "Please wait, we're already giving you a gumball";
	}
 
	public String ejectQuarter() {
		return "Sorry, you already turned the crank";
	}
 
	public String insertDime() {
		// TODO Auto-generated method stub
		return "Please wait, we're already giving you a gumball";
	}

	public String ejectDime() {
		// TODO Auto-generated method stub
		return "Sorry, you already turned the crank";
	}

	public String insertNickel() {
		// TODO Auto-generated method stub
		return "Please wait, we're already giving you a gumball";
	}

	public String ejectNickel() {
		// TODO Auto-generated method stub
		return "Sorry, you already turned the crank";
	}
	
	public String turnCrank() {
		return "Turning twice doesn't get you another gumball!";
	}
 
	public String dispense() {
		gumballMachine.releaseBall();
		
		if (gumballMachine.getCount() == 0) {
			gumballMachine.setState(gumballMachine.getSoldOutState());
                        return "A gumball comes rolling out the slot...Oops, out of gumballs! Balance Ejected: " + gumballMachine.getTotal();
		}
		else
                {
			gumballMachine.calculateState();
                        return "A gumball comes rolling out the slot...Balance Remaining: " + gumballMachine.getTotal();
                }               
	}
 
    @Override
	public String toString() {
		return "dispensing a gumball";
	}

}


