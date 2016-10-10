package nl.saxion.assignment_3;

import nl.saxion.assignment_3.model.Buyer;
import nl.saxion.assignment_3.model.Person;
import nl.saxion.assignment_3.model.Viewer;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Derwin on 09-Oct-16.
 */
public class Main {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private ArrayList<Person> people = new ArrayList<>();
    private ThreadPoolExecutor threadPoolExecutor;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        //Create the store object
        Store store = new Store();

        //Populate the array of people
        for (int i = 0; i < 100; i++) {
            people.add(new Viewer(store));
            if (i < 20) { //Add buyers
                people.add(new Buyer(store));
            }
        }

        //Make a thread pool according to the size of people
        this.threadPoolExecutor =
                (ThreadPoolExecutor) Executors.newFixedThreadPool(people.size());

        for (Person person : this.people) {
            threadPoolExecutor.execute(person); //Start the life of this person
        }
    }
}
