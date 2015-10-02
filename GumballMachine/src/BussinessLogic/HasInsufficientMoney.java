/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

/**
 *
 * @author Christopher
 */

public class HasInsufficientMoney implements IState  {
	GumballMachine gumballMachine;
	 
    public HasInsufficientMoney(GumballMachine gumballMachine) {
        this.gumballMachine = gumballMachine;
    }
 
	public String insertQuarter() {
		gumballMachine.addTotal(25);
		gumballMachine.calculateState();
		return "Quarter inserted. Total: " + gumballMachine.getTotal();
	}
 
	public String ejectQuarter() {
		if(gumballMachine.getTotal()<25)
			return "Insufficient Balance. Total: " + gumballMachine.getTotal();
		else
		{
			gumballMachine.removeTotal(25);
			gumballMachine.calculateState();
                        return "Quarter returned. Total: " + gumballMachine.getTotal();
		}
		
	}
 
	public String insertDime() {
		gumballMachine.addTotal(10);
		gumballMachine.calculateState();
		return "Dime inserted. Total: " + gumballMachine.getTotal();
	}

	public String ejectDime() {
		// TODO Auto-generated method stub
		if(gumballMachine.getTotal()<10)
			return "Insufficient Balance. Total: " + gumballMachine.getTotal();
		else
		{	
			gumballMachine.removeTotal(10);
			gumballMachine.calculateState();
                        return "Dime returned. Total: " + gumballMachine.getTotal();
		}
	
	}

	public String insertNickel() {
		gumballMachine.addTotal(5);
		gumballMachine.calculateState();
		return "Nickel inserted. Total: " + gumballMachine.getTotal();
	}

	public String ejectNickel() {
		// TODO Auto-generated method stub
		if(gumballMachine.getTotal()<5)
			return "Insufficient Balance. Total: " + gumballMachine.getTotal();
		else
		{
			gumballMachine.removeTotal(5);
			gumballMachine.calculateState();
                        return "Nickel returned. Total: " + gumballMachine.getTotal();
		}
	}
	
	public String turnCrank() {
               //System.out.print("asdasd");
		return "You turned, but there's not enough money";
	 }
 
	public String dispense() {
		return "You need to pay first";
	} 
 
        @Override
	public String toString() {
		return "waiting for some money to be added";
	}
}
