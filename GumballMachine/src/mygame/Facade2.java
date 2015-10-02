/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import static mygame.Main.app;
import mygame.builder.BuilderEngg;
import mygame.builder.BuilderGumball;
import mygame.builder.OldBuilderGumball;

/**
 *
 * @author Christopher
 */
public class Facade2 implements FacadeInterface2{
    Main app;
    AppSettings settings;
    FlyByCamera flyCam;
    Node guiNode;
    Camera cam;
    BitmapFont guiFont;
    AppStateManager stateManager;
    InputManager inputManager;
    AssetManager assetManager;   
    Node rootNode;
    
    private RigidBodyControl gumballscape;
    private Spatial sceneModel;
    private RigidBodyControl landscape;
    private Spatial ballsceneModel;
    private BulletAppState bulletAppState;
    private BuilderGumball buildGumball;
            
    Facade2(Main apptemp){
         app= apptemp; 
         flyCam= app.getFlyByCamera();
         settings= app.getSettings();
         guiNode=app.getGuiNode();
         cam=app.getCamera();
         guiFont=app.getBitmapFont();
         stateManager=app.getStateManager();
         inputManager=app.getInputManager();
         assetManager=app.getAssetManager();
         rootNode=app.getRootNode();
         bulletAppState = app.getBulletAppState();
    }
    
    
    public void screenTextBuilder(){
        buildGumball=new OldBuilderGumball(app);
        BuilderEngg builderEngg=new BuilderEngg(buildGumball);
        builderEngg.makeBuilder();
    }
    

     public void setUpLight() {
        // We add light so we see the scene
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(dl);
    }
     
     public void initCrossHairs() {
        
        
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    public void initWall() {
        // Create a wall with a simple texture from test_data

        Box box = new Box(15, 10.5f, 1.0f);
        Spatial wall = new Geometry("Box", box);
        Material mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/wall2.jpg"));
        mat_brick.setColor("Color", ColorRGBA.Black);
        wall.setMaterial(mat_brick);
        
        wall.setLocalTranslation(0.0f, 0, 9);
        rootNode.attachChild(wall);
        CollisionShape sceneShape1 =
                CollisionShapeFactory.createMeshShape((Spatial) wall);
        gumballscape = new RigidBodyControl(sceneShape1, 0);
        wall.addControl(gumballscape);
        bulletAppState.getPhysicsSpace().addAll(wall);

        box = new Box(15, 10.5f, 1.0f);
        wall = new Geometry("Box", box);
        mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/wall2.jpg"));
        wall.setMaterial(mat_brick);
        wall.setLocalTranslation(0.0f, 0, -19);
        //rootNode.attachChild(wall);
        CollisionShape sceneShape2 =
                CollisionShapeFactory.createMeshShape((Spatial) wall);
        gumballscape = new RigidBodyControl(sceneShape2, 0);
        wall.addControl(gumballscape);
        //bulletAppState.getPhysicsSpace().addAll(wall);
        
        box = new Box(15, 10.5f, 1.0f);
        wall = new Geometry("Box", box);
        mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/wall2.jpg"));
        wall.setMaterial(mat_brick);
        wall.rotate(0, 190, 0);
        wall.setLocalTranslation(15, 0, -5);
        rootNode.attachChild(wall);
        CollisionShape sceneShape3 =
                CollisionShapeFactory.createMeshShape((Spatial) wall);
        gumballscape = new RigidBodyControl(sceneShape3, 0);
        wall.addControl(gumballscape);
        bulletAppState.getPhysicsSpace().addAll(wall);
        
        box = new Box(15, 10.5f, 1.0f);
        wall = new Geometry("Box", box);
        mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/wall2.jpg"));
        wall.setMaterial(mat_brick);
        wall.rotate(0, 190, 0);
        wall.setLocalTranslation(-15, 0, -5);
        rootNode.attachChild(wall);
        CollisionShape sceneShape4 =
                CollisionShapeFactory.createMeshShape((Spatial) wall);
        gumballscape = new RigidBodyControl(sceneShape4, 0);
        wall.addControl(gumballscape);
        bulletAppState.getPhysicsSpace().addAll(wall);
    }

    public void makeFloor() {
        Box box = new Box(15, .2f, 15);
        Geometry floor = new Geometry("the Floor", box);
        floor.setLocalTranslation(0, -3.5f, -5);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat1.setColor("Color", ColorRGBA.Red);
        mat1.setTexture("ColorMap", assetManager.loadTexture("Textures/floor.jpg"));
        floor.setMaterial(mat1);
        
        CollisionShape sceneShape2 =
                CollisionShapeFactory.createMeshShape((Spatial) floor);
        gumballscape = new RigidBodyControl(sceneShape2, 0);
        floor.addControl(gumballscape);
        bulletAppState.getPhysicsSpace().addAll(floor);
        
        rootNode.attachChild(floor);
    }
    
}
