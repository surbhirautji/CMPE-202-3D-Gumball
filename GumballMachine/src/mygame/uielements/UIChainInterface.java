/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.uielements;

import mygame.coininhand.CoinInHandState;

/**
 *
 * @author Christopher
 */
public interface UIChainInterface {
    
    public void setNext(UIChainInterface next);
    public void consumeRequest(CoinInHandState state);
    
}
