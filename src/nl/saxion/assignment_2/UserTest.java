package nl.saxion.assignment_2;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserTest {

    private Company company;
    private User user;

    @Before
    public void setUp() throws Exception {
        company = new Company();
        user = new User(0, this.company);
    }

    @Test
    public void testUser() {
        Company company = new Company();
        User user = new User(0, company);

        assertNotNull(user.getCompany());
        assertEquals(0, user.getId());
    }

    @Test(expected = AssertionError.class)
    public void testUserNegativeId() {
        new User(-1, new Company());
    }

    @Test(expected = AssertionError.class)
    public void testUserNullCompany() {
        new User(0, null);
    }

    @Test(expected = AssertionError.class)
    public void assignConsultationNull() throws InterruptedException {
        this.user.assignConsultation(null);
    }

}