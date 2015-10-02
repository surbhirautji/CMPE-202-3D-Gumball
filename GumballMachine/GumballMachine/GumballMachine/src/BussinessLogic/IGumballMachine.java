/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

/**
 *
 * @author Christopher
 */

public interface IGumballMachine
{
     String insertQuarter( ) ;  
     String insertDime( ) ;
     String insertNickel( ) ; 
     String turnCrank( ) ;
     boolean isGumballInSlot( ) ; 
     void takeGumballFromSlot( ) ;
}