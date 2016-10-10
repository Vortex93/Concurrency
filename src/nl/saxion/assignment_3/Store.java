package nl.saxion.assignment_3;

import nl.saxion.assignment_3.model.Buyer;
import nl.saxion.assignment_3.model.Person;
import nl.saxion.assignment_3.model.Viewer;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * This is the store that is managed by the HISWA organisation.
 * Created by Derwin on 10-Oct-16.
 */
public class Store {

    private static final int SUCCESSIVE_THRESHOLD = 4;
    private static final int MAX_ALLOWED_VIEWER = 10;
    private static final int MAX_ALLOWED_BUYER = 1;
    public static final int MAX_TIME_INSIDE = 2000;
    public static final int MAX_NEGOTIATION_TIME = 3000;
    public static final int MIN_NEGOTIATION_TIME = 1000;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * People that are waiting at the entrance are listed in this array.
     */
    private final ArrayList<Person> waiting = new ArrayList<>();

    /**
     * People that are inside the building are listed in this array.
     */
    private final ArrayList<Person> inside = new ArrayList<>();

    private final Lock lock = new ReentrantLock();
    private final Condition viewerPermission = lock.newCondition();
    private final Condition buyerPermission = lock.newCondition();
    private final Condition notFull = lock.newCondition();
    private final Condition isEmpty = lock.newCondition();

    /**
     * Permissions
     */
    private boolean viewerHasPermission = false;
    private boolean buyerHasPermission = false;

    /**
     * Counter on how many buyer has negotiated.
     * If the remainder of this value (using SUCCESSIVE_THRESHOLD) is 0,
     * then the permission is given to the viewer.
     */
    private int successiveCounter = 0;

    /**
     * Add a person to the waiting line. (outside the entrance)
     * @param person The person that is going to wait in line.
     */
    public void addToWaiting(Person person) {
        assert person != null;
        lock.lock();
        waiting.add(person);
        if (waiting.size() == 1) {
            if (person instanceof Buyer) {
                giveBuyerPermission();
            } else {
                giveViewerPermission();
            }
        } else {
            if (person instanceof Buyer) {
                giveBuyerPermission();
            }
        }
        lock.unlock();
    }

    /**
     * This method is called when a person comes inside the store.
     * @param person The person that is going inside.
     */
    public void addToInside(Person person) {
        assert person != null;
        lock.lock();
        waiting.remove(person);
        inside.add(person);
        System.out.println(person.toString() + " enters store (" + inside.size() + ")");
        lock.unlock();
    }

    /**
     * This method is called whenever a person exists the store.
     * @param person The person that is leaving the building.
     */
    public void removeFromInside(Person person) {
        assert person != null;
        lock.lock();
        if (person instanceof Buyer) {
            //Buyer
            if (++successiveCounter % SUCCESSIVE_THRESHOLD == 0) {
                giveViewerPermission();
            } else {
                giveBuyerPermission();
            }
        } else {
            //Viewer
            if (isFull()) {
                if (getAmountBuyerWaiting() > 0) {
                    giveBuyerPermission();
                }
            }
        }

        inside.remove(person); //Person exists the store
        System.out.println(person.toString() + " exits store (" + inside.size() + ")");

        if (viewerHasPermission) {
            viewerPermission.signal(); //Signal a viewer to come inside
            notFull.signal();
        } else if (isEmpty()) {
            buyerPermission.signal(); //Signal a buyer to come inside
            isEmpty.signal(); //Notify the buyer that the store is empty
        }
        lock.unlock();
    }

    /**
     * People wait at this point until they are given
     * permission to come inside the store.
     * @param person The person that is awaiting permission.
     */
    public void waitForPermission(Person person) {
        try {
            lock.lock();
            System.out.println(person.toString() + " waits for permission");
            if (person instanceof Viewer) { //Person is a viewer
                while (!viewerHasPermission)
                    viewerPermission.await(); //Wait for viewer permission
                while (isFull())
                    notFull.await(); //Wait until it is not full
            } else if (person instanceof Buyer) { //Person is a buyer
                while (!buyerHasPermission) {
                    buyerPermission.await(); //Wait for a buyer permission
                }
                buyerHasPermission = false; //Since only 1 buyer can negotiate

                while (!isEmpty()) {
                    isEmpty.await();
                }
            }
            viewerPermission.signal();
            addToInside(person); //Add person inside the store
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * @return Returns the amount of buyer that is waiting outside in the line
     * to come inside the store.
     */
    private int getAmountBuyerWaiting() {
        int counter = 0;
        for (Person person : waiting) {
            if (person instanceof Buyer) {
                ++counter;
            }
        }
        return counter;
    }

    /**
     * Gives permission to the viewer.
     * NOTE: This does not signal a viewer to come inside.
     */
    private void giveViewerPermission() {
        viewerHasPermission = !(buyerHasPermission = false);
    }

    /**
     * Gives permission to the buyer
     * NOTE: This does not signal a buyer to come inside.
     */
    private void giveBuyerPermission() {
        buyerHasPermission = !(viewerHasPermission = false);
    }

    /**
     * @return Returns true if the store is full.
     */
    private boolean isFull() {
        return inside.size() >= MAX_ALLOWED_VIEWER;
    }

    /**
     * @return Returns true if the store is empty.
     */
    private boolean isEmpty() {
        return inside.size() == 0;
    }

}
