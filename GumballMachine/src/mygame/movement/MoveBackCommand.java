/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.movement;

import com.jme3.math.Vector3f;

/**
 *
 * @author akshaybhasme
 */
public class MoveBackCommand implements MovementCommand{

    private Player player;
    
    public MoveBackCommand(Player player){
        this.player = player;
    }
    
    public Vector3f execute(Vector3f walkDirection, Vector3f camDirection) {
        return player.moveBack(walkDirection,camDirection);
    }
    
}
