package nl.saxion.assignment_2;

import java.util.ArrayList;
import java.util.List;

/**
 * Where the simulation starts.
 * <p>
 * Created by user on 9/14/16.
 */
public class Main {

    public static final int USER_COUNT = 15; //This is for testing
    public static final int DEVELOPER_COUNT = 6;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    private List<Person> people = new ArrayList<>();

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        init();
    }

    private void init() {
        for (int i = 0; i < USER_COUNT; i++) { //Create amount of users
            people.add(new User(i));
        }
        for (int i = 0; i < DEVELOPER_COUNT; i++) { //Create amount of developers
            people.add(new Developer(i));
        }
        people.add(new Leader(0)); //1 Project leader
    }
}