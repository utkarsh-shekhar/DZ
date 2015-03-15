package com.thebigfail.dz;

/**
 * Created by Utkarsh on 3/15/2015.
 */
public class Pet {
    private String name, species;
    private int hp, attack, defence;
    private float fatigue, hunger, thirst;
    private float fatigueRate, hungerRate, thirstRate;
    private float baseFatigueRate, baseHungerRate, baseThirstRate;

    Pet(String name) {
        this.Pet(name, "default");
    }

    Pet(String name, String species) {
        this.name = name;
        this.species = species;

        // These all variables must be randomly generate within a
        // fixed range.

        hp = 70;        // Total HP
        attack = 5;     // Raw attack
        defence = 10;   // in percentage

        fatigue = 0;
        hunger = 0;
        thirst = 0;

        baseFatigueRate = fatigueRate = 0.01f;
        baseHungerRate = hungerRate = 0.01f;
        baseThirstRate = thirstRate = 0.01f;
    }

    void walk(int x, int y) {

    }

    void updateState(boolean running) {
        Runnable runnable = new Runnable () {
            private boolean running = true;

            @Override
            public void run() {
                while(running) {
                    fatigue +=fatigueRate;
                    hunger += hungerRate;
                    thirst += thirstRate;

                    try {
                        Thread.sleep(15 * 60 * 1000);
                    } catch(InterruptedException ie) {
                        ie.printStackTrace();
                    }
                }
            }

            public void setRunning(boolean flag) {
                running = flag;
            }

            public boolean getRunning() {
                return running;
            }
        };

        if(runnable.g)
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
