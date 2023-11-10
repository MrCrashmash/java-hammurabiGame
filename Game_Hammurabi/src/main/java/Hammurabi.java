// File   : hammurabi-noop/Hammurabi0.java
// Purpose: Starting point for working on the Hammurabi program.
// Author : Fred Swartz - 2007-05-03 - Placed in public domain.
package main.java;

import static main.java.variables.GameVariables.*;
import static main.java.constants.GameConstants.*;

public class Hammurabi {

    //================================================================== main
    public static void main(String[] args) {
        //... Run the simulation for 5 years or until everyone starves.
        while (k_peasants > 0 && k_year <= 5) {
            displayStatus();              // Show status of kingdom every year.
            int sb_acres  = promptForAcres(); // How much acre where sold or brought
            int food  = promptForFood();  // How much grain should be used for food?
            int seeds = promptForSeeds(); // How much grain should be used for planting?
            simulateOneYear(food, seeds); // Simulate effect of feeding and planting.
            k_year++;                     // Another year has passed.
        }

        //... End of simulation.  Show status.
        if (k_peasants == 0) {
            System.out.println("Everyone starved!!!!");
        } else {
            System.out.println("Congratuations, you survived for 5 years!");
        }
        displayStatus();
    }

    //========================================================== displayStatus
    public static void displayStatus() {
        System.out.println("  Kingdom status at year " + k_year+":"
                + "\n Last harvest = " + k_harvest
                + "\n Total grain  = " + k_grain
                + "\n Peasants     = " + k_peasants
                + "\n Acres        = " + k_area);
    }

    //========================================================= promptForAcres
    private static int promptForAcres() {
        // Ask the ruler if he wants to sell or buy land
        System.out.println("Exalted Ruler, How many acres do you wish to buy or sell?");
        int sb_acres = in.nextInt();

        while(k_area < sb_acres){
            System.out.println("But exalted Ruler, we only have " + k_area);
            System.out.println("How many acres do you really wish to buy or sell?");
            sb_acres = in.nextInt();
        }
        if(sb_acres != 0){
            k_area += sb_acres; // Reduce or ingress depending on how much land was bought or sold.
            k_grain -= k_acre_price * sb_acres; // Reduce or ingress depending on the land price and how much land was bought or sold.
            System.out.println("Exalted Ruler, you have now "+k_area+" acres and "+k_grain+" bushels");
        }
        return sb_acres;
    }

    //========================================================== promptForFood
    private static int promptForFood() {
        // Ask the ruler how much grain should be used for feeding the peasants.
        System.out.println("Exalted Ruler, how many bushels do you wish to feed your people?");
        int grainToFeed = in.nextInt();

        while (k_grain < grainToFeed){
            System.out.println("But exalted Ruler, we only have "+k_grain+" seeds");
            grainToFeed = in.nextInt();
        }
        k_grain -= grainToFeed; // Reduce grain by amount used for seed.
        return grainToFeed;
    }

    //========================================================= promptForSeeds
    private static int promptForSeeds() {
        // Ask the ruler how much grain should be used for seed.
        System.out.println("Exalted Ruler, how many bushels should be planted?");
        int grainToPlant = in.nextInt();

        boolean ready = true;
        do{
            if(k_area < grainToPlant / SEED_REQUIRED_PER_ACRE){
                System.out.println("But exalted Ruler, you only have "+k_area+" acres and only need to use "+k_area * SEED_REQUIRED_PER_ACRE+" seeds");
                System.out.println("How many bushels should be really planted?");
                grainToPlant = in.nextInt();
                continue;
            }
            if(k_grain < grainToPlant){
                System.out.println("But exalted Ruler, we only have "+k_grain+" seeds");
                System.out.println("How many bushels should be really planted?");
                grainToPlant = in.nextInt();
                continue;
            }
            if(k_peasants < ((grainToPlant / SEED_REQUIRED_PER_ACRE) / MAX_LAND_FARMABLE_PER_PERSON)){
                System.out.println("But exalted Ruler, we only have "+k_peasants+" peasants, they only can till "+k_peasants * MAX_LAND_FARMABLE_PER_PERSON+" arces");
                System.out.println("How many bushels should be really planted?");
                grainToPlant = in.nextInt();
                continue;
            }
            ready = false;
        }while(ready);

        k_grain -= grainToPlant; // Reduce grain by amount used for seed.
        return grainToPlant;
    }

    //========================================================= simulateOneYear
    private static void simulateOneYear(int food, int seed) {
        //... Calculate new population. Based on food. More food results Immigrants
        k_peasants = food / MIN_GRAIN_TO_SURVIVE;

        //    2. The yield per acre is random (2-6)
        //    3. Harvest is yield * area planted.

        // How many acres are planted with seed.
        int acresPlanted = seed / SEED_REQUIRED_PER_ACRE;

        int yieldPerAcre = randomWithRange(MIN_RANGE_YIELD ,MAX_RANGE_YIELD );  // Number from 2 to 6

        k_harvest        = yieldPerAcre * acresPlanted;

        //... Compute new amount of grain after the harvest.
        k_grain += k_harvest;  // New amount of grain in storage.
    }

    private static int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

}