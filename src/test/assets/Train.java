
package assets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

interface TrainTestConstants {
    int BASE_CAPACITY = 10;
    int BASE_COMFORT = 50;
    int BASE_WEIGHT = 1000;
}

class TestLocomotive extends Locomotive {
    public TestLocomotive() {
        super("Test-L", 1000, 50000);
    }
}

class TestPassengerCarriage extends Carriage implements TrainTestConstants {
    public TestPassengerCarriage(int initialPassengers, int maxCapacity) {
        super(BASE_WEIGHT, BASE_COMFORT, maxCapacity);
        this.passengerCount = initialPassengers;
    }
}

class TestRestaurant extends Carriage {
    public TestRestaurant() {
        super(100, 0, 0);
    }
}

class TestCompartment extends TestPassengerCarriage {
    public TestCompartment(int initialPassengers) {
        super(initialPassengers, BASE_CAPACITY);
    }
}

class TestCouchette extends TestPassengerCarriage {
    public TestCouchette(int initialPassengers) {
        super(initialPassengers, BASE_CAPACITY);
    }
}


class TrainTest implements TrainTestConstants {

    private Train train;
    private List<Carriage> testCarriages;
    private TestCompartment c1;
    private TestCouchette c2;
    private TestCouchette c3;
    private TestRestaurant r4;

    @BeforeEach
    void setUp() {
        // c1: 5/10 capacity (Ind 0)
        // c2: 3/10 capacity (Ind 1)
        // с3: 0/10 capacity (ind 2)
        // r4: 0/00 capacity (ind 3)
        c1 = new TestCompartment(5);
        c2 = new TestCouchette(3);
        c3 = new TestCouchette(0);
        r4 = new TestRestaurant();

        testCarriages = new ArrayList<>();
        testCarriages.add(c1);
        testCarriages.add(c2);
        testCarriages.add(c3);
        testCarriages.add(r4);

        train = new Train(new TestLocomotive(), testCarriages);

        train.addScore(-train.getScore()); //score = 0
    }

    @Test
    void testTransferSuccessCouchetteToCompartment() {
        String result = train.transferPassengers(1, 0, 2);
        assertEquals("Success: 2 passengers transfer from carriage 2 to carriage 1.", result, "problem with testTransferSuccessCouchetteToCompartment.");
        // 5 + 2 - 7
        // 3 - 2 = 1
        assertEquals(7, c1.getPassengerCount(), "problem with testTransferSuccessCouchetteToCompartment wrong amount to.");
        assertEquals(1, c2.getPassengerCount(), "problem with testTransferSuccessCouchetteToCompartment wrong amount from.");
    }

    @Test
    void testTransferSameCarriageError() {
        String result = train.transferPassengers(0, 0, 1);
        assertEquals("Error: You cannot transfer passengers in the same carriage.", result);
    }

        @Test
        void testTransferWrongIndexError() {
            String result = train.transferPassengers(0, 5, 1);
            assertEquals("Error: Wrong carriage number. Enter number from 1 to " + train.getCarriagesCount() + ".", result);
        }

    @Test
    void testTransferZeroAmountError() {
        String result = train.transferPassengers(0, 1, 0);
        assertEquals("Error: Amount of passengers must be > 0.", result);
    }


    @Test
    void testTransferNotEnoughPassengersError() {
        String result = train.transferPassengers(1, 0, 4);
        assertEquals("Error: Carriage " + (2) + " do not have that many passengers (3).", result);
        // "Error: Carriage " + (fromIndex + 1) + " do not have that many passengers (" +carriageFrom.getPassengerCount() + ")."
        assertEquals(3, c2.getPassengerCount(), "problem with testTransferNotEnoughPassengersError.");
    }

    @Test
    void testTransferNotEnoughSeatsError() {
        c2.addPassengers(7);
        train.transferPassengers(1, 2, 10);
        c2.addPassengers(5);
        String result = train.transferPassengers(1, 2, 2);
        assertEquals("Error: Carriage " + (3) + " do not have that many seats (" + 0 + ").", result);
        // "Error: Carriage " + (toIndex + 1) + " do not have that many seats (" + carriageTo.getFreeSeats() + ")."
    }


    @Test
    void testScoreAddition() {
        train.addScore(10);
        assertEquals(10, train.getScore());
        train.addScore(5);
        assertEquals(15, train.getScore());
        train.addScore(-5);
        assertEquals(15, train.getScore());
    }

    @Test
    void testNavigation() {
        assertEquals(Stations.values()[0], train.getCurrentStation(), "problem with testNavigation wrong start station.");
        assertFalse(train.isAtLastStation(), "problem with testNavigation not on last station.");

        Stations next = train.moveToNextStation();
        assertEquals(1, train.getCurrentStationIndex());
        assertEquals(Stations.values()[1], next);

        assertFalse(train.hasBeenChecked(), "problem with testNavigation check status true.");
        train.setHasBeenChecked(true);
        assertTrue(train.hasBeenChecked(), "problem with testNavigation check status false.");
    }

    @Test
    void testNavigationAtLastStation() {
        for(int i = 0; i < 19; i++){ // amount of stations = 20
            train.moveToNextStation();
        }

        assertTrue(train.isAtLastStation(), "problem with testNavigationAtLastStation must be last station.");

        Stations last = train.moveToNextStation();
        assertEquals(19, train.getCurrentStationIndex(), "problem with testNavigationAtLastStation wrong index.");
        assertEquals(Stations.values()[19], last, "problem with testNavigationAtLastStation wrong index.");
    }


    @Test
    void testAverageOccupancyCalculation() {
        // 10 + 10 + 10 = 30 amount of passengers carriages
        // 5 + 3 = 8 amount of passengers
        // (8 / 30) * 100% - occupancy
        double expected = 8.0 * 100.0 / 30.0;
        assertEquals(expected, train.getAverageOccupancy(), 0.001, "problem with testAverageOccupancyCalculation.");
    }

    @Test
    void testAverageOccupancyZeroCapacity() {
        train = new Train(new TestLocomotive(), List.of(new TestRestaurant()));
        assertEquals(0.0, train.getAverageOccupancy(), 0.001, "problem with testAverageOccupancyZeroCapacity.");
    }

    @Test
    void testAverageComfortCalculation() {
        double expected2 = (c1.getComfortLevel() + c2.getComfortLevel() + c3.getComfortLevel() + r4.getComfortLevel()) / 4.0;
        assertEquals(expected2, train.getAverageComfort(), 0.001, "problem with testAverageComfortCalculation.");
    }

    @Test
    void testAverageComfortZeroCarriages() {
        train = new Train(new TestLocomotive(), List.of(new TestRestaurant()));
        assertEquals(0.0, train.getAverageComfort(), 0.001, "problem with testAverageComfortZeroCarriages.");
    }

    @Test
    void testGetLocomotive() {
        assertNotNull(train.getLocomotive(), "problem with testGetLocomotive.");

        assertEquals(train.getLocomotive().getModel(), train.getLocomotive().getModel(), "problem with testGetLocomotive wrong model.");
    }

    @Test
    void testTrainTransferWrongIndexError() {
        int size = train.getCarriagesCount();

        String result1 = train.transferPassengers(size, 0, 1);
        String expectedMessage = "Error: Wrong carriage number. Enter number from 1 to " + size + ".";

        assertEquals(expectedMessage, result1, "problem with testTrainTransferWrongIndexError wrong model");

        String result2 = train.transferPassengers(0, -1, 1);
        assertEquals(expectedMessage, result2, "problem with testTrainTransferWrongIndexError too small");
    }

    @Test
    void testAveragesZeroCarriages() {
        Train restaurantOnlyTrain = new Train(new TestLocomotive(), List.of(new TestRestaurant()));

        assertEquals(0.0, restaurantOnlyTrain.getAverageOccupancy(), 0.001,
                "problem with testTrainTransferWrongIndexError wrong occupancy.");

        assertEquals(0.0, restaurantOnlyTrain.getAverageComfort(), 0.001,
                "problem with testTrainTransferWrongIndexError wrong comfort");

        Train emptyTrain = new Train(new TestLocomotive(), new ArrayList<>());
        assertEquals(0.0, emptyTrain.getAverageOccupancy(), 0.001,
                "problem with testTrainTransferWrongIndexError wrong occupancy");
    }
}