/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.uielements;

import com.jme3.font.BitmapText;
import mygame.Facade;
import mygame.Main;
import mygame.coininhand.CoinInHandState;
import mygame.coininhand.DimeInHandState;
import mygame.coininhand.QuarterInHand;

/**
 *
 * @author Christopher
 */
public class DimeNode implements UIChainInterface{

    private UIChainInterface next;
    private Main app;
    private int count=5;
    
    public DimeNode(Main appTemp){
        app=appTemp;
    }
    
    public void setNext(UIChainInterface next) {
        this.next = next;
    }
    
    public void consumeRequest(CoinInHandState state) {
        if(state instanceof  DimeInHandState){
            BitmapText helloText = (BitmapText) app.getGuiNode().getChild("countDime");
            helloText.setText("" + (--count));
        }else{
            if(next != null)
                next.consumeRequest(state);
            else
                System.out.println("Request not consumed");
        }
    }
    
}
