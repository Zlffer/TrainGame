
package ui;

import assets.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrainBuilderMenu {

    private Scanner scanner;

    public TrainBuilderMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public Train buildTrain() {
        System.out.println("\n--- Train Creation ---");

        Locomotive selectedLocomotive = selectLocomotive();
        if (selectedLocomotive == null) {
            System.out.println("Train creation canceled.");
            System.out.println("Press Enter to continue..");
            scanner.nextLine();
            return null;
        }
        System.out.println("You selected: " + selectedLocomotive.getModel());
        System.out.println("Max. weight: " + selectedLocomotive.getMaxHaulingWeight());

        List<Carriage> selectedCarriages = selectCarriages();
        if (selectedCarriages.isEmpty()) {
            System.out.println("Train creation canceled (no carriages).");
            System.out.println("Press Enter to continue..");
            scanner.nextLine();
            return null;
        }

        int totalWeight = calculateTotalWeight(selectedCarriages);
        System.out.println("Total weight of carriages: " + totalWeight);
        System.out.println("Locomotive max haul weight: " + selectedLocomotive.getMaxHaulingWeight());

        if (!selectedLocomotive.canHaul(totalWeight)) {
            System.out.println("Error: Locomotive " + selectedLocomotive.getModel() +
                    " cannot haul " + totalWeight + " (max is " +
                    selectedLocomotive.getMaxHaulingWeight() + ").");
            System.out.println("Train creation canceled.");
            System.out.println("Press Enter to continue..");
            scanner.nextLine();
            return null;
        }

        System.out.println("Train successfully created!");
        System.out.println("Press Enter to continue..");
        scanner.nextLine();

        return new Train(selectedLocomotive, selectedCarriages);
    }

    private Locomotive selectLocomotive() {

        Locomotive light = new LocomotiveLight();
        Locomotive medium = new LocomotiveMedium();
        Locomotive heavy = new LocomotiveHeavy();

        System.out.println("\nSelect Locomotive:");
        System.out.println("1. " + light.getModel() + " | Max. haul weight: " + light.getMaxHaulingWeight());
        System.out.println("2. " + medium.getModel() + " | Max. haul weight: " + medium.getMaxHaulingWeight());
        System.out.println("3. " + heavy.getModel() + " | Max. haul weight: " + heavy.getMaxHaulingWeight());
        System.out.println("0. Cancel");
        System.out.print("Your choice (0-3): ");

        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                return light;
            case "2":
                return medium;
            case "3":
                return heavy;
            default:
                return null;
        }
    }

    private List<Carriage> selectCarriages() {
        List<Carriage> carriages = new ArrayList<>();

        Carriage compartment = new CarriageCompartment();
        Carriage couchette = new CarriageCouchette();
        Carriage restaurant = new CarriageRestaurant();

        System.out.println("\nEnter a code for adding carriages:");
        System.out.println(" c - Compartment    (Comfort: " + compartment.getComfortLevel() + ", Weight: " + compartment.getWeight() + ", Capacity: " + compartment.getMaxCapacity());
        System.out.println(" o - Couchette    (Comfort: " + couchette.getComfortLevel() + ", Weight: " + couchette.getWeight() + ", Capacity: " + couchette.getMaxCapacity());
        System.out.println(" r - Restaurant    (Comfort: " + restaurant.getComfortLevel() + ", Weight: " + couchette.getWeight() + ", Capacity: " + restaurant.getMaxCapacity());
        System.out.println("Example: 'coocr' (2 compartment, 2 Couchette, 1 Restaurant)");
        System.out.print("Your code: ");

        String code = scanner.nextLine().toLowerCase();

        for (char symbol : code.toCharArray()) {
            switch (symbol) {
                case 'c':
                    carriages.add(new CarriageCompartment());
                    break;
                case 'o':
                    carriages.add(new CarriageCouchette());
                    break;
                case 'r':
                    carriages.add(new CarriageRestaurant());
                    break;
                default:
                    System.out.println("Warning: symbol '" + symbol + "' ignored.");
                    break;
            }
        }

        for (int i = 0; i < carriages.size(); i++) {
            if (carriages.get(i) instanceof CarriageRestaurant) {
                continue;
            }

            boolean boostApplied = false;

            if (i > 0 && carriages.get(i - 1) instanceof CarriageRestaurant) {
                carriages.get(i).applyComfortBoost();
                boostApplied = true;
            }

            if (!boostApplied && i < carriages.size() - 1 && carriages.get(i + 1) instanceof CarriageRestaurant) {
                carriages.get(i).applyComfortBoost();
            }
        }

        int amount = 0;
        for (int i = 0; i < carriages.size(); i++) {
            if (carriages.get(i) instanceof CarriageRestaurant) {
                amount++;
            }
        }
        if (amount == carriages.size()) {
            System.out.println("Congratulations, your train can carry 0 passengers, but you have McDonald's on board, what were you thinking about?");
        }

        System.out.println("Carriages added: " + carriages.size());
        return carriages;
    }

    private int calculateTotalWeight(List<Carriage> carriages) {
        int totalWeight = 0;
        for (Carriage carriage : carriages) {
            totalWeight += carriage.getWeight();
        }
        return totalWeight;
    }

}