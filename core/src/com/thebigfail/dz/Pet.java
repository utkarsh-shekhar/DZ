package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by Utkarsh on 3/15/2015.
 */
public class Pet {
    Dz dz;
    private String name, species;           // This will store the name and the species of the pet. Default species = "default".
    private String baseImage;               // This will store the location of the base image of the pet.
    private String[] petMoods;              // This will store all the moods of the pet
                                            // eg: petMoods[0] -> Default cry of the pet
                                            //     petMoods[1] -> Image when it is happy,
                                            //     petMoods[2] -> Image when it is sad,
                                            //     petMoods[3] -> Image when it is tired,
                                            //     petMoods[4] -> Image when it is hungry,
                                            //     petMoods[5] -> Image when it is clicked on, etc.
                                            // This is all TODO for now.

    private String[] moveFrames;            // This will store all the frame for the animation while the pet is moving.
    private Sound[] soundClips;             // This will store the location of each sound clip associated with the pet.
                                            // Standard notations will keep the sounds naming conventions the same for each pet.
                                            // eg: soundClips[0] -> Default cry of the pet
                                            //     soundClips[1] -> Sound a pet makes when it is happy,
                                            //     soundClips[2] -> Sound a pet makes when it is sad,
                                            //     soundClips[3] -> Sound a pet makes when it is tired,
                                            //     soundClips[4] -> Sound a pet makes when it is hungry,
                                            //     soundClips[5] -> Sound a pet makes when it is clicked on, etc.
//                                                    check isPetTouched() in various screen sizes
                                            // This is all TODO for now.

    private int hp, attack, defence, level;
    private int maxHp, maxAttack, maxDefence;

    // Here X, Y represent the coordinates of the bottom left corner of the pet image
    private int X, Y, centerX, centerY;
    private float hunger, fatigue, thirst;
    private float hungerRate, fatigueRate, thirstRate;
    private float maxHunger, maxFatigue, maxThirst;
    private float baseHungerRate, baseFatigueRate, baseThirstRate;
    private static boolean isUpdate = false;
    private static boolean isMoving = false;
    private static boolean touched = false;

    private SpriteBatch batch;
    private Texture red, green, blue;
    private int maxWidthToPlot;
    private float lineWidth;

    private AssetManager assetManager;


    // Not really sure if this should be a singleton class or not.
    // I'll come back to it later.
    Pet(String name,Dz dz) {     //passing dz object too
        this(name, "default",dz);

    }

    Pet(String name, String species, Dz dz) {
        this.name = name;
        this.dz= dz;
        this.species = species;

        batch = new SpriteBatch();


        petMoods = new String[10];
        soundClips = new Sound[10];

        // These things need to be randomly generated
        // in a given range(a, b).
        // The range (a, b) has not yet been decided.
        maxHp = hp = 100;
        maxAttack = attack = 10;
        maxDefence = defence = 10;      // Percentage
        level = 1;

        maxHunger = maxFatigue = maxThirst = 26;
        hunger = maxHunger;
        thirst = maxThirst;
        fatigue = 1;

        // These rates are still not correct.
        // TODO them before the app is finished and is ready to hit the market.
        baseHungerRate = hungerRate = 30 * 0.01736f;
        baseFatigueRate = fatigueRate = 50 * 0.01736f;
        baseThirstRate = thirstRate =  20 * 0.01736f;

        red = new Texture("red.png");
        green = new Texture("green.png");
        blue = new Texture("blue.png");

        maxWidthToPlot = Gdx.graphics.getWidth() / 3;
        lineWidth = 15 / dz.xScale;

        loadSounds();

        // (centerX, centerY) Location of the pet on the screen
        centerX = 100;
        centerY = 100;

        // Assign the base image and the animation images according to the species.
        if(species == "default") {
            baseImage = "bot.png";

            petMoods[0] = "bot.png";
            petMoods[1] = "tiredbot.png";

            soundClips[0] = Gdx.audio.newSound(Gdx.files.internal("clicked.wav"));
        }
        dz.petBase = new Texture(getBaseImage());
        setXY();

        update();
    }

    // This method returns the location of the base image of the pet.
    public String getBaseImage() {
        return baseImage;
    }

    // This method returns the location of the sound to play for a specific event.
    public void playSoundClip(int index) {
       /* if (assetManager.isLoaded("clicked.ogg")){
            soundClips[0] = assetManager.get("clicked.ogg", Sound.class);
            soundClips[0].play();
        }*/

        //soundClips[0] = Gdx.audio.newSound(Gdx.files.internal("assets/clicked.wav"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long id = soundClips[0].play();

        Gdx.app.log("LOLOLOL", "Long id = " + id);
    }

    public float getHunger() {
        return hunger;
    }

    public float getFatigue() {
        return fatigue;
    }

    public float getThirst() {
        return thirst;
    }

    // This method returns the x-axis value of the lower left corner of the pet image.
    public int getX() {
        return X;
    }

    // This method returns the y-axis value of the lower left corner of the pet image.
    public int getY() {
        return Y;
    }

    public void setXY() {
        X = centerX - dz.petBase.getWidth()/2;
        Y = centerY - dz.petBase.getHeight()/2;

    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterX(int x) {
        centerX = x;
        setXY();
    }

    public void setCenterY(int y) {
        centerY = y;
        setXY();
    }

    public void setIsMoving(boolean flag) {
        isMoving = flag;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    // Returns true if the pet lies where you have touched the screen
    public boolean isThere(int x, int y) {
        //System.out.println("getX: "+ getX() + "\t getY: " +getY());
        //System.out.println("x: "+ x + "\t y: " +y);
        //System.out.println("yScale: " + dz.yScale);
        //y=(int)(y*dz.yScale);
        //System.out.println("x: "+ x + "\t y: " +y);
        if(x >= getX()  && x <= getX() + dz.petBase.getWidth() && y >= getY()   && y <= getY() + dz.petBase.getHeight() )
            return true;

        return false;
    }

    // Set if the update method(updating the hunger, thirst and fatigue) should work or not.
    // Pass in true to enable it, else pass in false;
    public static void setUpdate(boolean update) {
        isUpdate = update;
    }

    // Return if the Pet hunger, fatigue and thirst are updating or not.
    public static boolean isUpdating() {
        return isUpdate;
    }

    // TODO: Check if passed (x, y) is a valid location.
    public boolean validLocation(int x, int y) {
        return true;
    }

    // moves the pet to the given (x, y) location in a straight line
    // All this happens in a new thread.
    // An animation can also be created here, will implement when we get a few images.
    public void moveTo(final int x, final int y) {
        if (isMoving() || isTouched()) {
            return;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    setIsMoving(true);
                    if(x == getCenterX())
                        setCenterX(getCenterX() - 1);

                    double m = Math.abs(y - getCenterY()) / Math.abs(x - getCenterX());

                    if(Math.abs(x - getCenterX()) >= Math.abs(y - getCenterY())) {

                        while (x > getCenterX() && !isTouched()) {
                            setCenterX(getCenterX() + 4);

                            setCenterY((int) (m * (double) (x - getCenterX()) + y));

                            try {
                                Thread.sleep(17);
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                        }
                    } else {
                        while (y > getCenterY()  && !isTouched()) {
                            setCenterY(getCenterY()+4);

                            setCenterX((int) ( (1 / m) * (double) (y - getCenterY()) + x));

                            try {
                                Thread.sleep(17);
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                        }
                    }
                    setIsMoving(false);
                }
            }
        });
        thread.start();
    }

    // Updates the Hunger, Fatigue and Thirst of the pet.
    // Doesn't work fully.
    public void update() {
        if (Pet.isUpdating()) {
            return;
        }

        Pet.setUpdate(true);
        Thread thread = new Thread(new Runnable() {
            boolean running = true;

            @Override
            public void run() {
                int count = 0;
                Random random = new Random();

                while (Pet.isUpdating()) {
                    count++;
                    if(hunger > maxHunger / 4) {

                        if(hungerRate != baseHungerRate) {
                            hungerRate = baseHungerRate;
                        }
                    } else if(hungerRate == baseHungerRate) {
                        hungerRate = baseHungerRate / 2;
                    }

                    // If pet is very tired then make it preserve energy by lowering the fatigue rate.
                    if(fatigue >= maxFatigue / 4 && fatigueRate == baseFatigueRate) {
                        fatigueRate = baseFatigueRate / 2;

                        // Change the pet's image to a one where the pet is tired.
                        baseImage = petMoods[1];

                        // refresh the changes
                        refreshPetImage();

                    } else if(fatigue < maxFatigue / 4) {

                        // Only do random movements if the pet is not tired.
                        int probability = random.nextInt(3);
                        if (probability == 1 && !isMoving() && !isTouched()) {
                            randomMovements();
                        }


                        if(fatigueRate != baseFatigueRate) {
                            fatigueRate = baseFatigueRate;

                            // Change the pet's image to the actual pet image.
                            baseImage = petMoods[0];

                            // refresh the changes
                            refreshPetImage();
                        }
                    }

                    // count == 2 means we update the pet stats every second minute
                    if(count == 2) {
                        hunger = (hunger - hungerRate <= 1)? 1 : hunger - hungerRate;
                        fatigue = (fatigue + fatigueRate >= maxFatigue)? maxFatigue : fatigue + fatigueRate;
                        thirst = (thirst - thirstRate <= 1)? 1 : thirst - thirstRate;

                        count = 0;
                    }
                    try {
                        // Sleep for 1 minute.
                        // TODO make it sleep for 1 minute.
                        Thread.sleep( 1000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }

                Pet.setUpdate(false);
            }
        });
        thread.start();
    }

    public void randomMovements() {
        if (isMoving()) {
            return;
        }

        Random random = new Random();
        int x, y;
        double dist = 0;
        do {
            x = random.nextInt(dz.resolutionX * 3 - dz.petBase.getWidth()/2);
            y = random.nextInt(dz.resolutionY - dz.petBase.getHeight()/2);

            dist = Math.sqrt(Math.pow(Math.abs(x - getCenterX()), 2) + Math.pow(Math.abs(y - getCenterY()), 2));
        } while (dist < 25);

        moveTo(x, y);
    }

    public void isPetTouched() {

        //dz.camera=new OrthographicCamera(720,1280);
        int centerX = dz.resolutionX / 2;
        int dx = (int)(Gdx.input.getX()*dz.xScale-centerX);        //taking center of screen as x=0, dx is distance relative to center

        //System.out.println("X: " + ((int)dz.camera.position.x + dx) + " \t Y: " + (Gdx.graphics.getHeight() - Gdx.input.getY()));
        //System.out.println("X = " + Gdx.input.getX() + "\t Y = " + Gdx.input.getY());

        if(isThere((int) dz.camera.position.x + dx, dz.resolutionY -  (int)(Gdx.input.getY()*dz.yScale))) {
            setTouched(true);

            // System.out.println("Pet is touched...");
        } /*else {
            setTouched(false);
            System.out.println("Pet is not touched...");
        }*/
    }

    // Function to plot pet's stats.
    public void plotStats() {
        batch.begin();
            // Draw Hunger / Energy
            batch.draw((hunger > maxHunger / 4)? blue : red, 45/dz.xScale, Gdx.graphics.getHeight() - 100/dz.yScale, (hunger / maxHunger) * maxWidthToPlot , lineWidth);
            // Draw Thirst
            batch.draw((thirst > maxThirst / 4)? blue : red, 45/dz.xScale, Gdx.graphics.getHeight() - 150/dz.yScale, (thirst / maxThirst) * maxWidthToPlot , lineWidth);
            // Draw Fatigue
            batch.draw((fatigue < 3 * (maxFatigue / 4))? blue : red, 45/dz.xScale, Gdx.graphics.getHeight() - 200/dz.yScale, (fatigue / maxFatigue) * maxWidthToPlot , lineWidth);

        batch.end();

    }

    public void refreshPetImage() {
        dz.petBase.dispose();
        dz.petBase = new Texture(getBaseImage());
    }

    public void loadSounds() {
        /*assetManager = new AssetManager();
        assetManager.load("clicked.ogg", Music.class);
        assetManager.finishLoading();*/

        soundClips[0] =  Gdx.audio.newSound(Gdx.files.internal("NNoisecollector barkloud.wav"));
    }
}
