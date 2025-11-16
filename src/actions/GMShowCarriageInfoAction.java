
package actions;

import assets.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Scanner;

public class GMShowCarriageInfoAction implements Action {

    private Train train;
    private Scanner scanner;

    public GMShowCarriageInfoAction(Scanner scanner, Train train) {
        this.scanner = scanner;
        this.train = train;
    }

    @Override
    public void execute() {

        List<Carriage> originalCarriages = train.getCarriages();

        if (originalCarriages.isEmpty()) {
            System.out.println("No carriages :( ...");
            return;
        }

        List<Carriage> sortedList = new ArrayList<>(originalCarriages);

        sortedList.sort(Comparator.comparingInt(Carriage::getComfortLevel).reversed());

        System.out.println("\n--- Carriages info (sorted by comfort) ---");

        int carriageNumber = 1;
        for (Carriage carriage : sortedList) {
            String type = getCarriageType(carriage);

            System.out.printf(
                    "%d. [%-12s] | Comfort: %-3d | Passengers: %d/%d\n",
                    carriageNumber++,
                    type,
                    carriage.getComfortLevel(),
                    carriage.getPassengerCount(),
                    carriage.getMaxCapacity()
            );
        }
        System.out.println("-----------------------------------------------------");

        System.out.println("Press Enter to continue..");
        scanner.nextLine();
    }


    private String getCarriageType(Carriage carriage) {
        if (carriage instanceof CarriageCompartment) {
            return "Compartment";
        }
        if (carriage instanceof CarriageCouchette) {
            return "Couchette";
        }
        if (carriage instanceof CarriageRestaurant) {
            return "Restaurant";
        }
        return "Unknown";
    }

}
