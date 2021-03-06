package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

/**
 * Created by Utkarsh on 3/15/2015.
 *
 * This class is the base class for all the pet related activities.
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
//
                                            // This is all TODO for now.

    private int width, height;

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
    private float lineHeight;

    Random random;


    // Not really sure if this should be a singleton class or not.
    // I'll come back to it later.
    Pet(String name, Dz dz) {     //passing dz object too
        this(name, "default", dz);

    }

    Pet(String name, String species, Dz dz) {
        this.name = name;
        this.dz= dz;
        this.species = species;

        batch = dz.batch;

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
        baseHungerRate = hungerRate = 1 * 0.01736f;
        baseFatigueRate = fatigueRate = 3 * 0.01736f;
        baseThirstRate = thirstRate =  20 * 0.01736f;

        red = new Texture("red.png");
        green = new Texture("green.png");
        blue = new Texture("blue.png");

        maxWidthToPlot = dz.resolutionX / 3;
        lineHeight = 15;

        loadSounds();

        width = height = 150;

        // (centerX, centerY) Location of the pet on the
        random = new Random();
        int pixmapX, pixmapY;
        do {
            pixmapX = random.nextInt(dz.map.pixmap.getWidth() - 2);
            pixmapY = random.nextInt(dz.map.pixmap.getHeight() - 2);
        } while (!validLocation(pixmapX, pixmapY));
        centerX = (int)((pixmapX * Tile.getXScale()) - Tile.getXScale() / 2);
        centerY = (int)((pixmapY * Tile.getYScale()) - Tile.getYScale() / 2);

        // Assign the base image and the animation images according to the species.
        if(species == "default") {
            baseImage = "bot.png";

            petMoods[0] = "bot.png";
            petMoods[1] = "tiredbot.png";

            // soundClips[0] = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        }
        setXY();

        update();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // This method returns the location of the base image of the pet.
    public String getBaseImage() {
        return baseImage;
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
        X = centerX - (width / 2);
        Y = centerY - (height / 2);

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
        //System.out.println("x and y:"+x+" "+y+"\tgetX and getY:"+getX()+" "+getY());
        if(x >= getX()  && x <= getX() + getWidth() && y >= getY()   && y <= getY() + getHeight() )
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
        return dz.map.match(dz.map.pixmap.getPixel(x, y) >>> 8 & 0xffffff, 0x000000);
    }
// 1 : 40
    // This method returns the location of the sound to play for a specific event.
    public void playSoundClip(final int index) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                soundClips[index].play();
            }
        });
        thread.start();
    }

    // moves the pet to the given (x, y) location in a straight line
    // All this happens in a new thread.
    // An animation can also be created here, will implement when we get a few images.
    // TODO
    // TOO MANY BUGS HERE. MUST FIX THEM
    public void moveTo(final int x, final int y) {

        if (isMoving() || isTouched()) {
            return;
        }

        moveAnimation();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    if (isMoving() || isTouched()) {
                        return;
                    }
                    setIsMoving(true);
                    if(x == getCenterX())
                        setCenterX(getCenterX() - 1);

                    double m = Math.abs(y - getCenterY()) / Math.abs(x - getCenterX());

                    if(Math.abs(x - getCenterX()) >= Math.abs(y - getCenterY())) {
                        int delta = x > getCenterX() ? 4 : -4;
                        while (Math.abs(x - getCenterX()) <= 3 && !isTouched()) {
                            setCenterX(getCenterX() + delta);

                            setCenterY((int) (m * (double) (x - getCenterX()) + y));

                            try {
                                Thread.sleep(17);
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                        }
                    } else {
                        int delta = y > getCenterY() ? 4 : -4;
                        while (y > getCenterY()  && !isTouched()) {
                            setCenterY(getCenterY() + delta);

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
                            dz.setPetTextureRegion(0, 0);
                            hungerRate = baseHungerRate;
                        }
                    } else if(hungerRate == baseHungerRate) {
                        dz.setPetTextureRegion(0, 300);
                        hungerRate = baseHungerRate / 2;
                    }

                    // If pet is very tired then make it preserve energy by lowering the fatigue rate.
                    if(fatigue >= 3 * (maxFatigue / 4) && fatigueRate == baseFatigueRate) {
                        fatigueRate = baseFatigueRate / 2;

                        // Change the pet's image to a one where the pet is tired.
                        baseImage = petMoods[1];

                        //Gdx.app.log("Fatigue", "Here at upper if");

                        dz.setPetTextureRegion(0, 150);

                    } else if(fatigue < maxFatigue / 4) {

                        // Only do random movements if the pet is not tired.
                        int probability = random.nextInt(2);
                        //Gdx.app.log("PROBABILITY: ", probability + " " + isMoving() + " " + isTouched());
                        if (probability == 1 && !isMoving() && !isTouched()) {
                            //Gdx.app.log("adf", "Random movements");
                            randomMovements();
                        }


                        if(fatigueRate != baseFatigueRate) {

                            dz.setPetTextureRegion(0, 0);
                            fatigueRate = baseFatigueRate;

                            //Gdx.app.log("Fatigue", "Here at lower else if.");
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

    // This method tries to make the pet move randomly to different positions on the map
  /*  public void randomMovements() {
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
    }*/

    // Returns the (x, y) location of the tile on the pixmap where the pet is currently at.
    // Returns a String with a space separating the x and y coordinates.
    public String pixMapLocation() {
        int x = (int)Math.ceil(centerX / 150);
        int y = (int)Math.ceil(centerY / 150);

        return x + " " + y;
    }

    public void randomMovements() {
        //Gdx.app.log("RANDOM MOVEMENTS: ", "isMoving " + isMoving() );
        if(isMoving()) return;

        int x, y, pixmapX, pixmapY;
        double dist = 0;
        do {
            pixmapX = random.nextInt(dz.map.pixmap.getWidth() - 2);
            pixmapY = random.nextInt(dz.map.pixmap.getHeight() - 2);

            x = (int)((pixmapX * Tile.getXScale()) - Tile.getXScale() / 2);
            y = (int)((pixmapY * Tile.getYScale()) - Tile.getYScale() / 2);

            dist = Math.abs(Math.sqrt(Math.pow(Math.abs(x - getCenterX()), 2) + Math.pow(Math.abs(y - getCenterY()), 2)));
        } while (dist <= 10 || !validLocation(pixmapX, pixmapY) );


        //Gdx.app.log("RANDOM MOVEMENTS: ", "x: " + x + " y: " + y );
        moveTo(x, y);
    }



    // checks if the pet is touched
    // sets the isTouched variable to true or false
    public void isPetTouched() {
        int centerX = dz.resolutionX / 2;
        int dx = (int)(Gdx.input.getX()*dz.xScale-centerX);        //taking center of screen as x=0, dx is distance relative to center

        if(isThere((int) dz.camera.position.x + dx, dz.resolutionY -  (int)(Gdx.input.getY()*dz.yScale))) {
            //System.out.println("pet is touched");
            setTouched(true);
        }
        else{
            //System.out.println("pet is not touched");
        }
    }

    // Function to plot pet's stats.
    public void plotStats() {
            // Draw Hunger / Energy
            batch.draw((hunger > maxHunger / 4)? blue : red, dz.camera.position.x-360+45, dz.resolutionY - 100, (hunger / maxHunger) * maxWidthToPlot , lineHeight);
            // Draw Thirst
            batch.draw((thirst > maxThirst / 4)? blue : red, dz.camera.position.x-360+45, dz.resolutionY - 150, (thirst / maxThirst) * maxWidthToPlot , lineHeight);
            // Draw Fatigue
            batch.draw((fatigue < 3 * (maxFatigue / 4))? blue : red, dz.camera.position.x-360+45, dz.resolutionY - 200, (fatigue / maxFatigue) * maxWidthToPlot , lineHeight);
    }

    // Loads all the sounds in the Pet class.
    // TODO.
    public void loadSounds() {
        soundClips[0] =  Gdx.audio.newSound(Gdx.files.internal("clicked.ogg"));
    }

    /*
    ** Pet animations start from here
    */

    public void moveAnimation() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                int frame = 0;
                while(isMoving()) {
                    Gdx.app.log("", "MOVING ANIMATION");
                    dz.setPetTextureRegion(150, 0 + (frame * 150));
                    frame++;
                    if(frame == 10)
                        frame = 0;
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dz.setPetTextureRegion(0, 0);
            }
        });
        thread.start();
    }

    public void dispose() {
        red.dispose();
        green.dispose();
        blue.dispose();
    }
}
