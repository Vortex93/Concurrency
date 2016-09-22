package nl.saxion.assignment_2.user;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.Report;
import nl.saxion.assignment_2.UserConsultation;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * Represents the scrum leader.
 * <p>
 * Created by Derwin on 14-Sep-16.
 */
public class Leader extends Person {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private Random random = new Random();

    /**
     * This semaphore is conditionally dependant on:
     * - The leader is not participating in a consultation.
     * - The leader received an invitation.
     * <p>
     * The semaphore release on the conditions specified.
     */
    private Semaphore readyForConsultation = new Semaphore(0);

    /**
     * Constructor
     *
     * @param id A unique identifier for the person.
     *           The id is used in combination with the type of user.
     *           e.g. "leader_1" which in this case d stands for leader and 0 as the id.
     */
    public Leader(int id, Company company) {
        super(id, company);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                work(); //Delay for 1000 to 2000 ms
                if (readyForConsultation.tryAcquire()) {
                    //The leader is ready to start the consultation
                    System.out.println(toString() + " is ready to start a consultation");

                }

                /*
                User Consultation
                 */
                if (getCompany().getReports().size() > 0) { //Check whether there is report
                    System.out.println(toString() + " received report.\n" +
                            toString() + " making a user consultation.");

                    //Get the reports
                    LinkedBlockingQueue<Report> reports = getCompany().getReports();
                    Report report = reports.poll(); //Get the most recent report

                    User user = report.getUser(); //Get the user in charge of the report
                    UserConsultation userConsultation = new UserConsultation(this);

                    System.out.println(toString() + " inviting " + user.toString() + " to the consultation.");
                    user.assignConsultation(userConsultation); //Invite the user to the consultation


                    //Wait for developers to be available
                    System.out.println(toString() + " is getting available developers.");
                    LinkedBlockingQueue<Developer> availableDevelopers = getCompany().getAvailableDevelopers();
                    Developer developer = availableDevelopers.peek();

                    System.out.println(toString() + " found developer " + developer.toString() + ".");
                    developer.assignConsultation(userConsultation); //Invite developer to the user consultation

                    userConsultation.begin(); //Start the consultation meeting.
                    System.out.println(toString() + " is consulting.");

                    super.waitRandomTime(1000, 5000); //Wait between 1000 to 5000 ms

                    System.out.println(toString() + " is ending the consultation.");
                    userConsultation.end();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Simulate busy time for the user.
     */
    private void work() throws InterruptedException {
        super.waitRandomTime(1000, 2000);
    }

    @Override
    public String toString() {
        return "leader_" + getId();
    }
}
