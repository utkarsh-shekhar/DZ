package com.thebigfail.dz;

import com.badlogic.gdx.Gdx;

import java.util.Random;

/**
 * Created by Utkarsh on 3/15/2015.
 */
public class Pet {
    private String name, species;
    private int hp, attack, defence, level;
    private int maxHp, maxAttack, maxDefence;

    // Here X, Y represent the coordinates of the bottom left corner of the pet image
    private int X, Y, centerX, centerY;
    private float hunger, fatigue, thirst;
    private float hungerRate, fatigueRate, thirstRate;
    private float baseHungerRate, baseFatigueRate, baseThirstRate;
    private static boolean isUpdate = false;
    private static boolean isMoving = false;

    // Not really sure if this should be a singleton class or not.
    // I'll come back to it later.
    Pet(String name) {
        this(name, "default");
    }

    Pet(String name, String species) {
        this.name = name;
        this.species = species;

        // These things need to be randomly generated
        // in a given range(a, b).
        // The range (a, b) has not yet been decided.
        maxHp = hp = 100;
        maxAttack = attack = 10;
        maxDefence = defence = 10;      // Percentage
        level = 1;

        hungerRate = 0.1f;
        fatigueRate = 0.1f;
        thirstRate = 0.1f;



        // (centerX, centerY) Location of the pet on the screen
        centerX = 100;
        centerY = 100;

        setXY();

        update();
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

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setXY() {
        X = centerX + 75;
        Y = centerY + 75;
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
        if (isMoving()) {
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

                        while (x > getCenterX()) {
                            setCenterX(getCenterX() + 4);

                            setCenterY((int) (m * (double) (x - getCenterX()) + y));

                            try {
                                Thread.sleep(17);
                            } catch (InterruptedException ie) {
                                ie.printStackTrace();
                            }
                        }
                    } else {
                        while (y > getCenterY()) {
                            setCenterY(getCenterY() + 4);

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
                    int probability = random.nextInt(3);
                    if(probability == 1 && !isMoving()) {
                        randomMovements();
                    }

                    if(count == 2) {
                        hunger += hungerRate;
                        fatigue += fatigueRate;
                        thirst += thirstRate;

                        count = 0;
                    }
                    try {
                        // Sleep for 1 minute.
                        Thread.sleep(2  * 1000);
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
            x = random.nextInt(Gdx.graphics.getWidth() * 3 - 150) + 75;
            y = random.nextInt(Gdx.graphics.getHeight() - 150) + 75;

            dist = Math.sqrt(Math.pow(Math.abs(x - getCenterX()), 2) + Math.pow(Math.abs(y - getCenterY()), 2));
        } while (dist < 25);

        moveTo(x, y);
    }
}
