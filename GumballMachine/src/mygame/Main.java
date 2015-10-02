package mygame;


import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapFont;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import mygame.builder.BuilderGumball;
import mygame.builder.OldBuilderGumball;


public class Main extends SimpleApplication {
    
    private BulletAppState bulletAppState;
    FacadeMainInterface FE;
    
    static Main app = new Main();
    public static void main(String[] args) {
        app.start();  
        
    }

    @Override
    public void simpleInitApp() {
        bulletAppState=new BulletAppState();
        
        FE=new FacadeEngineer(app);
        FE.runInterface1();
        FE.runInterface2();
     
        setDisplayStatView(false);
        
    }

    AppSettings getSettings() {
        return app.settings;
    }
    
    public BitmapFont getBitmapFont() {
        return app.guiFont;
    }
     
     BulletAppState getBulletAppState() {
        return bulletAppState;
    }
     
     
    @Override
    public void simpleUpdate(float tpf) {
        FE.runInterface3(tpf);
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

}