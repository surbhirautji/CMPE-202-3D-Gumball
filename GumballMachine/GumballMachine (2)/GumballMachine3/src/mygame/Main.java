package mygame;

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
import com.jme3.animation.LoopMode;

public class Main extends SimpleApplication implements AnimEventListener {

    private Spatial gumballWorld;
    private Node player;
    private Node shootables;
    private Geometry mark;
    private GumballMachine gumballMachine;
    private Boolean hasNickel;
    private Boolean hasDime;
    private Boolean hasQuarter;
    private int Quarters;
    private int Dime;
    private int Nickel;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {

        flyCam.setMoveSpeed(5.0f);
        initCrossHairs();
        initKeys();
        initMark();
        initShootables();
        initVariables();

        makeFloor();
        makeCeiling();
        initWall();
        initText();
        initBallLight();
        //initButtonCrank();
        initMachineScreenText();
    }

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

    }

    private void initVariables() {
        gumballMachine = new GumballMachine(5);
        hasNickel = false;
        hasDime = false;
        hasQuarter = false;
        Quarters = 10;
        Dime = 5;
        Nickel = 5;
    }

    private void initMachineScreenText() {

        Box box2 = new Box(5, 0.5f, 0.2f);
        Geometry red = new Geometry("Box", box2);
        Material mat2 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Black);
        red.setName("MachineScreen");
        red.setMaterial(mat2);
        red.setLocalScale(0.5f);
        red.setLocalTranslation(-0.0f, 0.5f, -4.7f);
        rootNode.attachChild(red);

        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText inHand = new BitmapText(guiFont, false);
        inHand.setSize(guiFont.getCharSet().getRenderedSize());
        inHand.setText("Welcome");
        inHand.setName("MachineScreenText");
        inHand.setLocalScale(0.01f);
        inHand.setLocalTranslation(-2.0f, 0.5f, -4.5f);
        rootNode.attachChild(inHand);
    }

    private void setMachineScreenText(String msg) {
        BitmapText MachineScreen = (BitmapText) rootNode.getChild("MachineScreenText");
        MachineScreen.setText(msg);
    }

    private void initBallLight() {
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);

    }

    public void initBallBounce() {
        rootNode.detachChildNamed("Ball");
        AnimChannel channel;
        AnimControl control;
        Spatial player = assetManager.loadModel("Models/Gumball/redgumball.j3o");
        player.setLocalScale(0.3f);
        player.setLocalTranslation(-0.0f, -1.0f, -4.6f);
        player.setName("Ball");
        rootNode.attachChild(player);
        Spatial child = rootNode.getChild("Sphere");
        control = child.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("Bounce");

    }

    /**
     * Declaring the "Shoot" action and mapping to its triggers.
     */
    private void initKeys() {
        inputManager.addMapping("Shoot",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT)); // trigger 2: left-button click

        inputManager.addMapping("Quarter", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Nickel", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("Dime", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("Reset", new KeyTrigger(KeyInput.KEY_4));
        inputManager.addListener(actionListener, "Quarter", "Nickel", "Dime", "Reset");
        inputManager.addListener(actionListener, "Shoot");

        // inputManager.addMapping("Bounce", new KeyTrigger(KeyInput.KEY_SPACE));
        // inputManager.addListener(actionListener, "Bounce");
    }
    /**
     * Defining the "Shoot" action: Determine what was hit and how to respond.
     */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            //  if (name.equals("Bounce") && !keyPressed) {
            //    if (!channel.getAnimationName().equals("Bounce")) {
            //      channel.setAnim("Bounce", 0.50f);
            //      channel.setLoopMode(LoopMode.DontLoop);
            //    }
            //  }

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

                        String msg = gumballMachine.turnCrank();
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
            return gumballMachine.insertNickel();
        } else if (hasDime) {
            return gumballMachine.insertDime();
        } else if (hasQuarter) {
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
            if (hit.contains("Oto-geom-1")) {
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
        /**
         * create four colored boxes and a floor to shoot at:
         */
        shootables = new Node("Shootables");
        rootNode.attachChild(shootables);
        shootables.attachChild(makeCharacter());
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

    protected void initMark() {
        Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
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

    protected Spatial makeCharacter() {
        // load a character from jme3test-test-data
        Spatial golem =  assetManager.loadModel("Models/GumballMachineModel/gumballmachine.j3o");
        //System.out.println(golem);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        golem.setMaterial(mark_mat);
        
        golem.scale(0.5f);
        golem.setLocalTranslation(-0.0f, -3.0f, -5.6f);
        golem.setName("Oto");
        // We must add a light to make the model visible
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        golem.addLight(sun);
        return golem;
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    private void initBall() {

        // We must add a light to make the model visible

        Spatial ball = assetManager.loadModel("Models/Ball/blender2ogre-export.scene");
        ball.scale(0.3f);

        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.1f, -0.2f, -0.5f));
        ball.addLight(sun);
        rootNode.attachChild(ball);
    }
    //(15, .2f, 15);

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
        rootNode.attachChild(wall);

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
        inHand.setSize(guiFont.getCharSet().getRenderedSize()+10);
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
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
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
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
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
        helloText.setSize(guiFont.getCharSet().getRenderedSize());
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
