/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

/**
 *
 * @author Christopher
 */

public class NoMoneyState implements IState{
    GumballMachine gumballMachine;
 
    public NoMoneyState(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
 
	public String insertQuarter() {
		gumballMachine.addTotal(25);
		gumballMachine.calculateState();
		return "Quarter inserted. Total: " + gumballMachine.getTotal();
	}
 
	public String ejectQuarter() {
		return "You haven't inserted a quarter";
	}
 
	public String insertDime() {
		gumballMachine.addTotal(10);
		gumballMachine.calculateState();
		return "Dime inserted. Total: " + gumballMachine.getTotal();
	}

	public String ejectDime() {
		// TODO Auto-generated method stub
		return "You haven't inserted a dime";
	}

	public String insertNickel() {
		gumballMachine.addTotal(5);
		gumballMachine.calculateState();
		return "Nickel inserted. Total: " + gumballMachine.getTotal();
	}

	public String ejectNickel() {
		// TODO Auto-generated method stub
		return "You haven't inserted a nickel";
	}
	
	public String turnCrank() {
		return "You turned, but there's no money";
	 }
 
	public String dispense() {
		return "You need to pay first";
	} 
 
    @Override
	public String toString() {
		return "waiting for some money to be added";
	}

}

