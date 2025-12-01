
package assets;


public abstract class Carriage {

    protected int weight;
    protected int baseComfortLevel;
    protected int maxCapacity;
    protected int passengerCount;

    protected boolean isBoosted;

    public Carriage(int weight, int baseComfortLevel, int maxCapacity) {
        this.weight = weight;
        this.baseComfortLevel = baseComfortLevel;
        this.maxCapacity = maxCapacity;
        this.passengerCount = 0;
        this.isBoosted = false;
    }

    public void applyComfortBoost() {
        this.isBoosted = true;
    }

    public boolean getIsBoosted() {
        return isBoosted;
    }

    public int getWeight() {
        return weight;
    }
    public int getComfortLevel() {
        int currentComfort = baseComfortLevel - passengerCount;

        if (isBoosted) {
            return (int) (currentComfort * 1.5);
        } else {
            return currentComfort;
        }
    }
    public int getMaxCapacity() {
        return maxCapacity;
    }
    public int getPassengerCount() {
        return passengerCount;
    }
    public int getFreeSeats() {
        return maxCapacity - passengerCount;
    }


    public boolean addPassengers(int ammount) {
        if (passengerCount + ammount <= maxCapacity) {
            passengerCount += ammount;
            return true;
        } else {
            return false;
        }
    }
    public boolean removePassengers(int ammount) {
        if (passengerCount - ammount >= 0) {
            passengerCount -= ammount;
            return true;
        } else {
            return false;
        }
    }


}