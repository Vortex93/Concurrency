package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * If 3 software developers have reported in, then the product owner will invite them to a
 * consultation about drawing up and prioritizing the user stories to be worked on in the next sprint.
 * <p>
 * If the product owner has the choice between a user consultation and a developers consultation,
 * the consultation with the end user will have priority. After all, the customer is king.
 * <p>
 * If a consultation has ended (which is determined by the project leader), each participant in the
 * consultation will go back to his place of work to continue his daily activities. Users who have
 * participated in this consultation will go back to their own company.
 */
public class SoftwareConsultation extends Consultation {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * A software consultation consists of at least 3 developers.
     */
    private final List<Developer> developers = new ArrayList<>();

    /**
     * This semaphore is conditionally dependant on:
     * - When there is certain amount of developers attending the software consultation.
     * <p>
     * When the conditions are true, the semaphore is released.
     */
    private Semaphore developerReady = new Semaphore(0); //min 3


    /**
     * Constructor
     */
    public SoftwareConsultation(Leader leader) {
        super(leader);
    }

    @Override
    public void begin() throws InterruptedException {
        developerReady.acquire();
        hasStarted.release(developers.size());
        super.begin();
    }

    @Override
    public void end() {
        hasEnded.release(developers.size());
        super.end();
    }

    /**
     * Add the developer to the developer list. If there are 3 or more developers
     *
     * @param developer Add the developer to the developer list.
     */
    public void addDeveloper(Developer developer) {
        this.developers.add(developer);

        if (this.developers.size() >= 3) {
            setDevelopersReady(this.developers.size());
        }
    }

    /**
     * Releases semaphore with the amount of developers that can attend the consultation.
     */
    private void setDevelopersReady(int amount) {
        assert amount > 0;
        developerReady.release(amount);
    }


    @Override
    public String toString() {
        return "software_consultation_" + super.id;
    }
}
