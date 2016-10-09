package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;
import nl.saxion.assignment_2.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * The company that is run by the leader and its developers
 * Created by Derwin on 21-Sep-16.
 */
@SuppressWarnings("WeakerAccess")
public class Company {

    /**
     * The amount of developer to generate.
     * By default: 6
     */
    private static final int DEVELOPER_COUNT = 2;

    /**
     * The amount of users to generate.
     */
    private static final int USER_COUNT = 2;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Leader of the team.
     */
    private final Leader leader = new Leader(0, this);

    /**
     * This represents the team of developers.
     */
    private final List<Developer> developers = new ArrayList<>();

    /**
     * This is the number of users.
     * These users have a relation with this company.
     * <p>
     * P.S.: This number can be changed to a random value.
     */
    private final List<User> users = new ArrayList<>();

    /**
     * Reports that are sent by the users.
     */
    private final List<Report> userReports = new ArrayList<>();

    /**
     * Reports that are sent by the software developers.
     */
    private final List<Report> softwareReports = new ArrayList<>();

    /**
     * Queue list of developers who are available for consultation.
     */
    private final List<Developer> availableDevelopers = new ArrayList<>();

    /**
     * This semaphore is conditionally dependant on:
     * - Whether there is at least 1 available developer in the list.
     */
    private final Semaphore hasAvailableDevelopers = new Semaphore(0, true);

    /**
     * Constructor
     */
    public Company() {
        //No implementation required
    }

    /**
     * Starts the simulation.
     */
    public void init() {
        //Create developers for the company
        for (int i = 0; i < DEVELOPER_COUNT; i++) {
            Developer developer = new Developer(i, this); //Create new developer
            addDeveloper(developer); //Add developer to the company
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
     * Add an available developer to the available list.
     *
     * @param developer The developer who is available.
     */
    public void addAvailableDeveloper(Developer developer) throws InterruptedException {
        assert developer != null;

        //Add developer to the list of available developer
        availableDevelopers.add(developer);

        //Due to problem with mutex, this solution seems to work best
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

    /**
     * Remove a developer from the available list.
     * This is called when the developer is no longer available.
     *
     * @param developer The developer who used to be available.
     */
    public void removeAvailableDeveloper(Developer developer) throws InterruptedException {
        assert developer != null;

        availableDevelopers.remove(developer); //Remove developer from tha available list

        //Due to problem with mutex, this solution seems to work best
        if (availableDevelopers.size() == 0) {
            hasAvailableDevelopers.drainPermits();
        }
    }

    /**
     * @return Returns list of available developers.
     */
    public List<Developer> getAvailableDevelopers() throws InterruptedException {
        hasAvailableDevelopers.acquire();
        return availableDevelopers;
    }
}
