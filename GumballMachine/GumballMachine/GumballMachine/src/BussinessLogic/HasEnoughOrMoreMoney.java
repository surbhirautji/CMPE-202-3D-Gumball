/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

/**
 *
 * @author Christopher
 */

public class HasEnoughOrMoreMoney implements IState  {
	GumballMachine gumballMachine;
	 
	public HasEnoughOrMoreMoney(GumballMachine gumballMachine) {
		this.gumballMachine = gumballMachine;
	}
  
	public String insertQuarter() {
		gumballMachine.addTotal(25);
		return "Quarter Added. Total: " + gumballMachine.getTotal();
	}
 
	public String ejectQuarter() {
		gumballMachine.removeTotal(25);
		gumballMachine.calculateState();
		return "Quarter removed. Total: " + gumballMachine.getTotal();
	}
	
	public String insertDime() {
		gumballMachine.addTotal(10);
		return "Dime Added. Total: " + gumballMachine.getTotal();
	}

	public String ejectDime() {
		gumballMachine.removeTotal(10);
		gumballMachine.calculateState();
		return "Dime removed. Total: " + gumballMachine.getTotal();
	}

	public String insertNickel() {
		gumballMachine.addTotal(5);
		return "Nickel inserted. Total: " + gumballMachine.getTotal();
	}

	public String ejectNickel() {
		gumballMachine.removeTotal(5);
		gumballMachine.calculateState();
		return "Total: " + gumballMachine.getTotal();
	}
	
 
	public String turnCrank() {
		gumballMachine.setState(gumballMachine.getSoldState());
		return "Crank turned";
		//System.out.println("Balance Remaining: " + gumballMachine.getTotal());
	}

    public String dispense() {
        return "No gumball dispensed";
    }
 
        @Override
	public String toString() {
		return "waiting for turn of crank";
	}
}

