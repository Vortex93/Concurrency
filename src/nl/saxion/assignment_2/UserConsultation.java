package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;
import nl.saxion.assignment_2.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * If a user reports in and no software developer is available, the project leader will wait until a
 * software dveloper reports in. If he has reported in and, in the meantime, more users have
 * reported in, then the project leader all users who have reported in the meantime invite to come
 * to the company. When they have arrived at the company, he will notify (one) software developer
 * and all arrived users that the consultation begins. There is no maximum to the number of users
 * that can participate in this consultation.
 * <p>
 * If a user reports to the company and multiple software developer are available, then the user
 * will be invited to come to the company immediately. If he has arrived, the user will be invited for
 * the consultation and also the available software developers will be notified that a user
 * consultation begins. The first software developer who continues finds that he should participate
 * in the user consultation, while the other software developer(s) find(s) that he/she is /are too late
 * for the consultation and can go back to work.
 * <p>
 * Created by Derwin on 14-Sep-16.
 */
public class UserConsultation extends Consultation {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * A user consultation consists of 1 or more users.
     */
    private final List<User> users = new ArrayList<>();

    /**
     * List of users who are present at the consultation.
     */
    private final List<User> arrivedUsers = new ArrayList<>();

    //Semaphores
    private final Semaphore usersReady = new Semaphore(0);
    private final Semaphore developerReady = new Semaphore(0);

    /**
     * Constructor
     */
    public UserConsultation(Leader leader) {
        super(leader);
    }

    @Override
    public void begin() throws InterruptedException {
        usersReady.acquire();
        developerReady.acquire();

        //Notify all that the consultation has started (1 = developer)
        hasStarted.release(arrivedUsers.size() + 1);
        super.begin();
    }

    @Override
    public void end() {
        //Notify all that the consultation has ended (1 = developer)
        hasEnded.release(arrivedUsers.size() + 1);
        super.end();
    }

    /**
     * Adds the user to the user list.
     * In other words, this is a basically an invitation or a invited user list.
     * <p>
     * Users added using this method will be considered invited to the consultation.
     *
     * @param user The user that is invited.
     */
    public void addUser(User user) throws InterruptedException {
        assert user != null;

        user.assignConsultation(this);
        users.add(user);
    }

    /**
     * Adds the user to the list of arrived list.
     *
     * @param user The user that has arrived.
     */
    public void addArrivedUser(User user) {
        assert user != null;
        arrivedUsers.add(user);

        if (arrivedUsers.size() == users.size()) {
            //All users are present
            usersReady.release(1);
        }
    }

    /**
     * Adds the developer to the user consultation.
     * Only 1 developer can attends this consultation.
     *
     * @param developer The developer to attend the consultation.
     */
    public void addDeveloper(Developer developer) throws InterruptedException {
        assert developer != null;

        /*
        Checks whether there is place for the developer,
        since user consultation can only take 1 developer.
         */
        if (getDevelopers().size() == 0) {
            super.addDeveloper(developer); //Add developer
        }
    }

    public void setDeveloperReady() {
        developerReady.release(1);
    }

    @Override
    public String toString() {
        return "user_consultation_" + super.id;
    }
}
