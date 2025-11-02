
package assets;


public abstract class Locomotive {

    protected String model;
    protected double weight;

    protected double maxHaulingWeight;

    public Locomotive() {

    }

    public double getMaxHaulingWeight() {
        return this.maxHaulingWeight;
    }
}