package nl.saxion.assignment_3.model;

import nl.saxion.assignment_3.Store;

import java.util.Random;

/**
 * Abstract class of person, every buyer and viewer extends from this
 * class.
 * <p>
 * This give general functions to the buyers and viewers.
 * <p>
 * Created by Derwin on 10-Oct-16.
 */
public abstract class Person implements Runnable {

    private static final int MIN_TIME_LIVE = 1000;
    private static final int MAX_TIME_LIVE = 5000;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Helper to provide random waiting time.
     */
    final Random random = new Random();

    /**
     * Unique identifier of the person.
     */
    private int id;

    /**
     * The store that the person is going to visit.
     */
    final Store store;

    /**
     * Constructor
     */
    public Person(int id, Store store) {
        assert store != null;
        this.id = id;
        this.store = store;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(toString());
    }

    /**
     * @return Returns the unique identifier of the person.
     */
    public int getId() {
        return id;
    }

    /**
     * Randomly wait an amount of time.
     */
    void live() throws InterruptedException {
        int time = random.nextInt(MAX_TIME_LIVE - MIN_TIME_LIVE) + MIN_TIME_LIVE;
        Thread.sleep(time);
    }

    /**
     * Adds itself to the waiting line of the store.
     */
    void visitStore() {
        this.store.addToWaiting(this);
    }

    /**
     * Remove itself from the inside list of the store.
     */
    void exitStore() {
        this.store.removeFromInside(this);
    }

    /**
     * Waits for permission to enter the building.
     */
    void waitForPermission() throws InterruptedException {
        this.store.waitForPermission(this);
    }
}
