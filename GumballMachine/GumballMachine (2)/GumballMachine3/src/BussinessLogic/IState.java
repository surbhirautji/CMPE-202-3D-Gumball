/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

/**
 *
 * @author Christopher
 */
	

public interface IState {
 
	public String insertDime();
	public String ejectDime();
	public String insertNickel();
	public String ejectNickel();
	
	public String insertQuarter();
	public String ejectQuarter();
	
	
	public String turnCrank();
	public String dispense();
}
