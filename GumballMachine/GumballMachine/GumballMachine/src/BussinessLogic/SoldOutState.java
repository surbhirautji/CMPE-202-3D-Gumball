/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

/**
 *
 * @author Christopher
 */


public class SoldOutState implements IState {
    GumballMachine gumballMachine;
 
    public SoldOutState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
 
	public String insertQuarter() {
		return "You can't insert a quarter, the machine is sold out";
	}
 
	public String ejectQuarter() {
		return "You can't eject, you haven't inserted a quarter yet";
	}
 
	public String insertDime() {
		// TODO Auto-generated method stub
		return "You can't insert a Dime, the machine is sold out";
		
	}

	public String ejectDime() {
		// TODO Auto-generated method stub
		return "You can't eject, you haven't inserted a Dime yet";
		
	}

	public String insertNickel() {
		// TODO Auto-generated method stub
		return "You can't insert a Nickel, the machine is sold out";
	}

	public String ejectNickel() {
		// TODO Auto-generated method stub
		return "You can't eject, you haven't inserted a Nickel yet";
	}
	
	public String turnCrank() {
		return "You turned, but there are no gumballs";
	}
 
	public String dispense() {
		return "No gumball dispensed";
	}
 
    @Override
	public String toString() {
		return "sold out";
	}
}

