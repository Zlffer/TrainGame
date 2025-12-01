
package actions;

import assets.*;

import java.util.Random;

public abstract class TrainTestHelper {

    public static final int BASE_CAPACITY = 10;
    public static final int BASE_COMFORT = 50;
    public static final int BASE_WEIGHT = 1000;

    public static class TestLocomotive extends Locomotive {
        public TestLocomotive() {
            super("Test-L", 50000, 100000);
        }
    }

    public static class TestPassengerCarriage extends Carriage {
        public TestPassengerCarriage(int initialPassengers, int maxCapacity) {
            super(BASE_WEIGHT, BASE_COMFORT, maxCapacity);
            this.passengerCount = initialPassengers;
        }
    }

    public static class TestRestaurant extends Carriage {
        public TestRestaurant() {
            super(100, 0, 0);
        }
    }


    public static class TestCompartment extends TestPassengerCarriage {
        public TestCompartment(int initialPassengers) {
            super(initialPassengers, BASE_CAPACITY);
        }
    }

    public static class TestCouchette extends TestPassengerCarriage {
        public TestCouchette(int initialPassengers) {
            super(initialPassengers, BASE_CAPACITY);
        }
    }

    public static class MockRandom extends Random {
        private final int nextIntValue;
        private final double nextDoubleValue;

        public MockRandom(int nextIntValue, double nextDoubleValue) {
            this.nextIntValue = nextIntValue;
            this.nextDoubleValue = nextDoubleValue;
        }

        @Override
        public int nextInt(int bound) {
            return nextIntValue;
        }
        @Override
        public double nextDouble() {
            return nextDoubleValue;
        }

    }
}