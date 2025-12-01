
package assets;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Train {

    private static final Logger logger = LogManager.getLogger(Train.class);

    protected Locomotive locomotive;
    protected List<Carriage> carriages;
    protected int score;

    protected Stations[] route;
    protected int currentStationIndex;

    protected List<StationStats> gameStatistics;

    protected boolean hasBeenCheckedThisStation;

    public Train(Locomotive locomotive, List<Carriage> carriages) {
        this.locomotive = locomotive;
        this.carriages = carriages;
        this.score = 0;
        this.route = Stations.values();
        this.currentStationIndex = 0;
        this.gameStatistics = new ArrayList<>();
        this.hasBeenCheckedThisStation = false;
    }

    public Locomotive getLocomotive() {
        return locomotive;
    }
    public List<Carriage> getCarriages() {
        return carriages;
    }
    public int getCarriagesCount(){
        return  carriages.size();
    }

    public String transferPassengers(int fromIndex, int toIndex, int amount) {

        if (amount <= 0) {
            logger.warn("Transfer error: Amount must be positive. Attempted: " + amount);
            return "Error: Amount of passengers must be > 0.";
        }
        if (fromIndex == toIndex) {
            logger.warn("Transfer error: same from to index.");
            return "Error: You cannot transfer passengers in the same carriage.";
        }
        int size = carriages.size();
        if (fromIndex < 0 || fromIndex >= size || toIndex < 0 || toIndex >= size) {
            logger.warn("Transfer error: wrong carriage number.");
            return "Error: Wrong carriage number. Enter number from 1 to " + size + ".";
        }


        Carriage carriageFrom = carriages.get(fromIndex);
        Carriage carriageTo = carriages.get(toIndex);


        if (carriageFrom instanceof CarriageRestaurant || carriageTo instanceof CarriageRestaurant) {
            logger.warn("Transfer error: passengers cannot be moved into or with the dining car.");
            return "Error: Passengers cannot be moved into or with the dining car.";
        }
        if (carriageFrom instanceof CarriageCompartment && carriageTo instanceof CarriageCouchette) {
            logger.warn("Transfer error: it is not possible to transfer passengers from a Coupe (higher class) to a Place Card (lower class).");
            return "Error: It is not possible to transfer passengers from a Coupe (higher class) to a Place Card (lower class).";
        }
        if (carriageFrom instanceof CarriageCouchette && carriageTo instanceof CarriageCompartment) {
            logger.warn("Transfer error: it is not possible to transfer passengers from a Place (lower class) to a Coupe Card (higher class).");
            return "Error: It is not possible to transfer passengers from a Place (lower class) to a Coupe Card (higher class).";
        }


        if (carriageFrom.getPassengerCount() < amount) {
            logger.warn("Transfer error: Not enough seats in carriage " + (fromIndex + 1) +
                    ". Free seats: " + carriageTo.getFreeSeats() + ", Attempted: " + amount);
            return "Error: Carriage " + (fromIndex + 1) + " do not have that many passengers (" +
                    carriageFrom.getPassengerCount() + ").";
        }
        if (carriageTo.getFreeSeats() < amount) {
            logger.warn("Transfer error: Not enough seats in carriage " + (toIndex + 1) +
                    ". Free seats: " + carriageTo.getFreeSeats() + ", Attempted: " + amount);
            return "Error: Carriage " + (toIndex + 1) + " do not have that many seats (" +
                    carriageTo.getFreeSeats() + ").";
        }

        carriageFrom.removePassengers(amount);
        carriageTo.addPassengers(amount);

        logger.info(String.format("Transfer SUCCESS: %d passengers moved from %d to %d",amount, (fromIndex + 1), (toIndex + 1)));
        return "Success: " + amount + " passengers transfer from carriage " +
                (fromIndex + 1) + " to carriage " + (toIndex + 1) + ".";
    }

    public void addScore(int points) {
        if (points > 0) {
            this.score += points;
        }
    }
    public int getScore() {
        return score;
    }

    public Stations getCurrentStation() {
        return this.route[this.currentStationIndex];
    }
    public int getCurrentStationIndex() {
        return this.currentStationIndex;
    }
    public boolean isAtLastStation() {
        return this.currentStationIndex == this.route.length - 1;
    }
    public Stations moveToNextStation() {
        if (!isAtLastStation()) {
            this.currentStationIndex++;
            this.hasBeenCheckedThisStation = false;
        }
        return getCurrentStation();
    }


    public List<StationStats> getGameStatistics() {
        return this.gameStatistics;
    }
    public double getAverageOccupancy() {
        int totalCapacity = 0;
        int totalPassengers = 0;

        for (Carriage carriage : carriages) {
            if (carriage instanceof CarriageRestaurant) {
                continue;
            }
            totalCapacity += carriage.getMaxCapacity();
            totalPassengers += carriage.getPassengerCount();
        }

        if (totalCapacity == 0) {
            return 0.0;
        }

        return (double) totalPassengers * 100.0 / totalCapacity;
    }
    public double getAverageComfort() {
        int comfortSum = 0;
        int passengerCarriageCount = 0;

        for (Carriage carriage : carriages) {
            if (carriage instanceof CarriageRestaurant) {
                continue;
            }
            comfortSum += carriage.getComfortLevel();
            passengerCarriageCount++;
        }

        if (passengerCarriageCount == 0) {
            return 0.0;
        }

        return (double) comfortSum / passengerCarriageCount;
    }
    public void recordStatistics(int passengersBoarded, int passengersAlighted) {

        String stationName = getCurrentStation().toString();
        double avgOccupancy = getAverageOccupancy();
        double avgComfort = getAverageComfort();

        StationStats snapshot = new StationStats(
                stationName,
                passengersBoarded,
                passengersAlighted,
                avgOccupancy,
                avgComfort
        );

        this.gameStatistics.add(snapshot);
    }

    public boolean hasBeenChecked() {
        return this.hasBeenCheckedThisStation;
    }
    public void setHasBeenChecked(boolean status) {
        this.hasBeenCheckedThisStation = status;
    }

}