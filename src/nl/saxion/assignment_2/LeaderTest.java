package nl.saxion.assignment_2;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.user.Leader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LeaderTest {

    @Test
    public void testLeader() {
        Company company = new Company();
        Leader leader = new Leader(0, company);

        assertNotNull(leader.toString());
        assertNotNull(leader.getCompany());
        assertEquals(0, leader.getId());
    }

    @Test(expected = AssertionError.class)
    public void leaderNegativeId() {
        new Leader(-1, null);
    }

    @Test(expected = AssertionError.class)
    public void leaderNullCompany() {
        new Leader(0, null);
    }
}