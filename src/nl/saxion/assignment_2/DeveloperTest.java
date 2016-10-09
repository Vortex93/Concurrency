package nl.saxion.assignment_2;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.user.Developer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeveloperTest {

    private Developer developer;
    private Company company;

    @Before
    public void setUp() throws Exception {
        company = new Company();
        developer = new Developer(0, company);
    }

    @Test
    public void testDeveloper() {
        Company company = new Company();
        Developer developer = new Developer(0, company);

        assertNotNull(developer.getCompany());
        assertEquals(0, developer.getId());

        assertNotNull(developer.toString());
        assertFalse(developer.toString().isEmpty());
    }

    @Test(expected = AssertionError.class)
    public void developerNegativeId() {
        new Developer(-1, null);
    }

    @Test(expected = AssertionError.class)
    public void developerNullCompany() {
        new Developer(0, null);
    }

    @Test(expected = AssertionError.class)
    public void assignNullConsultation() throws InterruptedException {
        developer.assignConsultation(null);
    }
}
