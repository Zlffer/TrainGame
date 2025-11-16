package actions;

import assets.*;
import ui.GameMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MMDownloadTrainAction implements Action {

    private Scanner userScanner;

    public MMDownloadTrainAction(Scanner scanner) {
        this.userScanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Train Downloading ---");
        System.out.println("Enter file name (example, 'train1.txt'): ");
        System.out.println("Example of data on file: ");
        System.out.println("L-Series (/M/L)");
        System.out.println("coorc");
        String filename = userScanner.nextLine();

        try {
            Scanner fileScanner = new Scanner(new File(filename));

            String locoModel = fileScanner.nextLine();
            String carriageCode = fileScanner.nextLine();

            fileScanner.close();

            Locomotive locomotive = parseLocomotive(locoModel);
            List<Carriage> carriages = parseCarriages(carriageCode);

            if (locomotive == null || carriages.isEmpty()) {
                System.out.println("Error: Wrong data.");
                System.out.println("Press Enter to return to the menu...");
                userScanner.nextLine();
                return;
            }

            int totalWeight = calculateTotalWeight(carriages);
            if (!locomotive.canHaul(totalWeight)) {
                System.out.println("Error: Locomotive from file (" + locoModel +
                        ") can not haul weight (" + totalWeight + ").");
                System.out.println("Press Enter to return to the menu...");
                userScanner.nextLine();
                return;
            }

            Train loadedTrain = new Train(locomotive, carriages);
            System.out.println("Train from file '" + filename + "' successfully loaded.");
            GameMenu gameMenu = new GameMenu(this.userScanner, loadedTrain);
            System.out.println("Press Enter to continue...");
            userScanner.nextLine();
            gameMenu.run();

        } catch (FileNotFoundException e) {
            System.out.println("Error: File '" + filename + "' not found.");
        } catch (Exception e) {
            System.out.println("Error: File damaged. " + e.getMessage());
        }
        System.out.println("Press Enter to return to the menu...");
        userScanner.nextLine();
    }

    private Locomotive parseLocomotive(String model) {
        switch (model) {
            case "L-Series":
                return new LocomotiveLight();
            case "M-Series":
                return new LocomotiveMedium();
            case "H-Series":
                return new LocomotiveHeavy();
            default:
                return null;
        }
    }

    private List<Carriage> parseCarriages(String code) {
        List<Carriage> carriages = new ArrayList<>();
        for (char symbol : code.toLowerCase().toCharArray()) {
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
            }
        }
        applyComfortBoosts(carriages);
        return carriages;
    }

    private void applyComfortBoosts(List<Carriage> carriages) {
        for (int i = 0; i < carriages.size(); i++) {
            if (carriages.get(i) instanceof CarriageRestaurant) continue;
            boolean boostApplied = false;
            if (i > 0 && carriages.get(i - 1) instanceof CarriageRestaurant) {
                carriages.get(i).applyComfortBoost();
                boostApplied = true;
            }
            if (!boostApplied && i < carriages.size() - 1 && carriages.get(i + 1) instanceof CarriageRestaurant) {
                carriages.get(i).applyComfortBoost();
            }
        }
    }

    private int calculateTotalWeight(List<Carriage> carriages) {
        int totalWeight = 0;
        for (Carriage carriage : carriages) {
            totalWeight += carriage.getWeight();
        }
        return totalWeight;
    }
}