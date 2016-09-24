package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Person;

/**
 * Created by Derwin on 14-Sep-16.
 */
public class Report {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private Person person;
    private String problem;

    /**
     * Constructor
     * @param person Person who writes the report.
     * @param problem Description of the report.
     */
    public Report(Person person, String problem) {
        assert person != null : "person is null";
        assert problem != null : "problem is null";
        assert !problem.isEmpty() : "problem is empty";

        this.person = person;
        this.problem = problem;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////////////////////////////////

    public Person getPerson() {
        return person;
    }

    public String getProblem() {
        return problem;
    }

}
