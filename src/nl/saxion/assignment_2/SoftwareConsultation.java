package nl.saxion.assignment_2;

import java.util.concurrent.Semaphore;

/**
 * Created by Derwin on 14-Sep-16.
 */
public class SoftwareConsultation extends Consultation {

    public static final int DEVELOPER_AMOUNT = 3; // can be more
    public static final int LEADER_AMOUNT = 1;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private Semaphore leaderReady = new Semaphore(LEADER_AMOUNT);
    private Semaphore developerReady = new Semaphore(DEVELOPER_AMOUNT);

    public void addPerson(Person person) {
        people.add(person);
    }


}
