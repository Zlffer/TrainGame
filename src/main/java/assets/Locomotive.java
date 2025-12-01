
package assets;


public abstract class Locomotive {

    protected String model;
    protected int weight;
    protected int maxHaulingWeight;

    public Locomotive(String model, int weight, int maxHaulingWeight) {
        this.model = model;
        this.weight = weight;
        this.maxHaulingWeight = maxHaulingWeight;
    }


    public String getModel(){
        return model;
    }
    public int getWeight(){
        return weight;
    }
    public int getMaxHaulingWeight() {
        return maxHaulingWeight;
    }


    public boolean canHaul(int haulWeight) {
        return haulWeight <= maxHaulingWeight;
    }


}