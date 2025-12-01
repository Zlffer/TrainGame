
package actions;

import assets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class GMShowCarriageInfoActionTest extends TrainTestHelper {

    static class SortTestCarriage extends TestPassengerCarriage {
        private final int fixedComfort;

        public SortTestCarriage(int comfort, int initialPassengers) {
            super(initialPassengers, BASE_CAPACITY);
            this.fixedComfort = comfort;
        }

        @Override
        public int getComfortLevel() {
            return fixedComfort;
        }
    }

    private Scanner mockScanner;
    private Train mockTrain;

    private TestCompartment c1;
    private TestCouchette c2;
    private TestRestaurant r1;

    @BeforeEach
    void setUp() {
        mockScanner = new Scanner(" \n");

        c1 = new TestCompartment(5);
        c2 = new TestCouchette(3);
        r1 = new TestRestaurant();

        List<Carriage> testCarriages = List.of(c1, c2, r1);
        mockTrain = new Train(new TestLocomotive(), testCarriages);
    }


    @Test
    void testExecuteSortsByComfortAndRunsWithoutException() {
        SortTestCarriage best = new SortTestCarriage(120, 5);
        SortTestCarriage mid = new SortTestCarriage(90, 3);
        SortTestCarriage worst = new SortTestCarriage(50, 1);
        SortTestCarriage tie = new SortTestCarriage(90, 1);

        List<Carriage> unsortedCarriages = List.of(worst, mid, best, tie);
        Train sortTrain = new Train(new TestLocomotive(), unsortedCarriages);

        GMShowCarriageInfoAction action = new GMShowCarriageInfoAction(mockScanner, sortTrain);

        assertDoesNotThrow(() -> action.execute(),
                "Execute should complete successfully and sort the list.");

    }


    @Test
    void testExecuteEmptyCarriageList() {
        Train emptyTrain = new Train(new TestLocomotive(), new ArrayList<>());
        GMShowCarriageInfoAction action = new GMShowCarriageInfoAction(mockScanner, emptyTrain);

        assertDoesNotThrow(() -> action.execute(),
                "problem with testExecuteEmptyCarriageList.");
    }


    @Test
    void testGetCarriageTypeCoversAllBranches() {
        class ActionWrapper extends GMShowCarriageInfoAction {
            public ActionWrapper(Scanner s, Train t) { super(s, t); }
            protected String getCarriageType(Carriage carriage) {
                if (carriage instanceof CarriageCompartment) {
                    return "Compartment";
                }
                return "Unknown";
            }
        }
        ActionWrapper wrapper = new ActionWrapper(mockScanner, mockTrain);

        assertEquals("Unknown", wrapper.getCarriageType(c1), "problem with testGetCarriageTypeCoversAllBranches.");
        assertEquals("Unknown", wrapper.getCarriageType(c2), "problem with testGetCarriageTypeCoversAllBranches.");
        assertEquals("Unknown", wrapper.getCarriageType(r1), "problem with testGetCarriageTypeCoversAllBranches.");
    }
}