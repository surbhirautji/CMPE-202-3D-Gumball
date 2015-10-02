/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import BussinessLogic.GumballMachine;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsCharacter;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.MaterialList;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.plugins.ogre.OgreMeshKey;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import mygame.coininhand.DimeInHandState;
import mygame.coininhand.NickelInHandState;
import mygame.coininhand.QuarterInHand;
import mygame.movement.GumballPlayer;
import mygame.movement.MoveAheadCommand;
import mygame.movement.MoveBackCommand;
import mygame.movement.MoveLeftCommand;
import mygame.movement.MoveRightCommand;
import mygame.movement.MovementInvoker;
import mygame.strategyCamera.CameraDirection;
import mygame.strategyCamera.CameraInterface;
import mygame.strategyCamera.LeftCamera;
import mygame.uielements.DimeNode;
import mygame.uielements.NickelNode;
import mygame.uielements.QuarterNode;
import mygame.uielements.UIChainInterface;

/**
 *
 * @author Christopher
 */
public class Facade implements AnimEventListener, FacadeInterface {

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
    AnimChannel channel;
    AnimControl control1;
    public Node lever;

    Facade(Main apptemp) {

        app = apptemp;
        flyCam = app.getFlyByCamera();
        settings = app.getSettings();
        guiNode = app.getGuiNode();
        cam = app.getCamera();
        guiFont = app.getBitmapFont();
        stateManager = app.getStateManager();
        inputManager = app.getInputManager();
        assetManager = app.getAssetManager();
        rootNode = app.getRootNode();
        bulletAppState = app.getBulletAppState();
        
        
        
        
    }
    private BulletAppState bulletAppState;
    private Spatial gumballWorld;
    private Node player;
    private Node shootables;
    private Geometry mark;
    private int GumballCount;
    private GumballMachine gumballMachine;
    private Boolean hasNickel;
    private Boolean hasDime;
    private Boolean hasQuarter;
    public int Quarters;
    public int Dime;
    public int Nickel;
    //Movement
    private RigidBodyControl gumballscape;
    private PhysicsCharacter playerMovement;
    Vector3f camUse;
    CameraInterface camInt;  
    
    private boolean left = false, right = false, up = false, down = false;
    
    //MovementCommand
    MovementInvoker movementInvoker;
    MoveAheadCommand moveAheadCommand;
    MoveLeftCommand moveLeftCommand;
    MoveRightCommand moveRightCommand;
    MoveBackCommand moveBackCommand;
    GumballPlayer gumballPlayer;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    
    //Sound
    private RigidBodyControl ball_phy;
    private static final Sphere sphere;
    private Material stone_mat;
    private Spatial machine;
    private Picture splashScreen;

    //Chain
    UIChainInterface uIQuarterNode;
    UIChainInterface uINickelNode;
    UIChainInterface uIDimeNode;
        
    static {
        sphere = new Sphere(32, 32, 0.4f, true, false);
        sphere.setTextureMode(Sphere.TextureMode.Projected);
    }
    private Node gameLevel;
    private static boolean useHttp = false;

    public void initNewWorld() {

        flyCam.setMoveSpeed(5.0f);
        MaterialList matList = (MaterialList) assetManager.loadAsset("Materials/Scene.material");
        OgreMeshKey key = new OgreMeshKey("Materials/main.meshxml", matList);
        gameLevel = (Node) assetManager.loadAsset(key);
        gameLevel.setLocalScale(0.1f);
        gameLevel.setLocalTranslation(-32.0f, -7.0f, -50.0f);
        //gameLevel.rotate(0,180,0);
        // add a physics control, it will generate a MeshCollisionShape based on the gameLevel
        gameLevel.addControl(new RigidBodyControl(0));

        rootNode.attachChild(gameLevel);

        bulletAppState.getPhysicsSpace().addAll(gameLevel);

        AmbientLight light = new AmbientLight();
        light.setColor(ColorRGBA.White.mult(2));
        rootNode.addLight(light);
        
        
    }
    
    public void initChain(){
         uIQuarterNode=new QuarterNode(app);
         uINickelNode=new NickelNode(app);
         uIDimeNode=new DimeNode(app);
          
        uIQuarterNode.setNext(uINickelNode);
        uINickelNode.setNext(uIDimeNode); 
    }

    public void splashScreen() {

        splashScreen = new Picture("Splash Screen");
        splashScreen.setImage(assetManager, "Textures/gum.jpeg", true);
        splashScreen.setWidth(640);
        splashScreen.setHeight(480);
        splashScreen.setPosition((settings.getWidth() - 640) / 2, (settings.getHeight() - 480) / 2);
        guiNode.attachChild(splashScreen);
        /*
         enqueue(new Callable<Void>() {
         public void run() {

         splashScreen.removeFromParent();
         }

         public Void call() throws Exception {
         throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         }

         });
         * */
    }

    public void initMaterials() {
        stone_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey key2 = new TextureKey("Textures/Terrain/Rock/Rock.PNG");
        key2.setGenerateMips(true);
        Texture tex2 = assetManager.loadTexture(key2);
        stone_mat.setTexture("ColorMap", tex2);
    }

    /**
     * This loop builds a wall out of individual bricks.
     */
    public void initGumballs() {
        for (int i = 0; i < GumballCount; i++) {
            makeGumBall();
        }
    }

    public void makeGumBall() {
        /**
         * Create a cannon ball geometry and attach to scene graph.
         */
        Geometry ball_geo = new Geometry("cannon ball", sphere);

        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.randomColor());
        //ball_geo.setMaterial(stone_mat);
        ball_geo.setMaterial(mat2);

        rootNode.attachChild(ball_geo);
        /**
         * Position the cannon ball
         */
       // ball_geo.setLocalTranslation(-0.0f, 3.0f, -5.0f);
        ball_geo.setLocalTranslation(-0.0f, 2.1f, -5.0f);
        ball_geo.setLocalScale(1f);
        /**
         * Make the ball physcial with a mass > 0.0f
         */
        ball_phy = new RigidBodyControl(5f);
        /**
         * Add physical ball to physics space.
         */
        ball_geo.addControl(ball_phy);
        bulletAppState.getPhysicsSpace().add(ball_phy);
        bulletAppState.getPhysicsSpace().addAll(machine);
        /**
         * Accelerate the physcial ball to shoot it.
         */
        ball_phy.setLinearVelocity(cam.getDirection().mult(25));
    }

    public void initPlayerAndCollision() {

        stateManager.attach(bulletAppState);

        // We load the scene from the zip file and adjust its size.
        //assetManager.registerLocator("town.zip", ZipLocator.class);
        //  sceneModel = assetManager.loadModel("Scenes/GumballWorld.j3o");
        //  sceneModel.setLocalScale(2f);
        //  sceneModel.setLocalTranslation(-0.0f, -4.0f, -0.0f);
        // We set up collision detection for the scene by creating a
        // compound collision shape and a static RigidBodyControl with mass zero.
        //  CollisionShape sceneShape =
        //          CollisionShapeFactory.createMeshShape((Node) sceneModel);
        //  landscape = new RigidBodyControl(sceneShape, 0);
        //  sceneModel.addControl(landscape);

        // We set up collision detection for the player by creating
        // a capsule collision shape and a CharacterControl.
        // The CharacterControl offers extra settings for
        // size, stepheight, jumping, falling, and gravity.
        // We also put the player in its starting position.
        //CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        //playerMovement = new CharacterControl(capsuleShape, 0.05f);
        playerMovement = new PhysicsCharacter(new SphereCollisionShape(5), .01f);
        playerMovement.setJumpSpeed(20);
        playerMovement.setFallSpeed(30);
        playerMovement.setGravity(30);
        //  playerMovement.setPhysicsLocation(new Vector3f(0, 4, 0));

        //(-32.0f, -7.0f, -50.0f);
        playerMovement.setPhysicsLocation(new Vector3f(28, 24, -110));
        movementInvoker =new MovementInvoker(cam, playerMovement);

        // We attach the scene and the player to the rootnode and the physics space,
        // to make them appear in the game world.
        //    rootNode.attachChild(sceneModel);   
        // bulletAppState.getPhysicsSpace().add(landscape);
        bulletAppState.getPhysicsSpace().addAll(machine);
        bulletAppState.getPhysicsSpace().add(playerMovement);
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
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("push");

    }

    public void initVariables() {
        GumballCount = 30;
        hasNickel = false;
        hasDime = false;
        hasQuarter = false;
        Quarters = 10;
        Dime = 5;
        Nickel = 5;
        gumballMachine = new GumballMachine(GumballCount, rootNode, assetManager);
        
        gumballPlayer=new GumballPlayer(inputManager,gumballMachine);
        
        moveAheadCommand=new MoveAheadCommand(gumballPlayer);
        moveLeftCommand=new MoveLeftCommand(gumballPlayer);
        moveRightCommand=new MoveRightCommand(gumballPlayer);
        moveBackCommand=new MoveBackCommand(gumballPlayer);

    }
/*
    public void initMachineScreenText() {

        Box box2 = new Box(6, 0.5f, 0.2f);
        Geometry red = new Geometry("Box", box2);
        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Black);
        red.setName("MachineScreen");
        red.setMaterial(mat2);
        red.setLocalScale(1.5f);
        red.setLocalTranslation(-0.0f, 5.0f, -8.7f);
        rootNode.attachChild(red);

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText inHand = new BitmapText(guiFont, false);
        inHand.setSize(guiFont.getCharSet().getRenderedSize());
        inHand.setText("Welcome");
        inHand.setName("MachineScreenText");
        inHand.setLocalScale(0.03f);
        inHand.setLocalTranslation(6.0f, 5.2f, -9.4f);
        inHand.rotate(0, 3.1f, 0);
        rootNode.attachChild(inHand);
    }
*/
    public void setMachineScreenText(String msg) {
        BitmapText MachineScreen = (BitmapText) rootNode.getChild("MachineScreenText");
        MachineScreen.setText(msg);
    }

    public void initBallBounce() {
        rootNode.detachChildNamed("Ball");
        AnimChannel channel;
        AnimControl control;
        Node ball = (Node) assetManager.loadModel("Models/Gumball/redgumball.j3o");
        ball.setLocalScale(0.8f);
        ball.setLocalTranslation(-0.0f, -5.0f, -6.0f);
        ball.setName("Ball");
        rootNode.attachChild(ball);
        Spatial child = ball.getChild("Sphere");
        control = child.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Bounce");

    }

    public void initKeys() {
        inputManager.addMapping("Shoot",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click

        inputManager.addMapping("Quarter", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Nickel", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("Dime", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_4));
        inputManager.addListener(actionListener, "Quarter", "Nickel", "Dime", "Reset");
        inputManager.addListener(actionListener, "Shoot");

        //Movement listeners
        inputManager.addListener(actionListener, "Left");
        inputManager.addListener(actionListener, "Right");
        inputManager.addListener(actionListener, "Up");
        inputManager.addListener(actionListener, "Down");
        inputManager.addListener(actionListener, "Jump");
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
 
        // inputManager.addMapping("Bounce", new KeyTrigger(KeyInput.KEY_SPACE));
        // inputManager.addListener(actionListener, "Bounce");
    }
    public ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            //  if (name.equals("Bounce") && !keyPressed) {
            //    if (!channel.getAnimationName().equals("Bounce")) {
            //      channel.setAnim("Bounce", 0.50f);
            //      channel.setLoopMode(LoopMode.DontLoop);
            //    }
            //  }

            //Movement
            if (name.equals("Left")) {
                left = keyPressed;
            } else if (name.equals("Right")) {
                right = keyPressed;
            } else if (name.equals("Up")) {
                up = keyPressed;
            } else if (name.equals("Down")) {
                down = keyPressed;
            } else if (name.equals("Jump")) {
                if (keyPressed) {
                    playerMovement.jump();
                }
            }
            ///////


            if (name.equals("Nickel") && !keyPressed) {
                /*hasNickel = true;
                hasDime = false;
                hasQuarter = false;*/
                gumballPlayer.nickelInHand();
                
                setScreenText("Nickel in hand");
            }

            if (name.equals("Dime") && !keyPressed) {
                /*hasNickel = false;
                hasDime = true;
                hasQuarter = false;*/
                gumballPlayer.dimeInHand();
                setScreenText("Dime in hand");
            }

            if (name.equals("Quarter") && !keyPressed) {
                /*hasNickel = false;
                hasDime = false;
                hasQuarter = true;*/
                gumballPlayer.quarterInHand();
                setScreenText("Quarter in hand");
            }

            if (name.equals("Reset") && !keyPressed) {
                /*hasQuarter = false;
                hasDime = false;
                hasNickel = false;*/
                gumballPlayer.nothingInHand();
                setScreenText("Nothing in hand");
            }

            if (name.equals("Shoot") && !keyPressed) {
                splashScreen.removeFromParent();
                if (CollectionDetection()) {
                 //   makeGumBall();
                    
                        String msg = gumballPlayer.insertCoin();
                        setCoinCount();
                        setMachineScreenText(msg);
                        if (msg.contains("A gumball comes rolling out the slot...")) {
                                     
                            channel.setAnim("CylinderAction");
                            channel.setSpeed(10.0f);
                            if (channel.getAnimationName().equals("CylinderAction")) 
                            channel.setLoopMode(LoopMode.DontLoop);
                            initBallBounce();

                        }
                }
            }
        }
    };

    /*public String tryInsert() {
        /*if (hasNickel) {
            return gumballMachine.insertNickel();
        } else if (hasDime) {
            return gumballMachine.insertDime();
        } else if (hasQuarter) {
            return gumballMachine.insertQuarter();
        } else {
            return "";
        }
        return gumballPlayer.insertCoin();
    }*/
    
  
    
/*
    public void initAudio() {
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

    }
*/
    public void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("Boom!", sphere);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mat1);
    }

    public void setCoinCount() {
        
        uIQuarterNode.consumeRequest(gumballPlayer.getCoinInHand());

        //return false;
    }

    public boolean CollectionDetection() {

        CollisionResults results = new CollisionResults();

        // 5. Use the results (we mark the hit object)
        if (results.size() > 0) {
            // The closest collision point is what was truly hit:
            CollisionResult closest = results.getClosestCollision();
            // Let's interact - we mark the hit with a red dot.
            mark.setLocalTranslation(closest.getContactPoint());
            rootNode.attachChild(mark);
        } else {
            // No hits? Then remove the red mark.
            rootNode.detachChild(mark);
        }

        // 2. Aim the ray from cam loc to cam direction.

        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        // 3. Collect intersections between Ray and Shootables in results list.
        shootables.collideWith(ray, results);
        // 4. Print the results
        System.out.println("----- Collisions? " + results.size() + "-----");
        boolean hitEle = false;
        for (int i = 0; i < results.size(); i++) {
            // For each hit, we know distance, impact point, name of geometry.
            float dist = results.getCollision(i).getDistance();
            Vector3f pt = results.getCollision(i).getContactPoint();
            String hit = results.getCollision(i).getGeometry().getName();
            System.out.println("* Collision #" + i);
            System.out.println("  You shot " + hit + " at " + pt + ", " + dist + " wu away.");
            if (hit.contains("Sphere1")) {
                // System.out.println("true");
                hitEle = true;
            } else {
                // System.out.println("false");
                hitEle = false;
            }
        }
        return hitEle;
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("Bounce")) {
            // channel.setAnim("Bounce", 0.50f);
            // channel.setLoopMode(LoopMode.DontLoop);
            // channel.setSpeed(1f);

            rootNode.detachChildNamed("Ball");
        }

        if (animName.equals("push")) {
            channel.setLoopMode(LoopMode.DontLoop);
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        // unused
    }

    public void initShootables() {
        shootables = new Node("Shootables");
        rootNode.attachChild(shootables);
        Spatial sp = makeCharacter();
        machine = sp;
        shootables.attachChild(sp);
    }

    public void makeCeiling() {
        Box box = new Box(15, .2f, 15);
        Geometry floor = new Geometry("the Floor", box);
        floor.setLocalTranslation(0, 4.0f, -5);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Brown);
        floor.setMaterial(mat1);
        rootNode.attachChild(floor);
    }

    protected Spatial makeCharacter() {
        Spatial golem = assetManager.loadModel("Models/GumballMachineModel/gumballmachine (1).j3o");
        golem.scale(1.5f);
        golem.setLocalTranslation(-0.0f, -3.0f, -5.6f);
        golem.setName("Oto");
        
        lever = (Node) assetManager.loadModel("Models/GumballMachineModel/pulllever2.j3o");
        lever.scale(01.6f);
        lever.setLocalTranslation(-2.8f, 4.2f, -5.6f);
        lever.rotate(0.0f, -0.2f, 3.0f);
        lever.setName("lever");
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");  // ... specify .j3md file to use (unshaded).
        mat.setColor("Color", ColorRGBA.Gray); 
        lever.setMaterial(mat);
        rootNode.attachChild(lever);
          Spatial child;
        child = lever.getChild("Cylinder");
        //Spatial child;
        //child = child1.getChild("Cylinder");
        System.out.println(child);
        control1 = child.getControl(AnimControl.class);
        control1.addListener(this);
        channel = control1.createChannel();
        //System.out.println(channel.getAnimationName());
         //if (channel.getAnimationName().equals("CylinderAction")) 
        if(lever.getName().equals("lever"))
         {
     
        
        channel.setAnim("CylinderAction");
        channel.setSpeed(10.0f);
        System.out.println("hello");
          channel.setLoopMode(LoopMode.DontLoop);
         }
       // channel.setAnim("CylinderAction");
        // We must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        golem.addLight(sun);
        CollisionShape golemsh =
                CollisionShapeFactory.createMeshShape((Spatial) golem);
        gumballscape = new RigidBodyControl(golemsh, 0);
        golem.addControl(gumballscape);

        return golem;
    }
    
    public void setCamType(CameraInterface cameraInterface){
        camInt=cameraInterface;
    }

    public void simpleUpdate(float tpf) {
        
        if (left) {  
            setCamType(new LeftCamera(cam)); 
            movementInvoker.invoke(moveLeftCommand,camInt.setCamera());
        }
        if (right) {
            setCamType(new LeftCamera(cam)); 
            movementInvoker.invoke(moveRightCommand,camInt.setCamera());
        }
        if (up) {
            setCamType(new CameraDirection(cam));  
            movementInvoker.invoke(moveAheadCommand,camInt.setCamera());
        }
        if (down) {
            setCamType(new CameraDirection(cam)); 
            movementInvoker.invoke(moveBackCommand,camInt.setCamera());
        } 
  
    }

    public void setScreenText(String msg) {
        BitmapText inhand = (BitmapText) guiNode.getChild("InHand");
        inhand.setText(msg);
    }

    public void initText() {
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText inHand = new BitmapText(guiFont, false);
        inHand.setSize(guiFont.getCharSet().getRenderedSize() + 20);
        inHand.setColor(ColorRGBA.Black);
        inHand.setText("Nothing in hand");
        inHand.setName("InHand");
        inHand.setLocalTranslation(300, inHand.getLineHeight(), 0);
        guiNode.attachChild(inHand);

        BitmapText helloText;

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Coins in hand:");
        helloText.setColor(ColorRGBA.Blue);
        helloText.setLocalTranslation(this.settings.getWidth() - 150, this.settings.getHeight() - 50, 0);
        guiNode.attachChild(helloText);

        Sphere sphere = new Sphere(50, 50, 50);
        Geometry coin = new Geometry("Quarters", sphere);
        coin.setLocalTranslation(this.settings.getWidth() - 100, this.settings.getHeight() - 150, 0);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        coin.setMaterial(mat1);
        guiNode.attachChild(coin);

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize() + 10);
        helloText.setText("" + Quarters);
        helloText.setColor(ColorRGBA.White);
        helloText.setName("countQuarters");
        helloText.setLocalTranslation(this.settings.getWidth() - 100, this.settings.getHeight() - 150, 0);
        guiNode.attachChild(helloText);

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Quarters");
        helloText.setColor(ColorRGBA.Black);
        helloText.setLocalTranslation(this.settings.getWidth() - 135, this.settings.getHeight() - 200, 0);
        guiNode.attachChild(helloText);

        sphere = new Sphere(50, 50, 50);
        coin = new Geometry("Dime", sphere);
        coin.setLocalTranslation(this.settings.getWidth() - 100, this.settings.getHeight() - 275, 0);
        mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Brown);
        coin.setMaterial(mat1);
        guiNode.attachChild(coin);

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize() + 10);
        helloText.setText("" + Dime);
        helloText.setName("countDime");
        helloText.setColor(ColorRGBA.White);

        helloText.setLocalTranslation(this.settings.getWidth() - 100, this.settings.getHeight() - 275, 0);
        guiNode.attachChild(helloText);


        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Dime");
        helloText.setColor(ColorRGBA.Black);
        helloText.setLocalTranslation(this.settings.getWidth() - 135, this.settings.getHeight() - 325, 0);
        guiNode.attachChild(helloText);

        sphere = new Sphere(50, 50, 50);
        coin = new Geometry("Nickel", sphere);
        coin.setLocalTranslation(this.settings.getWidth() - 100, this.settings.getHeight() - 400, 0);
        mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Green);
        coin.setMaterial(mat1);
        guiNode.attachChild(coin);

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize() + 10);
        helloText.setText("" + Nickel);
        helloText.setName("countNickel");
        helloText.setColor(ColorRGBA.White);
        helloText.setLocalTranslation(this.settings.getWidth() - 100, this.settings.getHeight() - 400, 0);
        guiNode.attachChild(helloText);

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        helloText = new BitmapText(guiFont, false);
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
        helloText.setText("Nickel");
        helloText.setColor(ColorRGBA.Black);
        helloText.setLocalTranslation(this.settings.getWidth() - 135, this.settings.getHeight() - 450, 0);
        guiNode.attachChild(helloText);
    }
}
