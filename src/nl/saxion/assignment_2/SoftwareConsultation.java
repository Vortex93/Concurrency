package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Derwin on 14-Sep-16.
 */
public class SoftwareConsultation extends Consultation {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * A software consultation consists of at least 3 developers.
     */
    private final List<Developer> developers = new ArrayList<>();

    private Semaphore leaderReady = new Semaphore(1);
    private Semaphore developerReady = new Semaphore(0);

    /**
     * Constructor
     */
    public SoftwareConsultation(Leader leader) {
        super(leader);
    }

    public void addDeveloper(Developer developer) {
        this.developers.add(developer);
        if (this.developers.size() >= 3) {
            developerReady.release(1);
        }
    }
}
