package mygame.movement;

import com.jme3.math.Vector3f;

/**
 *
 * @author akshaybhasme
 */
public interface Player {
    
    public Vector3f moveAhead(Vector3f walkDirection, Vector3f camDirection);
    public Vector3f moveBack(Vector3f walkDirection, Vector3f camDirection);
    public Vector3f moveLeft(Vector3f walkDirection, Vector3f camDirection);
    public Vector3f moveRight(Vector3f walkDirection, Vector3f camDirection);
    public Vector3f jump(Vector3f walkDirection, Vector3f camDirection);
    
}
