
package assets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class TestableCarriage extends Carriage {

    public static final int INITIAL_WEIGHT = 1000;
    public static final int INITIAL_COMFORT = 50;
    public static final int MAX_CAPACITY = 10;

    public TestableCarriage() {
        super(INITIAL_WEIGHT, INITIAL_COMFORT, MAX_CAPACITY);
    }
}

class CarriageTest {

    private TestableCarriage carriage;

    @BeforeEach
    void setUp() {
        carriage = new TestableCarriage();
    }

    @Test
    void testInitializationAndGetters() {
        assertEquals(TestableCarriage.INITIAL_WEIGHT, carriage.getWeight(), "problem with testInitializationAndGetters wrong weight.");
        assertEquals(TestableCarriage.MAX_CAPACITY, carriage.getMaxCapacity(), "problem with testInitializationAndGetters wrong capacity.");
    }

    @Test
    void testAddPassengersSuccessfully() {
        assertTrue(carriage.addPassengers(5), "problem with testAddPassengersSuccessfully.");
        assertEquals(5, carriage.getPassengerCount());
    }

    @Test
    void testAddPassengersOverflow() {
        carriage.addPassengers(5);
        assertFalse(carriage.addPassengers(6), "problem with testAddPassengersOverflow.");
        assertEquals(5, carriage.getPassengerCount());
    }

    @Test
    void testRemovePassengersUnderflow() {
        carriage.addPassengers(5);
        assertFalse(carriage.removePassengers(6), "problem with testRemovePassengersUnderflow.");
        assertEquals(5, carriage.getPassengerCount());
    }

    @Test
    void testRemovePassengersZeroAmount() {
        carriage.addPassengers(5);
        assertTrue(carriage.removePassengers(0), "problem with testRemovePassengersZeroAmount.");
        assertEquals(5, carriage.getPassengerCount());
    }

    @Test
    void testGetFreeSeats() {
        carriage.addPassengers(5);
        assertEquals(5, carriage.getFreeSeats());
        carriage.removePassengers(2);
        assertEquals(7, carriage.getFreeSeats());
    }



    @Test
    void testComfortLevelNoPassengersNoBoost() {
        // Comfort = 50 - 0 = 50
        assertEquals(TestableCarriage.INITIAL_COMFORT, carriage.getComfortLevel(), "problem with testComfortLevelNoPassengersNoBoost.");
    }

    @Test
    void testComfortLevelWithPassengers() {
        carriage.addPassengers(5);
        // Comfort = 50 - 5 = 45
        assertEquals(45, carriage.getComfortLevel());
    }

    @Test
    void testComfortBoostNoPassengers() {
        carriage.applyComfortBoost();
        // Comfort = (50 - 0) * 1.5 = 75
        assertEquals(75, carriage.getComfortLevel(), "problem with testComfortBoostNoPassengers.");
    }

    @Test
    void testComfortBoostWithPassengers() {
        carriage.addPassengers(10);
        // Comfort = 50 - 10 = 40
        carriage.applyComfortBoost();
        // Comfort = 40 * 1.5 = 60
        assertEquals(60, carriage.getComfortLevel());
    }
}