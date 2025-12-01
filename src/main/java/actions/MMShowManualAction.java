
package actions;

import assets.*;
import java.util.Scanner;

public class MMShowManualAction implements Action {

    Stations[] allStations = Stations.values();
    String firstStation = allStations[0].toString();
    String lastStation = allStations[allStations.length - 1].toString();

    Locomotive light = new LocomotiveLight();
    Locomotive medium = new LocomotiveMedium();
    Locomotive heavy = new LocomotiveHeavy();

    Carriage compartment = new CarriageCompartment();
    Carriage couchette = new CarriageCouchette();
    Carriage restaurant = new CarriageRestaurant();

    private Scanner scanner;

    public MMShowManualAction(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\n=============================================");
        System.out.println("            GAME MANUAL (TRAIN SIMULATOR)        ");
        System.out.println("=============================================");

        System.out.println("\n== 1. YOUR GOAL ==");
        System.out.println("You are a train driver. Your goal is to create a train and guide");
        System.out.println("it along the entire route from '" + firstStation + "' to '" + lastStation + "'.");
        System.out.println("At each station, passengers will board and disembark.");
        System.out.println("Your success is measured by the overall comfort level and the");
        System.out.println("number of passengers transported.");

        System.out.println("\n== 2. LOCOMOTIVES ==");
        System.out.println("The locomotive is the 'heart' of your train. Its main characteristic is");
        System.out.println("Max Haul Weight. The total weight of all your carriages");
        System.out.println("MUST NOT exceed this value.");
        System.out.println();
        System.out.println(" * 1. " + light.getModel() + "  | Max Haul: " + light.getMaxHaulingWeight() + " | Weight: " + light.getWeight());
        System.out.println(" * 2. " + medium.getModel() + "  | Max Haul: " + medium.getMaxHaulingWeight() + " | Weight: " + medium.getWeight());
        System.out.println(" * 3. " + heavy.getModel() + "  | Max Haul: " + heavy.getMaxHaulingWeight() + " | Weight: " + heavy.getWeight());

        System.out.println("\n== 3. CARRIAGES ==");
        System.out.println("You build your train by entering a symbol code (e.g., 'coocr').");
        System.out.println();
        System.out.println(" * Compartment ('c'):");
        System.out.println("   - Base Comfort: " + compartment.getComfortLevel());
        System.out.println("   - Capacity: " + compartment.getMaxCapacity() + " passengers");
        System.out.println("   - Weight: " + compartment.getWeight());
        System.out.println();
        System.out.println(" * Couchette ('o'):");
        System.out.println("   - Base Comfort: " + couchette.getComfortLevel());
        System.out.println("   - Capacity: " + couchette.getMaxCapacity() + " passengers");
        System.out.println("   - Weight: " + couchette.getWeight());
        System.out.println();
        System.out.println(" * Restaurant ('r'):");
        System.out.println("   - Base Comfort: " + restaurant.getComfortLevel());
        System.out.println("   - Capacity: " + restaurant.getMaxCapacity() + " passengers");
        System.out.println("   - Weight: " + restaurant.getWeight());

        System.out.println("\n== 4. COMFORT MECHANICS ==");
        System.out.println("Comfort is your most important metric.");
        System.out.println();
        System.out.println(" 1. BASE CALCULATION:");
        System.out.println("    Current comfort in a carriage is calculated by the formula:");
        System.out.println("    [Base Comfort] - [Current Passenger Count]");
        System.out.println("    (Yes, the more people, the lower the comfort).");
        System.out.println();
        System.out.println(" 2. RESTAURANT BOOST (x1.5):");
        System.out.println("    If a passenger carriage (Compartment or Couchette) is");
        System.out.println("    DIRECTLY adjacent to a Restaurant Car (left or right),");
        System.out.println("    its **current comfort is multiplied by 1.5**!");
        System.out.println("    This allows you to carry more people while maintaining comfort.");

        System.out.println("\n== 5. GAMEPLAY ==");
        System.out.println(" * 1. Next Station: Moves your train to the next station.");
        System.out.println(" * 2. Station Info: Shows your route.");
        System.out.println(" * 3. Carriage Info: Shows the status of each carriage.");
        System.out.println(" * 4. Passengers Transit: Lets you move passengers between carriages.");
        System.out.println(" * 5. Check Carriages: Allows you to check for tickets.");
        System.out.println(" * 6. Find Carriages: Helps find carriages by specific criteria.");
        System.out.println("\n=============================================");

        System.out.println("Press Enter to return to the menu...");
        scanner.nextLine();
    }

}