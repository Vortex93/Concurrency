package nl.saxion.assignment_2.user;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.Report;
import nl.saxion.assignment_2.UserConsultation;

import java.util.List;
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

                /*
                User Consultation
                 */
                //Check whether there is report from users
                if (getCompany().getReports().size() > 0) {
                    //Report received
                    System.out.println(toString() + " received report.");
                    System.out.println(toString() + " is looking for available developers.");

                    //Start looking for developers for the user consultation
                    //Meanwhile at this point, new users can still report and later be invited to the consultation
                    Developer developer = getCompany().getAvailableDeveloper();

                    System.out.println(toString() + " found developer " + developer.toString() + ".");

                    //Create new consultation
                    UserConsultation userConsultation = new UserConsultation(this);

                    //Invite the developer to the user consultation
                    userConsultation.addDeveloper(developer);

                    //Get the reports from users
                    List<Report> reports = getCompany().getReports();

                    //Go through each report
                    while (reports.size() > 0) {
                        Report report = reports.remove(0); //Get the report and remove it
                        User user = report.getUser(); //Get the user from the report

                        System.out.println(toString() + " inviting " + user.toString() + " to " + userConsultation.toString());

                        //Invite the user to the consultation
                        userConsultation.addUser(user);
                    }

                    //Ready up for consultation
                    userConsultation.begin(); //Start the consultation
                    super.waitRandomTime(1000, 5000); //Wait between 1000 to 5000 ms
                    userConsultation.end(); //End the consultation
                    System.out.println(toString() + " continues working.");
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