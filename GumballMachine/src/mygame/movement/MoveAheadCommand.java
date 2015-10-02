package mygame.movement;

import com.jme3.math.Vector3f;

/**
 *
 * @author akshaybhasme
 */
public class MoveAheadCommand implements MovementCommand{
    
    private Player player;
    
    public MoveAheadCommand(Player player){
        this.player = player;
    }

    public Vector3f execute(Vector3f walkDirection, Vector3f camDirection) {
        return player.moveAhead(walkDirection,camDirection);
    }
}
