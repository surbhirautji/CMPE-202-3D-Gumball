package mygame;

import BussinessLogic.BuilderEngg;
import BussinessLogic.BuilderGumball;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import BussinessLogic.GumballMachine;
import BussinessLogic.OldBuilderGumball;
import com.jme3.animation.LoopMode;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapFont;
import com.jme3.light.AmbientLight;
import com.jme3.math.Matrix3f;

public class Main extends SimpleApplication implements AnimEventListener {
//extends SimpleApplication
    private Spatial gumballWorld;
    private Node player;
    private Node shootables;
    private Geometry mark;
    AnimChannel channel;
    AnimControl control1;
    
    
    private GumballMachine gumballMachine;
    
    public Boolean hasNickel;
    public Boolean hasDime;
    public Boolean hasQuarter;
    public int Quarters;
    public int Dime;
    public int Nickel;
    //Movement
    public Spatial sceneModel;
    private BulletAppState bulletAppState;
    public RigidBodyControl landscape;
    public CharacterControl playerMovement;
    private Vector3f walkDirection = new Vector3f();
    private boolean left = false, right = false, up = false, down = false;
    private Vector3f camDir = new Vector3f();
    private Vector3f camLeft = new Vector3f();
    //Sound
    private AudioNode audio;
    private AudioNode audioCoin;
    static  Main app;

    public static void main(String[] args) {
        app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        flyCam.setMoveSpeed(5.0f);
        initCrossHairs();
        initKeys();
        initVariables();
        BuilderGumball oldgumball = new OldBuilderGumball(app);
	
	BuilderEngg gumballEngineer = new BuilderEngg(oldgumball);
	
	gumballEngineer.makeMachineText();
        initPlayerAndCollision();
       
        makeFloor();
        makeCeiling();
        initWall();
        initText();
        initShootables();
        initMark();
        initAudio();
       // initButtonCrank();
         /*BuilderGumball oldStyleRobot = new OldBuilderGumball(app);
	
	BuilderEngg robotEngineer = new BuilderEngg(oldStyleRobot);
	
	robotEngineer.makeRobot();*/
        //initMachineScreenText();
        setUpLight();

    }
    
    public BitmapFont getBitmapFont()
    {
        return guiFont;
    }

    private void setUpLight() {
        // We add light so we see the scene
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(1.3f));
        rootNode.addLight(al);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(dl);
    }
    
    

    private void initPlayerAndCollision() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        // We load the scene from the zip file and adjust its size.
        //assetManager.registerLocator("town.zip", ZipLocator.class);
        sceneModel = assetManager.loadModel("Scenes/GumballWorld.j3o");
        sceneModel.setLocalScale(2f);
        sceneModel.setLocalTranslation(-0.0f, -4.0f, -0.0f);
        // We set up collision detection for the scene by creating a
        // compound collision shape and a static RigidBodyControl with mass zero.
        CollisionShape sceneShape =
                CollisionShapeFactory.createMeshShape((Node) sceneModel);
        landscape = new RigidBodyControl(sceneShape, 0);
        sceneModel.addControl(landscape);

        // We set up collision detection for the player by creating
        // a capsule collision shape and a CharacterControl.
        // The CharacterControl offers extra settings for
        // size, stepheight, jumping, falling, and gravity.
        // We also put the player in its starting position.
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        playerMovement = new CharacterControl(capsuleShape, 0.05f);
        playerMovement.setJumpSpeed(20);
        playerMovement.setFallSpeed(30);
        playerMovement.setGravity(30);
        playerMovement.setPhysicsLocation(new Vector3f(0, 4, 0));

        // We attach the scene and the player to the rootnode and the physics space,
        // to make them appear in the game world.
        rootNode.attachChild(sceneModel);   
        bulletAppState.getPhysicsSpace().add(landscape);
        bulletAppState.getPhysicsSpace().add(playerMovement);
    }
/*
    private void initButtonCrank() {
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

    }*/

    private void initVariables() {
       // gumballMachine = new GumballMachine(5);
        gumballMachine=GumballMachine.getInstance(5);
        //BuilderGumball gumballMachine=new BuilderGumball();
    
        hasNickel = false;
        hasDime = false;
        hasQuarter = false;
        Quarters = 10;
        Dime = 5;
        Nickel = 5;
    }
/*
    private void initMachineScreenText() {

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
*/
    private void initAudio() {
        // gun shot sound is to be triggered by a mouse click. 
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

    private void setMachineScreenText(String msg) {
        BitmapText MachineScreen = (BitmapText) rootNode.getChild("MachineScreenText");
        MachineScreen.setText(msg);
    }

    public void initBallBounce() {
        rootNode.detachChildNamed("Ball");
        AnimChannel channel;
        AnimControl control;
        Node ball = (Node) assetManager.loadModel("Models/Gumball/redgumball.j3o");
        ball.setLocalScale(0.3f);
        ball.setLocalTranslation(-0.0f, -0.0f, -6.0f);
        ball.setName("Ball");
        rootNode.attachChild(ball);
        Spatial child = ball.getChild("Sphere");
        control = child.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Bounce");

    }

    private void initKeys() {
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
    
    private ActionListener actionListener = new ActionListener() {
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
                hasNickel = true;
                hasDime = false;
                hasQuarter = false;
                setScreenText("Nickel in hand");
            }

            if (name.equals("Dime") && !keyPressed) {
                hasNickel = false;
                hasDime = true;
                hasQuarter = false;
                setScreenText("Dime in hand");
            }

            if (name.equals("Quarter") && !keyPressed) {
                hasNickel = false;
                hasDime = false;
                hasQuarter = true;
                setScreenText("Quarter in hand");
            }

            if (name.equals("Reset") && !keyPressed) {
                hasQuarter = false;
                hasDime = false;
                hasNickel = false;
                setScreenText("Nothing in hand");
            }

            if (name.equals("Shoot") && !keyPressed) {
                if (CollectionDetection()) {
                    if (setCoinCount()) {
                        setMachineScreenText(tryInsert());
                    } else {

//                        System.out.println(gumballMachine.turnCrank());
                        String msg = gumballMachine.turnCrank();
                        
                        channel.setAnim("CylinderAction");
                        channel.setSpeed(10.0f);
                        if (channel.getAnimationName().equals("CylinderAction")) 
                         channel.setLoopMode(LoopMode.DontLoop);   
                        audio.playInstance();
                        setMachineScreenText(msg);
                        if (msg.contains("A gumball comes rolling out the slot...")) {
                            initBallBounce();
                            

                        }
                    }
                }
            }
        }
    };

    private String tryInsert() {
        if (hasNickel) {
            audioCoin.playInstance();
            return gumballMachine.insertNickel();
        } else if (hasDime) {
            audioCoin.playInstance();
            return gumballMachine.insertDime();
        } else if (hasQuarter) {
            audioCoin.playInstance();
            return gumballMachine.insertQuarter();
        } else {
            return "";
        }
    }

    private boolean setCoinCount() {
        if (hasQuarter) {
            BitmapText helloText = (BitmapText) guiNode.getChild("countQuarters");
            int count = Integer.parseInt(helloText.getText());
            if (count == 0) {
                setScreenText("Nothing in hand");
                return false;
            } else {
                helloText.setText("" + (count - 1));
                return true;
            }
        } else if (hasNickel) {
            BitmapText helloText = (BitmapText) guiNode.getChild("countNickel");
            int count = Integer.parseInt(helloText.getText());
            if (count == 0) {
                setScreenText("Nothing in hand");
                return false;
            } else {
                helloText.setText("" + (count - 1));
                return true;
            }
        } else if (hasDime) {
            BitmapText helloText = (BitmapText) guiNode.getChild("countDime");
            int count = Integer.parseInt(helloText.getText());
            if (count == 0) {
                setScreenText("Nothing in hand");
                return false;
            } else {
                helloText.setText("" + (count - 1));
                return true;
            }
        }
        return false;
    }

    private boolean CollectionDetection() {

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

    private void initShootables() {
        shootables = new Node("Shootables");
        rootNode.attachChild(shootables);
        Node sp = makeCharacter();
        sp.setLocalTranslation(-0.0f, -3.0f, -5.6f);
        shootables.attachChild(sp);
        
    }

    protected void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("Boom!", sphere);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mat1);
    }

    protected void makeFloor() {
        Box box = new Box(15, .2f, 15);
        Geometry floor = new Geometry("the Floor", box);
        floor.setLocalTranslation(0, -3.5f, -5);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        //mat1.setColor("Color", ColorRGBA.Red);
        mat1.setTexture("ColorMap", assetManager.loadTexture("Textures/floor.jpg"));
        floor.setMaterial(mat1);
        rootNode.attachChild(floor);
    }

    protected void makeCeiling() {
        Box box = new Box(15, .2f, 15);
        Geometry floor = new Geometry("the Floor", box);
        floor.setLocalTranslation(0, 4.0f, -5);
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.White);
        floor.setMaterial(mat1);
        rootNode.attachChild(floor);
    }

    protected void initCrossHairs() {
        setDisplayStatView(false);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(guiFont, false);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs
        ch.setLocalTranslation( // center
                settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
        guiNode.attachChild(ch);
    }

    protected Node makeCharacter() {
        Node golem = (Node) assetManager.loadModel("Models/GumballMachineModel/gumballmachine.j3o");
        golem.scale(1.0f);
        golem.setLocalTranslation(-0.0f, -3.0f, -5.6f);
        golem.rotate(0.0f, -1.6f, 0.0f);
        golem.setName("Oto");
        Node lever = (Node) assetManager.loadModel("Models/GumballMachineModel/pulllever2.j3o");
        lever.scale(01.0f);
        lever.setLocalTranslation(2.2f, 2.2f, -5.6f);
        lever.rotate(0.0f, -0.2f, 3.1f);
        lever.setName("lever");
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");  // ... specify .j3md file to use (unshaded).
        mat.setColor("Color", ColorRGBA.Gray); 
        lever.setMaterial(mat);
        rootNode.attachChild(lever);
        
       //   AnimChannel channel;
       // AnimControl control1;
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
         
         
        //channel.setAnim("CylinderAction");
        // We must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        golem.addLight(sun);
        return golem;
    }

    @Override
    public void simpleUpdate(float tpf) {
        camDir.set(cam.getDirection()).multLocal(0.6f);
        camLeft.set(cam.getLeft()).multLocal(0.4f);
        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(camLeft);
        }
        if (right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (up) {
            walkDirection.addLocal(camDir);
        }
        if (down) {
            walkDirection.addLocal(camDir.negate());
        }
        playerMovement.setWalkDirection(walkDirection);
        cam.setLocation(playerMovement.getPhysicsLocation());
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void initWall() {
        // Create a wall with a simple texture from test_data
        Box box = new Box(15, 4.5f, 1.0f);
        Spatial wall = new Geometry("Box", box);
        Material mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/wall2.jpg"));
        wall.setMaterial(mat_brick);
        wall.setLocalTranslation(0.0f, 0, 9);
        //rootNode.attachChild(wall);

        box = new Box(15, 4.5f, 1.0f);
        wall = new Geometry("Box", box);
        mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/wall2.jpg"));
        wall.setMaterial(mat_brick);
        wall.setLocalTranslation(0.0f, 0, -19);
        rootNode.attachChild(wall);

        box = new Box(15, 4.5f, 1.0f);
        wall = new Geometry("Box", box);
        mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/wall2.jpg"));
        wall.setMaterial(mat_brick);
        wall.rotate(0, 190, 0);
        wall.setLocalTranslation(15, 0, -5);
        rootNode.attachChild(wall);

        box = new Box(15, 4.5f, 1.0f);
        wall = new Geometry("Box", box);
        mat_brick = new Material(
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
                assetManager.loadTexture("Textures/wall2.jpg"));
        wall.setMaterial(mat_brick);
        wall.rotate(0, 190, 0);
        wall.setLocalTranslation(-15, 0, -5);
        rootNode.attachChild(wall);
    }

    private void setScreenText(String msg) {
        BitmapText inhand = (BitmapText) guiNode.getChild("InHand");
        inhand.setText(msg);
    }

    private void initText() {
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
