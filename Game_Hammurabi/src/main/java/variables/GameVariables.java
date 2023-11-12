package main.java.variables;

public class GameVariables {

    //=============================================================== variables
    // Everything prefixed with "k_" is part of the status of a Kingdom.
    //   All "k_" variables should be part of a Kingdom class, but....
    public static int k_grain; // Bushels of grain in storage.
    public static int k_area; // Area of kingdom in acres.
    public static int k_year;  // Years since founding of kingdom.
    public static int k_harvest;  // Last harvest in bushels.
    public static int k_peasants; // Number of peasants.
    public static int k_acre_price; // the price for an acre
    public static int k_starved; // How many Peasants starved
    public static int k_immigrated; // How many Peasants came to the Kingdom
    public static boolean isPlague; // Is there a Plague?
    public static String plagueText = "A horrible plague struck! Half the population died! "; // Is there a Plague?
    public static int ratsAte; // How much did the Rates ate?

}
