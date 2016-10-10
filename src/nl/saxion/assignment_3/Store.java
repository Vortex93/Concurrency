package nl.saxion.assignment_3;

import nl.saxion.assignment_3.model.Buyer;
import nl.saxion.assignment_3.model.Person;
import nl.saxion.assignment_3.model.Viewer;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
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

    private boolean viewerHasPermission = false;
    private boolean buyerHasPermission = false;

    private int successiveCounter = 0;

    /**
     * Constructor
     */
    public Store() {

    }

    public void addWaiting(Person person) {
        assert person != null;
        lock.lock();
        waiting.add(person);
        if (waiting.size() == 1) {
            if (person instanceof Buyer) {
                giveBuyerPermission();
            } else {
                giveViewerPermission();
            }
        }
        lock.unlock();
    }

    public void addToInside(Person person) {
        assert person != null;
        lock.lock();
        waiting.remove(person);
        inside.add(person);
        System.out.println(person.toString() + " enters store (" + inside.size() + ")");
        lock.unlock();
    }

    public void removeFromInside(Person person) {
        assert person != null;
        lock.lock();

        if (person instanceof Buyer) {
            if (++successiveCounter % SUCCESSIVE_THRESHOLD == 0
                    || getAmountBuyerWaiting() == 0) {
                giveViewerPermission();
            } else {
                giveBuyerPermission();
            }
        }

        inside.remove(person);

        if (viewerHasPermission) {
            viewerPermission.signal();
        } else {
            if (isEmpty()) {
                buyerPermission.signal();
                isEmpty.signal();
            }
        }
        notFull.signal();

        System.out.println(person.toString() + " exits store (" + inside.size() + ")");
        lock.unlock();

    }

    public void waitForPermission(Person person) {
        try{
            lock.lock();


            System.out.println(person.toString() + " waits for permission");
            if (person instanceof Viewer) { //Person is a viewer
                while (!viewerHasPermission) {
                    viewerPermission.await(); //Wait for viewer permission
                }
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

            while (isFull())
                notFull.await(); //Wait until it is not full

            addToInside(person); //Add person inside the storez
        } catch  (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private int getAmountBuyerWaiting() {
        int counter = 0;
        for (Person person : waiting) {
            if (person instanceof Buyer) {
                ++counter;
            }
        }
        return counter;
    }

    private void giveViewerPermission() {
        viewerHasPermission = !(buyerHasPermission = false);
    }

    private void giveBuyerPermission() {
        buyerHasPermission = !(viewerHasPermission = false);
    }

    /**
     * @return Returns true
     */
    private boolean isFull() {
        return inside.size() >= MAX_ALLOWED_VIEWER;
//        int viewerCounter = 0;
//        int buyerCounter = 0;
//        for (Person person : inside) {
//            viewerCounter += person instanceof Viewer ? 1 : 0;
//            buyerCounter += person instanceof Buyer ? 1 : 0;
//        }
//        return buyerCounter >= MAX_ALLOWED_BUYER || viewerCounter >= MAX_ALLOWED_VIEWER;
    }

    private boolean isEmpty() {
        return inside.size() == 0;
    }

}
