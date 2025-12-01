
package actions;

import assets.Carriage;
import assets.Train;
import java.util.Scanner;
import java.util.InputMismatchException;

public class GMPassengersTransitAction implements Action{

    private Scanner scanner;
    private Train train;

    public GMPassengersTransitAction(Scanner scanner, Train train) {
        this.scanner = scanner;
        this.train = train;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Passengers Transfer ---");

        displayCarriageList();

        int trainSize = train.getCarriagesCount();
        if (trainSize < 2) {
            System.out.println("Unable to transfer passengers: train have less than 2 carriages.");
            return;
        }

        try {
            System.out.print("Transfer from carriage №: ");
            int fromIndex = scanner.nextInt() - 1;

            System.out.print("Transfer to carriage №: ");
            int toIndex = scanner.nextInt() - 1;

            System.out.print("Amount of passengers: ");
            int amount = scanner.nextInt();

            scanner.nextLine();

            String result = train.transferPassengers(fromIndex, toIndex, amount);

            System.out.println(result);

        } catch (InputMismatchException e) {
            System.out.println("Error: Not a number entered. Transfer canceled.");
            scanner.nextLine();
            System.out.println("Press Enter to continue..");
            scanner.nextLine();
        }

        System.out.println("Press Enter to continue..");
        scanner.nextLine();
    }

    private void displayCarriageList() {
        System.out.println("Current train info:");
        int i = 1;
        for (Carriage carriage : train.getCarriages()) {
            System.out.printf("  Carriage %d: %d/%d passengers\n",
                    i++,
                    carriage.getPassengerCount(),
                    carriage.getMaxCapacity()
            );
        }
    }

}
