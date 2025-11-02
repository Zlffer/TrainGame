
package assets;


public abstract class Carriage {

    protected double weight;
    protected int baseComfortLevel;
    protected int maxCapacity;

    protected int passengerCount;

    public Carriage() {
        this.passengerCount = 0;

    }

}