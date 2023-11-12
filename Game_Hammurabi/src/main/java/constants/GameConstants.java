package main.java.constants;

import java.util.Scanner;

public class GameConstants {

    //============================== constants
    public static final int MIN_GRAIN_TO_SURVIVE         = 20;
    public static final int MAX_LAND_FARMABLE_PER_PERSON = 10;
    public static final int SEED_REQUIRED_PER_ACRE       = 1;
    public static final int MIN_RANGE_YIELD              = 2;
    public static final int MAX_RANGE_YIELD              = 6;
    public static final int MIN_RANGE_ACRE_PRICE         =18;
    public static final int MAX_RANGE_ACRE_PRICE         = 26;

    //============================== Start Constants
    public static int START_GRAIN = 3000; // Bushels of grain in storage.
    public static int START_AREA = 1000; // Area of kingdom in acres.
    public static int START_YEAR = 0;  // Years since founding of kingdom.
    public static int START_HARVEST = 0;  // Last harvest in bushels.
    public static int START_PEASANTS = 100; // Number of peasants.
    public static final Scanner in = new Scanner(System.in);
}
