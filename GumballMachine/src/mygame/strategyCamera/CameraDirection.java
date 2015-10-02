/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.strategyCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
/**
 *
 * @author Christopher
 */
public class CameraDirection implements CameraInterface{

    Camera cam;
    public CameraDirection(Camera cam){
        this.cam=cam;
    }

    public Vector3f setCamera() {
        return cam.getDirection().multLocal(0.4f);
    }
    
}