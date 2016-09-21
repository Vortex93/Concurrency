package nl.saxion.assignment_2;

import java.util.concurrent.Semaphore;

/**
 * Created by Derwin on 14-Sep-16.
 */
public class UserConsultation {

    public static final int MIN_USER_AMOUNT = 1; // can be more
    public static final int DEVELOPER_AMOUNT = 1;
    public static final int LEADER_AMOUNT = 1;

    private Semaphore usersReady = new Semaphore(MIN_USER_AMOUNT);
    private Semaphore developerReady = new Semaphore(DEVELOPER_AMOUNT);
    private Semaphore leaderReady = new Semaphore(LEADER_AMOUNT);


}
