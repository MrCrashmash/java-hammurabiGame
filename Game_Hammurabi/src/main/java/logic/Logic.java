package main.java.logic;
import main.java.GUI.GUI;
import javax.swing.*;
import static java.lang.Math.abs;
import static main.java.variables.GameVariables.*;
import static main.java.constants.GameConstants.*;

/**
 * Logic component of the Game
 */
public class Logic {
    private static int acres;
    private static int grain;

    /**
     * Game start Method, is also used to restart the game.
     * Gives everything there starting values or conditions
     */
    public static void starter(){
        k_grain = START_GRAIN;
        k_area  = START_AREA;
        k_year  = START_YEAR;
        k_harvest   = START_HARVEST;
        k_peasants  = START_PEASANTS;
        k_starved   = 0;
        k_immigrated = 0;
        ratsAte = 0;
        isPlague = false;
        setNewAcrePrice();
        GUI.textFieldRest();
        GUI.setReportText();
    }

    /**
     * This method takes the inputs from the UI and checks if these are valid for further use.
     * @param tradedAcres is the number of acres the ruler want to trade: negative int for selling and positive int for buying
     * @param foodGrain is the number of grain the ruler want to give the people as food.
     * @param grainToPlant is the number of grain the ruler wants to plant his acres
     */
    public static void submitter(int tradedAcres, int foodGrain, int grainToPlant){
        acres = k_area;
        grain = k_grain;

        if(checkForAcres(tradedAcres)
                && checkForFood(foodGrain)
                && checkForSeeds(grainToPlant)
                && checkForGrain()){
            simulateYear(foodGrain, grainToPlant);
        }
    }

    /**
     * This method validates the number of acres the ruler want to trade and updates the intermediate value of the grain and acres that the ruler has.
     * @param tradedAcres is the number of acres the ruler want to trade: negative int for selling and positive int for buying.
     * @return boolean true if the number is valid
     *                 false otherwise.
     */
    private static boolean checkForAcres(int tradedAcres){
        if(tradedAcres != 0){
            if(0 > acres + tradedAcres){
                GUI.setStatus("We only have "+acres+" acres you selling to much.");
                return false; //We only have X acres you're selling too much.
            }
            if(grain < tradedAcres * k_acre_price){
                GUI.setStatus("You buy too much, we only have "+grain+" grain.");
                return false; //You buy too much, we only have X grain.
            }
        }
        // Update private acres and grain values.
        acres += tradedAcres;
        grain -= k_acre_price * tradedAcres;
        return true;
    }

    /**
     * This method validates the number of grain the ruler wants to give the people as food and updates the intermediate value of the grain that the ruler has.
     * @param foodGrain is the number of grain the ruler want to give the people as food.
     * @return true if the number is valid
     *         false otherwise.
     */
    private static boolean checkForFood(int foodGrain){

        if(grain < foodGrain){
            GUI.setStatus("We don't have this  much grain, we only have "+grain+" grain. (after Acre trade)");
            return false; //We don't have this  much grain, We only have X grain.
        }
        if(0 > foodGrain){
            GUI.setStatus("You cant feed the People with a  negative value.");
            return false; //You cant feed the People with a  negative value.
        }

        grain -= foodGrain;
        return true;
    }

    /**
     * This method validates the number of grain the ruler want to plant his acres and updates the intermediate value of the grain that the ruler has.
     * @param grainToPlant is the number of grain the ruler wants to plant his acres
     * @return true if the number is valid
     *         false otherwise.
     */
    private static boolean checkForSeeds(int grainToPlant){
        if(acres < grainToPlant / SEED_REQUIRED_PER_ACRE){
            GUI.setStatus("You only have "+acres+" acres and only need to use "+acres * SEED_REQUIRED_PER_ACRE+" seeds.");
            return false; //you only have X acres and only need to use Y seeds.
        }
        if(grain < grainToPlant){
            GUI.setStatus("We only have "+k_grain+" seeds.");
            return false; //We only have "+k_grain+" seeds.
        }
        if(0 > grainToPlant){
            GUI.setStatus("You cant plant a negative number of seeds.");
            return false; //You cant plant a negative number of seeds.
        }
        if(k_peasants < ((grainToPlant / SEED_REQUIRED_PER_ACRE) / MAX_LAND_FARMABLE_PER_PERSON)){
            GUI.setStatus("We only have "+k_peasants+" peasants, they only can till "+k_peasants*MAX_LAND_FARMABLE_PER_PERSON+" arces.");
            return false; //we only have X peasants, they only can till Y arces
        }

        grain -= grainToPlant;
        return true;
    }

    /**
     * This method checks if the ruler after all has enough grain to pay everything
     * @return true if he has enough grain
     *         false otherwise.
     */
    private static boolean checkForGrain(){
        return 0 <= grain; //you are spending too much grain.
    }

    /**
     * This methode simulates one game year. It updates the global variables with the intermediate values that where checked.
     * It updates the number of peasants and takes food, plague and migrants into account.
     * It updates the grain count and takes this year's harvest and rats into account.
     * It checks whether an endgame condition has been reached. Either because the years have been reached or the kingdom has starved.
     * At the end: a new acre price is set, the report is updated and the input fields cleared.
     * @param foodGrain the grain the ruler wants to feed to the people.
     * @param grainToPlant the grain the ruler wants to use for planting.
     */
    private static void simulateYear(int foodGrain, int grainToPlant){
        k_area = acres;
        k_grain = grain;

        // Calculate new population. Based on food, more food results in Immigrants, plague halves the population
        updatePeasants(foodGrain);

        // How many acres are planted with seed.
        calcHarvest(grainToPlant);

        //... Compute new amount of grain after the harvest.
        k_grain += k_harvest;  // New amount of grain in storage.
        k_grain -= howMuchRatsAte(); // Rats eat a small percentage of the grain

        gameEnd(); // check if game survived
        k_year++; // next Year
        setNewAcrePrice(); // new acre price
        GUI.setReportText(); // update Status Text
        GUI.textFieldRest(); // clear input fields
    }

    /**
     * This method updates the number of peasants and takes the food and the plague into account
     * @param food the grain the ruler wants to feed to the people.
     */
    private static void updatePeasants(int food){
        int feedPeople = food / MIN_GRAIN_TO_SURVIVE;
        int growth = feedPeople - k_peasants; // negative if people starved --- positive if people game to the city

        if(growth < 0){
            k_starved = Math.abs(growth);
            k_immigrated = 0;
        } else if(growth > 0){
            k_starved = 0;
            k_immigrated = Math.abs(growth);
        } else {
            k_starved = 0;
            k_immigrated = 0;
        }
        k_peasants = feedPeople;
        k_peasants = isPlague() ? k_peasants / 2 : k_peasants;
    }

    /**
     * This method is checking if the endgame condition is reached. If yes it gives the ruler the option to restart or close the game.
     */
    private static void gameEnd(){
        if (k_peasants <= 0) {
            GUI.setStatus("Everyone starved!!!!");
            int result = JOptionPane.showConfirmDialog(null, "Everyone starved! \n Close Game?", "You Lost", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                System.exit(0);
            } else {
                starter();
            }
        }
        if(k_year >= GAME_LENGTH){
            GUI.setStatus("Congratulations, you survived for "+GAME_LENGTH+" years!");
            int result = JOptionPane.showConfirmDialog(null, "Congratulations! \n Close Game?", "You Won", JOptionPane.YES_NO_OPTION);
            if(result == JOptionPane.YES_OPTION){
                System.exit(0);
            } else {
                starter();
            }
        }
    }

    /**
     * This method calculates the harvest for the current year. The yield is a random number (in a range).
     * @param seeds the grain the ruler wants to use for planting.
     */
    private static void calcHarvest(int seeds){
        int acresPlanted = seeds / SEED_REQUIRED_PER_ACRE;
        int yieldPerAcre = randomWithRange(MIN_RANGE_YIELD, MAX_RANGE_YIELD);  // Number from 2 to 6
        k_harvest        = yieldPerAcre * acresPlanted;
    }

    /**
     * This method sets a new acres price. The price is random (in a range).
     */
    private static void setNewAcrePrice(){
        k_acre_price = randomWithRange(MIN_RANGE_ACRE_PRICE, MAX_RANGE_ACRE_PRICE);
    }

    /**
     * This method gives out a random number in a given range.
     * @param min beginning of the range
     * @param max end of the range
     * @return int a random number in the given range
     */
    private static int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    /**
     * This method is randomly deciding with a given percentage (Plague_Chance) if there is a plague struck or not
     * @return boolean true there is a plague
     *                 false otherwise.
     */
    private static boolean isPlague(){
        int plague = randomWithRange(0, 999);
        isPlague = plague < PLAGUE_CHANCE * 10;
        return isPlague;
    }

    /**
     * This Method is randomly deciding with a given percentage (RAT_MAX_AMOUNT) how much the rats ate this year.
     * @return int the amount the rats ate
     */
    private static int howMuchRatsAte(){
        double ratAmount = (double) randomWithRange(0, RAT_MAX_AMOUNT) / 100;
        ratsAte = (int) (ratAmount * k_grain);
        return ratsAte;
    }
}

