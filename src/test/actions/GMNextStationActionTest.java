
package actions;

import assets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;


class GMNextStationActionTest extends TrainTestHelper {

    private Scanner mockScanner;
    private Train mockTrain;
    private int initialStationIndex;
    private TestCompartment c1;
    private TestCouchette c2;

    @BeforeEach
    void setUp() {
        mockScanner = new Scanner("\n");

        c1 = new TestCompartment(10);
        c2 = new TestCouchette(5);

        List<Carriage> testCarriages = List.of(c1, c2);
        mockTrain = new Train(new TestLocomotive(), testCarriages);

        initialStationIndex = mockTrain.getCurrentStationIndex();
    }

    @Test
    void testExecuteAtLastStation() {
        for(int i = 0; i < 19; i++){ // amount of stations = 20
            mockTrain.moveToNextStation();
        }

        GMNextStationAction action = new GMNextStationAction(mockScanner, mockTrain);

        action.execute();

        assertEquals(19, mockTrain.getCurrentStationIndex(), "problem with testExecuteAtLastStation wrong index.");
        assertTrue(mockTrain.getGameStatistics().isEmpty(), "problem with testExecuteAtLastStation.");
    }

    @Test
    void testSimulateStationArrivalWithPassengers() {
        Random mockRandom = new MockRandom(20, 0.5);

        TestCompartment c_comp = (TestCompartment) mockTrain.getCarriages().get(0);
        TestCouchette c_couch = (TestCouchette) mockTrain.getCarriages().get(1);

        GMNextStationAction action = new GMNextStationAction(mockScanner, mockTrain, mockRandom);

        action.execute();

        // (Board Passengers):
        // c_comp (7 pas., 3 empty): passengersWantingToBoard = 20. numToBoard = 3. : 7 + 3 = 10
        // c_couch (4 pas., 6 empty): passengersWantingToBoard = 30. numToBoard = 6. : 4 + 6 = 10
        assertEquals(10, c_comp.getPassengerCount(), "problem with testSimulateStationArrivalWithPassengers value must be 10.");
        assertEquals(10, c_couch.getPassengerCount(), "problem with testSimulateStationArrivalWithPassengers value must be 10 too.");

        assertEquals(initialStationIndex + 1, mockTrain.getCurrentStationIndex(), "problem with testSimulateStationArrivalWithPassengers wrong index.");
        assertTrue(mockTrain.getGameStatistics().size() > 0, "problem with testSimulateStationArrivalWithPassengers value must be > 0.");
    }

    @Test
    void testSimulateStationArrivalZeroChange() {
        Random mockRandom = new MockRandom(0, 0.0);

        new GMNextStationAction(mockScanner, mockTrain, mockRandom).execute();

        TestCompartment c_comp = (TestCompartment) mockTrain.getCarriages().get(0);
        TestCouchette c_couch = (TestCouchette) mockTrain.getCarriages().get(1);

        //    c_comp (10 pas.): 10 * 0.1 = 1. : 9 - remained
        //    c_couch (5 pas.): 5 * 0.1 = 0. : 5
        assertEquals(9, c_comp.getPassengerCount(), "problem with testSimulateStationArrivalZeroChange value must be 9.");
        assertEquals(5, c_couch.getPassengerCount(), "problem with testSimulateStationArrivalZeroChange value must be 5.");
    }
}