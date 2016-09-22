package nl.saxion.assignment_2;

import nl.saxion.assignment_2.user.Developer;
import nl.saxion.assignment_2.user.Leader;
import nl.saxion.assignment_2.user.User;

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

    private Semaphore userArrived = new Semaphore(0);
    private Semaphore developerReady = new Semaphore(0);

    /**
     * Constructor
     */
    public UserConsultation(Leader leader) {
        super(leader);
    }

    @Override
    public void begin() throws InterruptedException {
        userArrived.acquire();
        developerReady.acquire();
        super.begin();
    }

    /**
     * @param developer Add the developer to the user consultation
     *                  There is only 1 developer allowed per user consultation.
     */
    public void addDeveloper(Developer developer) {
        assert developer != null;

        /*
        Checks whether there is place for the developer,
        since user consultation can only take 1 developer.
         */
        if (getDevelopers().size() < 1) {
            super.addDeveloper(developer); //Add developer
            developerReady.release(1); //Set the developer ready
        }
    }

    /**
     * The user calls this method when the user has arrived to the consultation.
     *
     * @param user User to be added to the list.
     */
    public void addUser(User user) {
        assert user != null;

        super.addUser(user); //Add the user
        userArrived.release(1); //Notify that the user has arrived
    }

    @Override
    public String toString() {
        return "user_consultation_" + super.id;
    }
}
