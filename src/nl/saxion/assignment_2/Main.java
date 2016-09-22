package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Where the simulation starts.
 * <p>
 * Created by user on 9/14/16.
 */
public class Main {

    private static final int USER_COUNT = 1;

    private static final int DEVELOPER_COUNT = 6;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private List<Person> people = new ArrayList<>();

    /**
     * This is the company also known as the team.
     * In this case "company" is used. (This can be later changed)
     */
    private Company company;

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        //Start the simulation
        company = new Company();
    }
}