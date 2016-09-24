package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;
import nl.saxion.assignment_2.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * The company that is run by the leader and its developers
 * Created by Derwin on 21-Sep-16.
 */
@SuppressWarnings("WeakerAccess")
public class Company {

    private static final int DEVELOPER_COUNT = 6;
    private static final int USER_COUNT = 5;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Leader of the team.
     */
    private Leader leader = new Leader(0, this);

    /**
     * The team consists of 6 developer.
     */
    private final LinkedBlockingQueue<Developer> developers = new LinkedBlockingQueue<>();

    /**
     * Queue list of developers who are available for consultation.
     */
    private final List<Developer> availableDevelopers = new ArrayList<>();
    private final Semaphore hasAvailableDevelopers = new Semaphore(0, true);

    /**
     * Reports that are sent by the users.
     */
    private final List<Report> userReports = new ArrayList<>();

    /**
     * Reports that are sent by the software developers.
     */
    private final List<Report> softwareReports = new ArrayList<>();

    /**
     * This is the number of users.
     * These users have a relation with this company.
     * <p>
     * P.S.: This number can be changed to a random value.
     */
    private final List<User> users = new ArrayList<>();

    /**
     * Constructor
     */
    public Company() {
        //Create developers for the company
        for (int i = 0; i < DEVELOPER_COUNT; i++) {
            Developer developer = new Developer(i, this); //Create new developer
            try {
                developers.put(developer); //Add user to the queue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Create all the users
        for (int i = 0; i < USER_COUNT; i++) {
            User user = new User(i, this); //Create new user
            users.add(user); //Add to the user list
        }
    }

    /**
     * @param developer The developers participating in the agile scrum.
     */
    public void addDeveloper(Developer developer) {
        assert developer != null;
        developers.add(developer);
    }

    /**
     * Add report to the user report list.
     *
     * @param report Report to be added.
     */
    public void addUserReport(Report report) throws InterruptedException {
        assert report != null : "null report";
        userReports.add(report);
    }

    /**
     * Add report to the software report list.
     *
     * @param report Report to be added.
     */
    public void addSoftwareReport(Report report) throws InterruptedException {
        assert report != null : "null report";
        softwareReports.add(report);
    }

    /**
     * @return Returns the leader of the team / company.
     */
    public Leader getLeader() {
        return leader;
    }

    /**
     * @return Returns the queue of the developers
     */
    public LinkedBlockingQueue<Developer> getDevelopers() {
        return developers;
    }

    /**
     * @return Returns the queue of the user reports.
     */
    public List<Report> getUserReports() {
        return userReports;
    }

    /**
     * @return Returns the queue of the software reports.
     */
    public List<Report> getSoftwareReports() {
        return softwareReports;
    }

    /**
     * @return Returns true if there is any user reports, otherwise false.
     */
    public boolean hasReports() {
        return userReports.size() > 0;
    }

    public void addAvailableDeveloper(Developer developer) throws InterruptedException {
        assert developer != null;

        //Add developer to the list of available developer
        availableDevelopers.add(developer);

        if (availableDevelopers.size() > 0) {
            hasAvailableDevelopers.release(1);
        }
    }

    /**
     * @return Returns the first available developer.
     */
    public Developer getAvailableDeveloper() throws InterruptedException {
        hasAvailableDevelopers.acquire();
        return availableDevelopers.remove(0); //Remove the first one
    }

    public void removeAvailableDeveloper(Developer developer) throws InterruptedException {
        assert developer != null;

        availableDevelopers.remove(developer);

        if (availableDevelopers.size() == 0) {
            hasAvailableDevelopers.drainPermits();
        }
    }

    /**
     * @return Returns list of available developers.
     */
    public List<Developer> getAvailableDevelopers() throws InterruptedException {
        hasAvailableDevelopers.acquire();
       /* availableDevelopers.clear();*/
        return availableDevelopers;
    }
}
