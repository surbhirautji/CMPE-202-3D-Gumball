/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BussinessLogic;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import mygame.Main;

/**
 *
 * @author Vinay
 */
public class Gumball implements GumballPlan,AnimEventListener {
 private BulletAppState bulletAppState;
 Main app;
 AssetManager assetManager;
 Node rootNode;
 GumballMachine gumballMachine;
 BitmapFont guiFont;
 AudioNode audio;
 AudioNode audioCoin;
 public Gumball(Main app)
 {
     this.app=app;
     assetManager=app.getAssetManager();
     rootNode=app.getRootNode();
     guiFont=app.getBitmapFont();
     
 }
   /* public void initPlayerAndCollision() {
        bulletAppState = new BulletAppState();
        AppStateManager stateManager=app.getStateManager();
        stateManager.attach(bulletAppState);

        
        // We load the scene from the zip file and adjust its size.
        //assetManager.registerLocator("town.zip", ZipLocator.class);
        
        app.sceneModel = assetManager.loadModel("Scenes/GumballWorld.j3o");
        app.sceneModel.setLocalScale(2f);
        app.sceneModel.setLocalTranslation(-0.0f, -4.0f, -0.0f);
        // We set up collision detection for the scene by creating a
        // compound collision shape and a static RigidBodyControl with mass zero.
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) app.sceneModel);
        app.landscape = new RigidBodyControl(sceneShape, 0);
        app.sceneModel.addControl(app.landscape);

        // We set up collision detection for the player by creating
        // a capsule collision shape and a CharacterControl.
        // The CharacterControl offers extra settings for
        // size, stepheight, jumping, falling, and gravity.
        // We also put the player in its starting position.
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        app.playerMovement = new CharacterControl(capsuleShape, 0.05f);
        app.playerMovement.setJumpSpeed(20);
        app.playerMovement.setFallSpeed(30);
        app.playerMovement.setGravity(30);
        app.playerMovement.setPhysicsLocation(new Vector3f(0, 4, 0));

        // We attach the scene and the player to the rootnode and the physics space,
        // to make them appear in the game world.
        rootNode.attachChild(app.sceneModel);   
        bulletAppState.getPhysicsSpace().add(app.landscape);
        bulletAppState.getPhysicsSpace().add(app.playerMovement);
    }

    public void initButtonCrank() {
        AnimChannel channel;
        AnimControl control;
        Spatial player = assetManager.loadModel("Models/Button/buttonpush2.j3o");
        player.setLocalScale(0.1f);
        player.setLocalTranslation(-0.0f, -1.0f, -4.6f);
        player.setName("Crank");
        rootNode.attachChild(player);
        Spatial child = rootNode.getChild("Cylinder");
        control = child.getControl(AnimControl.class);
        control.addListener((AnimEventListener) this);
        channel = control.createChannel();
        channel.setAnim("push");

    }

    public void initVariables() {
       // gumballMachine = new GumballMachine(5);
        gumballMachine=GumballMachine.getInstance(5);
        //BuilderGumball gumballMachine=new BuilderGumball();
    
        app.hasNickel = false;
        app.hasDime = false;
        app.hasQuarter = false;
        app.Quarters = 10;
        app.Dime = 5;
        app.Nickel = 5;
    }*/

    public void initMachineScreenText() {

        Box box2 = new Box(6, 0.5f, 0.2f);
        Geometry red = new Geometry("Box", box2);
        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Black);
        red.setName("MachineScreen");
        red.setMaterial(mat2);
        red.setLocalScale(0.5f);
        red.setLocalTranslation(-0.0f, 2.0f, -4.7f);
        rootNode.attachChild(red);

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText inHand = new BitmapText(guiFont, false);
        inHand.setSize(guiFont.getCharSet().getRenderedSize());
        inHand.setText("Welcome");
        inHand.setName("MachineScreenText");
        inHand.setLocalScale(0.01f);
        inHand.setLocalTranslation(-2.8f, 2.0f, -4.5f);
        rootNode.attachChild(inHand);
    }

   /* public void initAudio() {
        
        audio = new AudioNode(assetManager, "Sounds/bounce.wav", false);
        audio.setPositional(false);
        audio.setLooping(false);
        audio.setVolume(10);
        rootNode.attachChild(audio);

        audioCoin = new AudioNode(assetManager, "Sounds/coin-insert.wav", false);
        audioCoin.setPositional(false);
        audioCoin.setLooping(false);
        audioCoin.setVolume(10);
        rootNode.attachChild(audioCoin);

    }*/

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
