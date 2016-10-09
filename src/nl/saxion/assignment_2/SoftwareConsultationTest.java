package nl.saxion.assignment_2;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.SoftwareConsultation;
import nl.saxion.assignment_2.user.Leader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SoftwareConsultationTest {

    private SoftwareConsultation consultation;

    @Before
    public void setUp() throws Exception {
        Company company = new Company();
        Leader leader = new Leader(0, company);
        consultation = new SoftwareConsultation(leader);
    }

    @Test
    public void testSoftwareConsultation() {
        Company company = new Company();
        Leader leader = new Leader(0, company);
        SoftwareConsultation consultation = new SoftwareConsultation(leader);

        assertNotNull(consultation.toString());
        assertNotNull(consultation.getDevelopers());
        assertEquals(0, consultation.getDevelopers().size());
    }

    @Test(expected = AssertionError.class)
    public void softwareConsultationNullLeader() {
        new SoftwareConsultation(null);
    }

    @Test(expected = AssertionError.class)
    public void softwareConsultationAddNullDeveloper() {
        this.consultation.addDeveloper(null);
    }
}