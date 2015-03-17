package com.thebigfail.dz;

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
    private static boolean isUpdate = true;

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


        // (centerX, centerY) Location of the pet on the screen
        centerX = 100;
        centerY = 100;

        setXY();
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
        return  centerX;
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

    // Set if the update method(updating the hunger, thirst and fatigue) should work or not.
    // Pass in true to enable it, else pass in false;
    public static void setUpdate(boolean update) {
        isUpdate = update;
    }

    // Return if the Pet hunger, fatigue and thirst are updating or not.
    public static boolean isUpdating() {
        return isUpdate;
    }

    // moves the pet to the given (x, y) location in a straight line
    // All this happens in a new thread.
    // An animation can also be created here, will implement when we get a few images.
    public void moveTo(final int x, final int y) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                double m = Math.abs(y - getCenterY()) / Math.abs(x - getCenterX());
                while(x != getCenterX()) {
                    setCenterX(getCenterX() + 4);

                    setCenterY( (int) (m * (double) (x - getCenterX()) + y));

                    try{
                        Thread.sleep(17);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    // Updates the Hunger, Fatigue and Thirst of the pet.
    // Doesn't work fully.
    public void update() {
        if(Pet.isUpdating()) {
            return;
        }

        Thread thread = new Thread(new Runnable() {
            boolean running = true;
            @Override
            public void run() {
                while(Pet.isUpdating()) {
                    hunger += hungerRate;
                    fatigue += fatigueRate;
                    thirst += thirstRate;

                    try {
                        // Sleep for 15 minutes.
                        Thread.sleep(15 * 60 * 1000);
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }
}
