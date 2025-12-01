
package actions;

import assets.*;

import java.util.Random;
import java.util.Scanner;

public class GMNextStationAction implements Action {

    private Scanner scanner;
    private Train train;
    private final Random random;

    public GMNextStationAction(Scanner scanner, Train train, Random random) {
        this.scanner = scanner;
        this.train = train;
        this.random = random;
    }

    public GMNextStationAction(Scanner scanner, Train train) {
        this(scanner, train, new Random());
    }

    @Override
    public void execute() {

        if (train.isAtLastStation()) {
            System.out.println("\n--- END OF ROUTE ---");
            System.out.println("The train is already at the final station: " +
                    train.getCurrentStation().toString());
            System.out.println("You can exit the game (point 7).");
            return;
        }

        System.out.println("\nThe train is leaving the station " +
                train.getCurrentStation().toString() + "...");

        train.moveToNextStation();
        simulateStationArrival(train);

        if (train.isAtLastStation()) {
            System.out.println("(!) CONGRATULATIONS! You have arrived at the final station!");
        }

        System.out.println("Press Enter to continue..");
        scanner.nextLine();
    }


    private void simulateStationArrival(Train train) {
        System.out.println("--- Arrival at the station: " + train.getCurrentStation().toString() + " ---");

        int passengersAlighted = alightPassengers(train);
        System.out.println("Passengers got out: " + passengersAlighted);

        int passengersBoarded = boardPassengers(train, 1.0);
        System.out.println("Passengers have arrived: " + passengersBoarded);

        System.out.println("----------------------------------------");

        train.recordStatistics(passengersBoarded, passengersAlighted);
    }
    private int alightPassengers(Train train) {
        int totalAlighted = 0;
        for (Carriage carriage : train.getCarriages()) {
            if (carriage instanceof CarriageRestaurant) {
                continue;
            }
            int passengers = carriage.getPassengerCount();
            if (passengers > 0) {
                double percentToAlight = 0.1 + (0.5 - 0.1) * random.nextDouble();
                int numToAlight = (int) (passengers * percentToAlight);

                if (numToAlight > 0) {
                    carriage.removePassengers(numToAlight);
                    totalAlighted += numToAlight;
                }
            }
        }
        return totalAlighted;
    }

    private int boardPassengers(Train train, double multiplier) {

        int totalPassengersBoarded = 0;

        for (Carriage carriage : train.getCarriages()) {

            if (carriage instanceof CarriageRestaurant) {
                continue;
            }

            int freeSeats = carriage.getFreeSeats();
            if (freeSeats == 0) {
                continue;
            }

            int passengersWantingToBoard = 0;
            if(carriage instanceof CarriageCompartment){
                passengersWantingToBoard = (int) (random.nextInt(41) * multiplier);
            } else{
                passengersWantingToBoard = (int) (random.nextInt(61) * multiplier);
            }

            if (passengersWantingToBoard == 0) {
                continue;
            }

            int numToBoard = Math.min(passengersWantingToBoard, freeSeats);

            if (numToBoard > 0) {
                carriage.addPassengers(numToBoard);
                totalPassengersBoarded += numToBoard;
            }
        }
        return totalPassengersBoarded;
    }

}