package mygame.movement;

import com.jme3.math.Vector3f;

/**
 *
 * @author akshaybhasme
 */
public interface MovementCommand {
    
    public Vector3f execute(Vector3f walkDirection, Vector3f camDirection);
    
}
