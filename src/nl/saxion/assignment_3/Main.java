package nl.saxion.assignment_3;

import nl.saxion.assignment_3.model.Buyer;
import nl.saxion.assignment_3.model.Person;
import nl.saxion.assignment_3.model.Viewer;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * A simulation for assignment 3
 * <p>
 * Created by Derwin on 09-Oct-16.
 */
public class Main {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * List of people in the simulation.
     */
    private ArrayList<Person> people = new ArrayList<>();

    /**
     * Manages all the threads.
     */
    private ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) {
        new Main().run();
    }

    /**
     * Starting point of the simulation.
     */
    private void run() {
        //Create the store object
        Store store = new Store();

        //Populate the array of people
        for (int i = 0; i < 20; i++) {
            people.add(new Viewer(store));
            if (i < 6) { //Add buyers
                people.add(new Buyer(store));
            }
        }

        //Make a thread pool according to the size of people
        this.threadPoolExecutor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(people.size());

        //Start the life of every person
        for (Person person : this.people) {
            threadPoolExecutor.execute(person);
        }
    }
}
