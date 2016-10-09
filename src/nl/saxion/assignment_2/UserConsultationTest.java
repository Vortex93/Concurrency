package nl.saxion.assignment_2;

import com.sun.org.apache.xerces.internal.xinclude.MultipleScopeNamespaceSupport;
import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.UserConsultation;
import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserConsultationTest {

    private UserConsultation consultation;

    @Before
    public void setUp() throws Exception {
        Company company = new Company();
        Leader leader = new Leader(0, company);
        consultation = new UserConsultation(leader);
    }

    @Test
    public void testUserConsultation() throws InterruptedException {
        Company company = new Company();
        Leader leader = new Leader(0, company);
        UserConsultation consultation = new UserConsultation(leader);

        assertNotNull(consultation.getDevelopers());
        assertEquals(0, consultation.getDevelopers().size());

        Developer developer = new Developer(0, company);
        consultation.addDeveloper(developer);

        assertNotNull(consultation.getDevelopers());
        assertEquals(1, consultation.getDevelopers().size());

        assertNotNull(consultation.toString());
        assertFalse(consultation.toString().isEmpty());

        assertEquals("user_consultation_0", consultation.toString());
    }

    @Test(expected = AssertionError.class)
    public void userConsultationNullLeader() {
        Company company = new Company();
        Leader leader = new Leader(0, company);
        new UserConsultation(leader);
    }

    @Test(expected = AssertionError.class)
    public void userConsultationAddNullDeveloper() throws InterruptedException {
        this.consultation.addDeveloper(null);
    }

}