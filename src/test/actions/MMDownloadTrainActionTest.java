
package actions;

import assets.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import actions.TrainTestHelper.*;

class MMDownloadTrainActionTest extends TrainTestHelper{

    private MMDownloadTrainAction action;

    private static final String SUCCESS_FILENAME = "test_success_train.txt";

    @BeforeEach
    void setUp() {
        action = new MMDownloadTrainAction(new Scanner(" \n"));

        try (PrintWriter writer = new PrintWriter(SUCCESS_FILENAME)) {
            writer.println("L-Series");
            writer.println("coorc");
        } catch (Exception e) {
            fail("Error: " + e.getMessage());
        }
    }


    @Test
    void testParseLocomotiveSuccess() {
        assertNotNull(action.parseLocomotive("L-Series"), "problem with testParseLocomotiveSuccess must be LocomotiveLight.");
        assertNotNull(action.parseLocomotive("M-Series"), "problem with testParseLocomotiveSuccess must be LocomotiveMedium.");
        assertNotNull(action.parseLocomotive("H-Series"), "problem with testParseLocomotiveSuccess must be LocomotiveHeavy.");
    }

    @Test
    void testParseLocomotiveInvalid() {
        assertNull(action.parseLocomotive("X-Series"), "problem with testParseLocomotiveInvalid must be NULL.");
        assertNull(action.parseLocomotive(""), "problem with testParseLocomotiveInvalid must be NULL too.");
    }

    @Test
    void testParseCarriagesSuccess() {
        // 'c' - Compartment, 'o' - Couchette, 'r' - Restaurant, 'x' - unknown
        List<Carriage> carriages = action.parseCarriages("cOrx");

        assertEquals(3, carriages.size(), "problem with testParseCarriagesSuccess must be 3.");
        assertTrue(carriages.get(0) instanceof CarriageCompartment, "problem with testParseCarriagesSuccess must be Compartment.");
        assertTrue(carriages.get(1) instanceof CarriageCouchette, "problem with testParseCarriagesSuccess must be Couchette.");
        assertTrue(carriages.get(2) instanceof CarriageRestaurant, "problem with testParseCarriagesSuccess must be Restaurant.");
    }

    @Test
    void testCalculateTotalWeight() {
        List<Carriage> carriages = List.of(
                new TestCompartment(0),
                new TestCouchette(0),
                new TestRestaurant()
        );
        assertEquals(2100, action.calculateTotalWeight(carriages), "problem with testCalculateTotalWeight.");
    }


    @Test
    void testApplyComfortBoostsLogic() {
        List<Carriage> carriages = List.of(
                new TestCompartment(0),
                new TestRestaurant(),
                new TestCompartment(0)
        );

        action.applyComfortBoosts(carriages);

        assertFalse(carriages.get(0).getIsBoosted(), "problem with testApplyComfortBoostsLogic must be not boosted.");
    }

    @Test
    void testExecuteFileNotFound() {
        Scanner userScanner = new Scanner("nonexistent.txt\n\n\n");
        MMDownloadTrainAction action = new MMDownloadTrainAction(userScanner);

        assertDoesNotThrow(() -> action.execute(),
                "problem with testExecuteFileNotFound.");
    }

    @Test
    void testExecuteTrainTooHeavy() {
        try (PrintWriter writer = new PrintWriter("heavy_train.txt")) {
            writer.println("L-Series");
            writer.println("oooooooooooooooooooo");
        } catch (Exception e) { fail(e.getMessage()); }

        Scanner userScanner = new Scanner("heavy_train.txt\n\n");
        MMDownloadTrainAction action = new MMDownloadTrainAction(userScanner);

        assertDoesNotThrow(() -> action.execute(),
                "problem with testExecuteTrainTooHeavy.");

        new File("heavy_train.txt").delete();
    }

    @Test
    void testExecuteWrongData() {
        try (PrintWriter writer = new PrintWriter("wrong_loco.txt")) {
            writer.println("Unknown-Model");
            writer.println("c");
        } catch (Exception e) { fail(e.getMessage()); }

        Scanner userScanner = new Scanner("wrong_loco.txt\n\n");
        MMDownloadTrainAction action = new MMDownloadTrainAction(userScanner);

        assertDoesNotThrow(() -> action.execute(),
                "problem with testExecuteWrongData.");

        new File("wrong_loco.txt").delete();
    }
}