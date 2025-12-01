package actions;

import assets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

class GMPassengersTransitActionTest extends TrainTestHelper {

    private Train mockTrainFull;
    private Train mockTrainSmall;
    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        TestCompartment c1 = new TestCompartment(5);
        TestCouchette c2 = new TestCouchette(3);
        List<Carriage> testCarriagesFull = List.of(c1, c2, new TestRestaurant());
        mockTrainFull = new Train(new TestLocomotive(), testCarriagesFull);

        mockTrainSmall = new Train(new TestLocomotive(), List.of(new TestCompartment(5)));
    }

    @Test
    void testExecuteSuccessfulTransfer() {
        InputStream inputS = new ByteArrayInputStream("2\n1\n2\n\n\n".getBytes());
        Scanner scanner = new Scanner(inputS);

        TestCompartment c1 = (TestCompartment) mockTrainFull.getCarriages().get(0);
        TestCouchette c2 = (TestCouchette) mockTrainFull.getCarriages().get(1);

        GMPassengersTransitAction action = new GMPassengersTransitAction(scanner, mockTrainFull);

        assertDoesNotThrow(() -> action.execute(), "problem with testExecuteSuccessfulTransfer.");

        assertEquals(7, c1.getPassengerCount(), "problem with testExecuteSuccessfulTransfer value must be 7.");
        assertEquals(1, c2.getPassengerCount(), "problem with testExecuteSuccessfulTransfer value must be 1.");
    }

    @Test
    void testExecuteInputMismatchError() {
        InputStream inputS = new ByteArrayInputStream("1\nabc\n \n \n".getBytes());
        Scanner scanner = new Scanner(inputS);

        GMPassengersTransitAction action = new GMPassengersTransitAction(scanner, mockTrainFull);

        assertDoesNotThrow(() -> action.execute(), "problem with testExecuteInputMismatchError.");

        TestCompartment c1 = (TestCompartment) mockTrainFull.getCarriages().get(0);
        assertEquals(5, c1.getPassengerCount(), "problem with testExecuteInputMismatchError value must be 5.");
    }

    @Test
    void testExecuteSmallTrainSize() {
        InputStream inputS = new ByteArrayInputStream("\n\n\n".getBytes());
        Scanner scanner = new Scanner(inputS);

        GMPassengersTransitAction action = new GMPassengersTransitAction(scanner, mockTrainSmall);

        assertDoesNotThrow(() -> action.execute(), "problem with testExecuteSmallTrainSize.");

    }
}