package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.User;

/**
 * Created by Derwin on 14-Sep-16.
 */
public class Report {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private User user;
    private String problem;

    /**
     * Constructor
     *
     * @param user    The user making this report.
     * @param problem The problem of the user.
     */
    public Report(User user, String problem) {
        assert user != null;
        assert problem != null;

        this.user = user;
        this.problem = problem;
    }

    public User getUser() {
        return user;
    }

    public String getProblem() {
        return problem;
    }
}
