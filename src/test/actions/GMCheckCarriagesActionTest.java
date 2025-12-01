
package actions;

import assets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;


class GMCheckCarriagesActionTest extends TrainTestHelper {

    private Scanner mockScanner;
    private Train mockTrain;
    private TestPassengerCarriage c1, c2;
    private TestRestaurant r1;
    private List<Carriage> testCarriages;

    @BeforeEach
    void setUp() {
        mockScanner = new Scanner(" \n");

        c1 = new TestPassengerCarriage(15, 20);
        c2 = new TestPassengerCarriage(5, 20);
        r1 = new TestRestaurant();

        testCarriages = List.of(c1, c2, r1);
        mockTrain = new Train(new TestLocomotive(), testCarriages);

        mockTrain.setHasBeenChecked(false);
        mockTrain.addScore(-mockTrain.getScore());
    }

    @Test
    void testCheckSucceedsAndRemovesStowaways() {
        Random mockRandom = new MockRandom(5, 0.0);
        GMCheckCarriagesAction action = new GMCheckCarriagesAction(mockScanner, mockTrain, mockRandom);

        action.execute();

        assertTrue(mockTrain.hasBeenChecked(), "problem with testCheckSucceedsAndRemovesStowaways value must be true.");

        // (15 pas. * 0.1 = 1.5. (int)1.5 = 1. -> 15 - 1 = 14)
        assertEquals(14, c1.getPassengerCount(), "problem with testCheckSucceedsAndRemovesStowaways value must be 14.");

        // (5 pas. * 0.1 = 0.5. but at least 1 -> 5 - 1 = 4)
        assertEquals(4, c2.getPassengerCount(), "problem with testCheckSucceedsAndRemovesStowaways value must be 4.");

        //score (1 * 10 + 1 * 10 = 20)
        assertEquals(20, mockTrain.getScore(), "problem with testCheckSucceedsAndRemovesStowaways value must be 20.");
    }

    @Test
    void testCheckFailsAndStowawaysNotRemoved() {
        // nothing happened random must be < 20 to remove passengers
        Random mockRandom = new MockRandom(25, 0.0);
        GMCheckCarriagesAction action = new GMCheckCarriagesAction(mockScanner, mockTrain, mockRandom);

        action.execute();

        assertTrue(mockTrain.hasBeenChecked(), "problem with testCheckFailsAndStowawaysNotRemoved value must be true");

        assertEquals(15, c1.getPassengerCount(), "problem with testCheckFailsAndStowawaysNotRemoved value must be the same 15");
        assertEquals(5, c2.getPassengerCount(), "problem with testCheckFailsAndStowawaysNotRemoved value must be the same 5.");

        assertEquals(0, mockTrain.getScore(), "problem with testCheckFailsAndStowawaysNotRemoved value must be 0");
    }

    @Test
    void testCheckAlreadyCarriedOut() {
        mockTrain.setHasBeenChecked(true);

        Random mockRandom = new MockRandom(5, 0.0);
        GMCheckCarriagesAction action = new GMCheckCarriagesAction(mockScanner, mockTrain, mockRandom);

        action.execute();

        assertEquals(0, mockTrain.getScore(), "problem with testCheckAlreadyCarriedOut value must be 0");
        assertEquals(15, c1.getPassengerCount(), "problem with testCheckAlreadyCarriedOut value must be the same 15.");
        assertEquals(5, c2.getPassengerCount(), "problem with testCheckAlreadyCarriedOut value must be the same 5.");


        assertTrue(mockTrain.hasBeenChecked(), "problem with testCheckAlreadyCarriedOut value must be true");
    }
}