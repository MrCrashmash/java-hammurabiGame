package main.java.logic;

import main.java.GUI.GUI;
import static main.java.variables.GameVariables.*;
import static main.java.constants.GameConstants.*;

public class Logic {
    private static int acres;
    private static int grain;

    public static void starter(){
        k_grain = START_GRAIN;
        k_area  = START_AREA;
        k_year  = START_YEAR;
        k_harvest = START_HARVEST;
        k_peasants = START_PEASANTS;
        setNewAcrePrice();
    }

    public static void submiter(int tradedAcres, int foodGrain, int grainToPlant){
        acres = k_area;
        grain = k_grain;


        if(check_ForAcres(tradedAcres)
                && check_ForFood(foodGrain)
                && check_ForSeeds(grainToPlant)
                && check_ForGrain()){
            simulateYear(foodGrain, grainToPlant);
        }
    }


    private static boolean check_ForAcres(int tradedAcres){
        if(tradedAcres != 0){
            if(0 > acres + tradedAcres){
                GUI.setStatus("We only have "+acres+" acres you selling to much.");
                return false; //We only have X acres you selling to much.
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

    private static boolean check_ForFood(int foodGrain){

        if(grain < foodGrain){
            GUI.setStatus("We dont have this  much grain, we only have "+grain+" grain. (after Acre trade)");
            return false; //We dont have this  much grain, We only have X grain.
        }
        if(0 > foodGrain){
            GUI.setStatus("You cant feed the People with a  negativ value.");
            return false; //You cant feed the People with a  negativ value.
        }

        grain -= foodGrain;
        return true;
    }

    private static boolean check_ForSeeds(int grainToPlant){
        if(acres < grainToPlant / SEED_REQUIRED_PER_ACRE){
            GUI.setStatus("You only have "+acres+" acres and only need to use "+acres * SEED_REQUIRED_PER_ACRE+" seeds.");
            return false; //you only have X acres and only need to use Y seeds.
        }
        if(grain < grainToPlant){
            GUI.setStatus("We only have "+k_grain+" seeds.");
            return false; //We only have "+k_grain+" seeds.
        }
        if(0 > grainToPlant){
            GUI.setStatus("You cant plant a negativ number of seeds.");
            return false; //You cant plant a negativ number of seeds.
        }
        if(k_peasants < ((grainToPlant / SEED_REQUIRED_PER_ACRE) / MAX_LAND_FARMABLE_PER_PERSON)){
            GUI.setStatus("We only have "+k_peasants+" peasants, they only can till "+k_peasants*MAX_LAND_FARMABLE_PER_PERSON+" arces.");
            return false; //we only have X peasants, they only can till Y arces
        }

        grain -= grainToPlant;
        return true;
    }

    private static boolean check_ForGrain(){
        if(0 > grain){
            return false; //you are spending to much grain.
        }
        return true;
    }

    private static void simulateYear(int foodGrain, int grainToPlant){
        k_area = acres;
        k_grain = grain;

        //... Calculate new population. Based on food. More food results in Immigrants
        k_peasants = foodGrain / MIN_GRAIN_TO_SURVIVE;
        // How many acres are planted with seed.
        int acresPlanted = grainToPlant / SEED_REQUIRED_PER_ACRE;

        int yieldPerAcre = randomWithRange(MIN_RANGE_YIELD ,MAX_RANGE_YIELD );  // Number from 2 to 6

        k_harvest        = yieldPerAcre * acresPlanted;

        //... Compute new amount of grain after the harvest.
        k_grain += k_harvest;  // New amount of grain in storage.
        k_year++;
        setNewAcrePrice();
        GUI.setStatusText();
        GUI.textfieldRest();
    }

    private static void setNewAcrePrice(){
        k_acre_price = randomWithRange(MIN_RANGE_ACRE_PRICE, MAX_RANGE_ACRE_PRICE);
    }
    private static int randomWithRange(int min, int max){
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }





}
