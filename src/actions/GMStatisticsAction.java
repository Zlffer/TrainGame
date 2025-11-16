package actions;

import assets.StationStats;
import assets.Train;
import java.util.List;
import java.util.Scanner;

public class GMStatisticsAction implements Action {

    private Train train;
    private Scanner scanner;

    public GMStatisticsAction(Scanner scanner, Train train) {
        this.scanner = scanner;
        this.train = train;
    }

    @Override
    public void execute() {

        System.out.println("\n--- CURRENT STATISTICS ---");
        System.out.printf("Current station: %s\n", train.getCurrentStation().toString());
        System.out.printf("Total score: %d\n", train.getScore());
        System.out.printf("Average occupancy: %.1f%%\n", train.getAverageOccupancy());
        System.out.printf("Average comfort: %.1f\n", train.getAverageComfort());

        List<StationStats> statsHistory = train.getGameStatistics();

        if (statsHistory.isEmpty()) {
            System.out.println("There is no history of the stations yet.");
            return;
        }

        System.out.println("\n--- STATION HISTORY ---");
        // Друкуємо заголовок таблиці
        System.out.printf("%-25s | %-7s | %-8s | %-11s | %-8s\n",
                "Station", "Enter", "Exit", "Occupancy. (%)", "Comfort");
        System.out.println(String.format("%-25s | %-7s | %-8s | %-11s | %-8s",
                "-------------------------", "-------", "--------", "-----------", "--------"));

        for (StationStats stats : statsHistory) {
            System.out.printf("%-25s | %-7d | %-8d | %-11.1f | %-8.1f\n",
                    stats.stationName,
                    stats.passengersBoarded,
                    stats.passengersAlighted,
                    stats.averageOccupancy,
                    stats.averageComfort
            );
        }
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Press Enter to continue..");
        scanner.nextLine();
    }

}