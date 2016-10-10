package nl.saxion.assignment_3.model;

import nl.saxion.assignment_3.Store;

import java.util.Random;

/**
 * Created by Derwin on 10-Oct-16.
 */
public abstract class Person implements Runnable {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    protected final Random random = new Random();

    /**
     * Unique identifier of the person.
     */
    private int id;
    final Store store;

    public Person(int id, Store store) {
        assert store != null;
        this.id = id;
        this.store = store;
    }

    @Override
    public void run() {
        Thread.currentThread().setName(toString());
    }

    public int getId() {
        return id;
    }

    void live() throws InterruptedException {
        int time = random.nextInt(5000) + 1000;
        Thread.sleep(time);
    }

    void visitStore() {
        this.store.addWaiting(this);
    }

    void exitStore() {
        this.store.removeFromInside(this);
    }

    void waitForPermission() throws InterruptedException {
        this.store.waitForPermission(this);
    }
}
