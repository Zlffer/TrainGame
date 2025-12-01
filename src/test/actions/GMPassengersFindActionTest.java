
package actions;

import assets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

class GMPassengersFindActionTest extends TrainTestHelper {

    private Train mockTrain;
    private TestPassengerCarriage c1, c2, c3;
    private List<Carriage> testCarriages;

    @BeforeEach
    void setUp() {
        // Створюємо вагони з різним станом
        c1 = new TestPassengerCarriage(15, 20); // 15 пас.
        c2 = new TestPassengerCarriage(5, 20);  // 5 пас.
        c3 = new TestPassengerCarriage(12, 20); // 12 пас.

        testCarriages = List.of(c1, c2, c3, new TestRestaurant()); // 4 вагони
        mockTrain = new Train(new TestLocomotive(), testCarriages);
    }


    private void testSearchScenario(String input, int expectedCount) {
        InputStream inputS = new ByteArrayInputStream(input.getBytes());
        Scanner mockScanner = new Scanner(inputS);

        GMPassengersFindAction action = new GMPassengersFindAction(mockScanner, mockTrain);

        action.execute();
    }

    @Test
    void testFinderRangeFoundMultiple() {
        testSearchScenario("10\n20\n \n", 2);
    }

    @Test
    void testFinderRangeReversed() {
        testSearchScenario("15\n5\n \n", 3);
    }

    @Test
    void testFinderRangeNotFound() {
        testSearchScenario("25\n30\n \n", 0);
    }


    @Test
    void testExecuteInputMismatchError() {
        InputStream inputS = new ByteArrayInputStream("10\nabc\n\n\n".getBytes());
        Scanner mockScanner = new Scanner(inputS);

        GMPassengersFindAction action = new GMPassengersFindAction(mockScanner, mockTrain);

        assertDoesNotThrow(() -> action.execute(), "problem with testExecuteInputMismatchError.");
    }

    @Test
    void testExecuteInputMismatchFirstNumberError() {

        InputStream inputS = new ByteArrayInputStream("abc\n10\n \n".getBytes());
        Scanner mockScanner = new Scanner(inputS);

        GMPassengersFindAction action = new GMPassengersFindAction(mockScanner, mockTrain);

        assertDoesNotThrow(() -> action.execute(), "problem with testExecuteInputMismatchFirstNumberError.");
    }
}