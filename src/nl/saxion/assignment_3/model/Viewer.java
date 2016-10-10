package nl.saxion.assignment_3.model;

import nl.saxion.assignment_3.Store;

/**
 * Viewers are given access to the store to admire the
 * yachts.
 * <p>
 * Created by Derwin on 10-Oct-16.
 */
public class Viewer extends Person {

    /**
     * Help provide unique identifier for viewers.
     */
    private static int counter = 0;

    /**
     * Constructor
     */
    public Viewer(Store store) {
        super(counter++, store);
    }

    @Override
    public void run() {
        try {
            live(); //Randomly wait
            visitStore(); //Adds itself to the entrance of the store
            waitForPermission();
            walkAround();
            exitStore();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Walk a random amount of time in the store.
     */
    private void walkAround() throws InterruptedException {
        System.out.println(toString() + " inside store");
        int max = Store.MAX_TIME_INSIDE;
        int time = random.nextInt(max - 1000) + 1000;
        Thread.sleep(time);
    }


    @Override
    public String toString() {
        return "v_" + getId();
    }
}
