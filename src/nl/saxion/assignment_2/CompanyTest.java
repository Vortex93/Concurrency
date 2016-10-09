package nl.saxion.assignment_2;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.Report;
import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CompanyTest {

    private Company company;

    @Before
    public void setUp() throws Exception {
        company = new Company();
    }

    @Test
    public void testCompany() throws InterruptedException {
        Company company = new Company();
        Developer developer1 = new Developer(0, company);
        Developer developer2 = new Developer(1, company);

        company.addDeveloper(developer1);
        company.addDeveloper(developer2);

        company.addAvailableDeveloper(developer1);
        company.addAvailableDeveloper(developer2);
        assertNotNull(company.getAvailableDevelopers());
        assertEquals(2, company.getAvailableDevelopers().size());

        assertNotNull(company.getAvailableDeveloper());
        assertEquals(1, company.getAvailableDevelopers().size());

        company.addAvailableDeveloper(developer1);
        company.removeAvailableDeveloper(developer1);

        assertEquals(1, company.getAvailableDevelopers().size());

        User user = new User(0, company);
        Report report = new Report(user, "Brain problem");

        company.addUserReport(report);
        assertNotNull(company.getUserReports());
        assertEquals(1, company.getUserReports().size());

        report = new Report(developer1, "Also brain problem");
        company.addSoftwareReport(report);

        assertNotNull(company.getSoftwareReports());
        assertEquals(1, company.getSoftwareReports().size());
    }

    @Test(expected = AssertionError.class)
    public void addDeveloperNull() {
        this.company.addDeveloper(null);
    }

    @Test(expected = AssertionError.class)
    public void addUserReportNull() throws InterruptedException {
        this.company.addUserReport(null);
    }

    @Test(expected = AssertionError.class)
    public void addSoftwareReportNull() throws InterruptedException {
        this.company.addSoftwareReport(null);
    }

    @Test(expected = AssertionError.class)
    public void addAvailableDeveloperNull() throws InterruptedException {
        this.company.addAvailableDeveloper(null);
    }

    @Test(expected = AssertionError.class)
    public void removeAvailableDeveloperNull() throws InterruptedException {
        this.company.removeAvailableDeveloper(null);
    }

}
