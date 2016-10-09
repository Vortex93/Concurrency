package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Abstract of the consultation.
 * <p>
 * Created by Derwin on 14-Sep-16.
 */
@SuppressWarnings("WeakerAccess")
public abstract class Consultation {

    private static int counter = 0;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * This is a unique identification for the user consultation.
     */
    protected final int id = counter++;

    /**
     * Leader of this consultation.
     */
    private final Leader leader;

    /**
     * Developers participating in this consultation.
     */
    private final List<Developer> developers = new ArrayList<>();

    //Semaphores
    protected final Semaphore hasStarted = new Semaphore(0);
    protected final Semaphore hasEnded = new Semaphore(0);

    /**
     * Constructor
     *
     * @param leader The leader who created this consultation.
     */
    protected Consultation(Leader leader) {
        assert leader != null;
        this.leader = leader;
    }

    /**
     * Start the consultation.
     * This is called by the leader, to ready up the members.
     */
    public void begin() throws InterruptedException {
        System.out.println(toString() + " started.");
    }

    /**
     * End the consultation.
     * This is called by the leader, to notify the members to continue what
     * they were doing.
     */
    public void end() {
        System.out.println(toString() + " ended.");
    }

    /**
     * Wait until the consultation has started.
     */
    public void waitUntilStart() throws InterruptedException {
        hasStarted.acquire(); //Wait until consultation has started
    }

    /**
     * Wait until the consultation has ended.
     */
    public void waitUntilEnd() throws InterruptedException {
        hasEnded.acquire(); //Wait until consultation has ended
    }

    /**
     * @param developer Add the developer to the developer invitation list.
     */
    protected void addDeveloper(Developer developer) throws InterruptedException {
        assert developer != null;
        developers.add(developer); //Invite developer
        developer.assignConsultation(this); //Assign this consultation to the developer
    }

    /**
     * @return Returns the list of developers.
     */
    protected List<Developer> getDevelopers() {
        return developers;
    }
}