
package assets;

public class StationStats {

    public final String stationName;
    public final int passengersBoarded;
    public final int passengersAlighted;
    public final double averageOccupancy;
    public final double averageComfort;

    public StationStats(String stationName, int passengersBoarded, int passengersAlighted, double averageOccupancy, double averageComfort) {
        this.stationName = stationName;
        this.passengersBoarded = passengersBoarded;
        this.passengersAlighted = passengersAlighted;
        this.averageOccupancy = averageOccupancy;
        this.averageComfort = averageComfort;
    }
}