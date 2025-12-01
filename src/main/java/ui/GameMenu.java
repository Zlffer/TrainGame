
package ui;

import actions.*;
import java.util.Scanner;
import assets.*;
import java.util.Random;


public class GameMenu {

    private Scanner scanner;
    private boolean isRunning;
    private Train train;

    private static final Random random = new Random();

    public GameMenu(Scanner scanner, Train train) {
        this.scanner = scanner;
        this.train = train;
        this.isRunning = true;
    }

    public void run() {

        System.out.println("\nThe train is ready to depart.");
        System.out.println("Starting station: " + train.getCurrentStation().toString());
        boardInitialPassengers(train);

        System.out.println("\nTrain is ready. Entering Game Menu...");

        while (isRunning) {
            displayGameMenu();
            String input = scanner.nextLine();
            handleGameMenuInput(input);
        }

        System.out.println("Exiting Game Menu, returning to Main Menu...");
    }

    private void displayGameMenu() {
        System.out.println("\n--- Game Menu (Train Control) ---");
        System.out.println("1. Next station");
        System.out.println("2. Statistics");
        System.out.println("3. Carriage info (sort by comfort)");
        System.out.println("4. Passengers transit");
        System.out.println("5. Check carriages (tickets)");
        System.out.println("6. Find carriages (by passenger range)");
        System.out.println("7. Return to Main Menu");
        System.out.print("Enter your choice: ");
    }

    private void handleGameMenuInput(String input) {
        switch (input) {
            case "1":
                new GMNextStationAction(scanner, train).execute();
                break;
            case "2":
                new GMStatisticsAction(scanner, train).execute();
                break;
            case "3":
                new GMShowCarriageInfoAction(scanner, train).execute();
                break;
            case "4":
                new GMPassengersTransitAction(scanner, train).execute();
                break;
            case "5":
                new GMCheckCarriagesAction(scanner, train).execute();
                break;
            case "6":
                new GMPassengersFindAction(scanner, train).execute();
                break;
            case "7":
                this.isRunning = false;
                break;
            default:
                System.out.println("Wrong choice >:( ...");
        }
    }

    private void boardInitialPassengers(Train train) {
        System.out.println("Passengers take their seats...");
        int passengersBoarded = boardPassengers(train, 2.0);
        System.out.println("Initial boarding completed. Passengers on board: " + passengersBoarded);
        train.recordStatistics(passengersBoarded, 0);
    }
    private int boardPassengers(Train train, double multiplier) {
        int totalPassengersBoarded = 0;
        for (Carriage carriage : train.getCarriages()) {
            if (carriage instanceof CarriageRestaurant) continue;
            int freeSeats = carriage.getFreeSeats();
            if (freeSeats == 0) continue;

            int passengersWantingToBoard = 0;
            if (carriage instanceof CarriageCompartment) {
                passengersWantingToBoard = (int) (random.nextInt(41) * multiplier);
            } else {
                passengersWantingToBoard = (int) (random.nextInt(61) * multiplier);
            }
            if (passengersWantingToBoard == 0) continue;

            int numToBoard = Math.min(passengersWantingToBoard, freeSeats);
            if (numToBoard > 0) {
                carriage.addPassengers(numToBoard);
                totalPassengersBoarded += numToBoard;
            }
        }
        return totalPassengersBoarded;
    }

}