package com.thebigfail.dz;

/**
 * Created by Utkarsh on 3/15/2015.
 */
public class Pet {
    private String name, species;
    private int hp, attack, defence, level;
    private int maxHp, maxAttack, maxDefence;
    private int x, y, centerX, centerY;
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


        // (x, y) Location of the pet on the screen
        x = 100;
        y = 100;
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
    public void moveTo(int x, int y) {

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
