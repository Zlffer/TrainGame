
package actions;

import assets.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GMCheckCarriagesAction implements Action {

    private Train train;
    private Random random;
    private Scanner scanner;

    public GMCheckCarriagesAction(Scanner scanner, Train train, Random random) {
        this.scanner = scanner;
        this.train = train;
        this.random = random;
    }

    public GMCheckCarriagesAction(Scanner scanner, Train train) {
        this(scanner, train, new Random());
    }


    @Override
    public void execute() {

        if (train.hasBeenChecked()) {
            System.out.println("\nThe check has already been carried out at this station.");
            System.out.println("Move on to the next one to perform a new check.");
            return;
        }

        System.out.println("\n--- Tickets Checking ---");
        System.out.println("Starting tickets checking...");

        List<Carriage> carriages = train.getCarriages();
        int totalStowawaysFound = 0;

        for (int i = 0; i < carriages.size(); i++) {
            Carriage carriage = carriages.get(i);

            if (carriage instanceof CarriageRestaurant || carriage.getPassengerCount() == 0) {
                continue;
            }

            if (random.nextInt(100) < 20) {

                int stowaways = (int) (carriage.getPassengerCount() * 0.1);
                if (stowaways == 0) {
                    stowaways = 1;
                }

                carriage.removePassengers(stowaways);
                train.addScore(stowaways * 10);

                System.out.println("(!) In carriage №" + (i + 1) + " find " + stowaways + " 'passengers with out tickets'!");
                totalStowawaysFound += stowaways;
            }
        }

        if (totalStowawaysFound == 0) {
            System.out.println("Inspection completed. All passengers with flowers. Well done!");
        } else {
            System.out.println("Verification completed. Total planted: " + totalStowawaysFound);
            System.out.println("Points received: " + (totalStowawaysFound * 10));
        }
        train.setHasBeenChecked(true);
        System.out.println("Press Enter to continue..");
        scanner.nextLine();
    }

}
