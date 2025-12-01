
package actions;

import assets.Carriage;
import assets.Train;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;

public class GMPassengersFindAction implements Action {

    private Train train;
    private Scanner scanner;

    public GMPassengersFindAction(Scanner scanner, Train train) {
        this.scanner = scanner;
        this.train = train;
    }

    @Override
    public void execute() {
        System.out.println("\n--- Passengers Finder ---");

        try {
            System.out.print("Number from: ");
            int fromNumber = scanner.nextInt();

            System.out.print("Number to: ");
            int toNumber = scanner.nextInt();

            CarriageFinder(fromNumber,toNumber);

        } catch (InputMismatchException e) {
            System.out.println("Error: Not a number entered. Finder canceled.");
            scanner.nextLine();
            System.out.println("Press Enter to continue..");
            scanner.nextLine();
        }
        System.out.println("Press Enter to continue..");
        scanner.nextLine();
    }

    private void CarriageFinder(int fromNumber, int toNumber) {

        if (fromNumber > toNumber) {
            int temp = fromNumber;
            fromNumber = toNumber;
            toNumber = temp;
        }

        System.out.println("Searching result (From: " + fromNumber + " to " + toNumber + " passengers):");

        boolean foundAny = false;
        List<Carriage> carriages = train.getCarriages();

        for (int i = 0; i < carriages.size(); i++) {

            Carriage carriage = carriages.get(i);
            int passengers = carriage.getPassengerCount();

            if (passengers >= fromNumber && passengers <= toNumber) {

                System.out.printf("  Carriage %d: %d/%d passengers\n",
                        (i + 1),
                        passengers,
                        carriage.getMaxCapacity()
                );
                foundAny = true;
            }
        }

        if (!foundAny) {
            System.out.println("No carriages matching the criteria were found.");
        }
}
}

