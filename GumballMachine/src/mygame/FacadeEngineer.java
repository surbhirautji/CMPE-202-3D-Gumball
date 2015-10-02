/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import static mygame.Main.app;

/**
 *
 * @author Christopher
 */
public class FacadeEngineer implements FacadeMainInterface{
    FacadeInterface FI;
    FacadeInterface2 FI2;
    
    FacadeEngineer(Main app){
        FI=new Facade(app);
        FI2=new Facade2(app);
    }
            
            
    public void runInterface1(){
              
        
        FI.initKeys();
        FI.initVariables();
        
        FI.initText();
        FI.initShootables();
        
        //initButtonCrank();
        //`FI.initMachineScreenText();
        
        FI.initChain();
        FI.initPlayerAndCollision();
        FI.initMaterials();
        FI.initGumballs(); 
        
        //makeCeiling();
        FI.initMark();
       // FI.initAudio();
        FI.initNewWorld();
        FI.splashScreen();
    }
    
    public void runInterface2(){
        
        FI2.initCrossHairs();
        
        FI2.makeFloor();
        FI2.setUpLight();
        FI2.initWall();
        FI2.screenTextBuilder();
    }
    
    public void runInterface3(float tpf){
        FI.simpleUpdate(tpf);
    }
}
