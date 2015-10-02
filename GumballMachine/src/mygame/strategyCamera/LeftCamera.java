/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.strategyCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import mygame.Main;
/**
 *
 * @author Christopher
 */
public class LeftCamera implements CameraInterface{
    
    Camera cam;
    public LeftCamera(Camera cam){
        this.cam=cam;
    }

    public Vector3f setCamera() {
        return cam.getLeft().multLocal(0.6f);
    }
    
}
