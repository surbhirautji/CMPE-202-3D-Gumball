/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.movement;

import com.jme3.bullet.objects.PhysicsCharacter;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

/**
 *
 * @author Christopher
 */
public class MovementInvoker {
    
    
    private Camera cam;
    private Vector3f walkDirection = new Vector3f();
    private PhysicsCharacter playerMovement;
    
    public MovementInvoker(Camera cam, PhysicsCharacter playerMovement){
        this.cam = cam;
        this.playerMovement = playerMovement;
    }
    
    public void invoke(MovementCommand movementCommand,Vector3f camDir){
        
        walkDirection.set(0, 0, 0);
        
        walkDirection=movementCommand.execute( walkDirection, camDir);
        
        playerMovement.setWalkDirection(walkDirection);
        cam.setLocation(playerMovement.getPhysicsLocation());
    }
    
}
