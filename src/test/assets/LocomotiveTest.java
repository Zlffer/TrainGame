
package assets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestableLocomotive extends Locomotive {

    public static final String MODEL = "Test-141";
    public static final int WEIGHT = 50000;
    public static final int MAX_HAULING_WEIGHT = 100000;

    public TestableLocomotive() {
        super(MODEL, WEIGHT, MAX_HAULING_WEIGHT);
    }
}

class LocomotiveTest {

    private Locomotive locomotive;

    @BeforeEach
    void setUp() {
        locomotive = new TestableLocomotive();
    }


    @Test
    void testInitializationAndGetters() {
        assertEquals(TestableLocomotive.MODEL, locomotive.getModel(), "problem with testInitializationAndGetters wrong model.");
        assertEquals(TestableLocomotive.WEIGHT, locomotive.getWeight(), "problem with testInitializationAndGetters wrong weight.");
        assertEquals(TestableLocomotive.MAX_HAULING_WEIGHT, locomotive.getMaxHaulingWeight(), "problem with testInitializationAndGetters wrong max hauling weight.");
    }


    @Test
    void testCanHaulWithinLimit() {
        int haulWeight = 99999;

        assertTrue(locomotive.canHaul(haulWeight), "problem with testCanHaulWithinLimit.");
    }

    @Test
    void testCanHaulAtMaximumLimit() {
        int haulWeight = TestableLocomotive.MAX_HAULING_WEIGHT;

        assertTrue(locomotive.canHaul(haulWeight), "problem with testCanHaulAtMaximumLimit.");
    }

    @Test
    void testCannotHaulOverLimit() {
        int haulWeight = 100001;

        assertFalse(locomotive.canHaul(haulWeight), "problem with testCannotHaulOverLimit.");
    }

    @Test
    void testCanHaulZeroWeight() {
        int haulWeight = 0;

        assertTrue(locomotive.canHaul(haulWeight), "problem with testCanHaulZeroWeight.");
    }
}