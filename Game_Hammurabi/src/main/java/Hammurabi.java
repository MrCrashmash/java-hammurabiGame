package main.java;
import static main.java.variables.GameVariables.*;
import static main.java.constants.GameConstants.*;
import main.java.logic.Logic;
import main.java.GUI.*;

public class Hammurabi {

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.startGUI();
        Logic.starter();
    }
}