package nl.saxion.assignment_3.model;

import nl.saxion.assignment_3.Store;

/**
 * Created by Derwin on 10-Oct-16.
 */
public class Buyer extends Person {

    private static int counter = 0;

    /**
     * Constructor
     */
    public Buyer(Store store) {
        super(counter++, store);
    }

    @Override
    public void run() {
        try {
            live();
            visitStore();
            waitForPermission();
            negotiate();
            exitStore(); //Exit with a new boat
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void negotiate() throws InterruptedException {
        System.out.println(toString() + " negotiating...");
        int max = store.MAX_NEGOTIATION_TIME;
        int min = store.MIN_NEGOTIATION_TIME;
        int time = random.nextInt(max - min) + min;
        Thread.sleep(time);
    }

    @Override
    public String toString() {
        return "b_" + getId();
    }
}
