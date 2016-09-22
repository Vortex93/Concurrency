package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;
import nl.saxion.assignment_2.user.User;

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

    /**
     * A user consultation consists of 1 or more users.
     */
    private final List<User> users = new ArrayList<>();

    protected final Semaphore isReady = new Semaphore(0);
    protected final Semaphore hasEnded = new Semaphore(0);

    /**
     * Start the consultation.
     * This is called by the leader, to ready up the members.
     */
    public void begin() throws InterruptedException {
        isReady.release(users.size() + developers.size());
        System.out.println(toString() + " started.");
    }

    /**
     * End the consultation.
     * This is called by the leader, to notify the members to continue what
     * they were doing.
     */
    public void end() {
        hasEnded.release(users.size() + developers.size());
        System.out.println(toString() + " ended.");
    }

    /**
     * Wait until the consultation is ready.
     */
    public void waitUntilReady() throws InterruptedException {
        isReady.acquire(); //Wait until consultation is ready
    }

    /**
     * Wait until the consultation has ended.
     */
    public void waitUntilEnd() throws InterruptedException {
        hasEnded.acquire(); //Wait until consultation has ended
    }

    /**
     * Constructor
     *
     * @param leader The leader who created this consultation.
     */
    protected Consultation(Leader leader) {
        this.leader = leader;
    }

    /**
     * @param developer Add the developer to the developer list.
     */
    protected void addDeveloper(Developer developer) {
        assert developer != null;
        this.developers.add(developer);
    }

    /**
     * @param user Add the user to the users list.
     */
    protected void addUser(User user) {
        this.users.add(user);
    }

    /**
     * @return Returns the list of developers.
     */
    protected List<Developer> getDevelopers() {
        return developers;
    }

    /**
     * @return Returns the list of users.
     */
    protected List<User> getUsers() {
        return users;
    }
}