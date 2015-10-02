/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author Christopher
 */
public interface FacadeInterface {
        public void initMark();
        //public void initAudio();
        public void initKeys();
        public void initVariables();
        
        public void initText();
        public void initShootables();
        
        //initButtonCrank();
        //public void initMachineScreenText();
        
        
        public void initPlayerAndCollision();
        public void initMaterials();
        public void initGumballs(); 
        
        //makeCeiling();
        public void initChain();
        public void initNewWorld();
        public void splashScreen();
        public void simpleUpdate(float ftp);
}
