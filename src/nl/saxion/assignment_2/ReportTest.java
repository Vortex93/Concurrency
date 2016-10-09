package nl.saxion.assignment_2;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.Report;
import nl.saxion.assignment_2.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ReportTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testReport() {
        User user = new User(0, new Company());
        Report report = new Report(user, "Brain problem");

        assertNotNull(report.getPerson());
        assertNotNull(report.getProblem());
        assertFalse(report.getProblem().isEmpty());
    }

    @Test(expected = AssertionError.class)
    public void reportNullPerson() {
        new Report(null, "");
    }

    @Test
    public void reportNullProblem() {
        User user = new User(0, new Company());
        new Report(user, null);
    }

    @Test(expected = AssertionError.class)
    public void reportEmptyProblem() {
        User user = new User(0, new Company());
        new Report(user, "");
    }

}