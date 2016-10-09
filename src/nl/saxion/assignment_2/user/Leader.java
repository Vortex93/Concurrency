package nl.saxion.assignment_2.user;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.Report;
import nl.saxion.assignment_2.SoftwareConsultation;
import nl.saxion.assignment_2.UserConsultation;

import java.util.List;

/**
 * Represents the scrum leader.
 * <p>
 * Created by Derwin on 14-Sep-16.
 */
public class Leader extends Person {

    /**
     * The minimum amount of user reports to act upon.
     * By default: 1 (according to assignment)
     */
    private static final int MIN_USER_REPORT = 1;

    /**
     * The minimum amount of developer reports to act upon.
     * By default: 3 (according to assignment)
     */
    private static final int MIN_DEVELOPER_REPORT = 3;

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

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

                if (shouldHandleUserReports()) {
                    //Handle user consultation
                    handleUserConsultation();
                }

                if (shouldHandleSoftwareReports()) {
                    //Handle software consultation
                    handleSoftwareConsultation();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handle the user's reports.
     * Create a new consultation for the users who have reported in.
     */
    private void handleUserConsultation() throws InterruptedException {
        System.out.println(toString() + " handling user consultation.");
        System.out.println(toString() + " is looking for available developers.");

        //Start looking for developers for the user consultation
        //Meanwhile at this point, new users can still report and later be invited to the consultation
        Developer developer = getCompany().getAvailableDeveloper();

        System.out.println(toString() + " found developer " + developer.toString() + ".");


        UserConsultation userConsultation = new UserConsultation(this); //Create new consultation
        userConsultation.addDeveloper(developer); //Invite the developer to the user consultation

        List<Report> reports = getCompany().getUserReports(); //Get the reports from users

        while (reports.size() > 0) { //While has reports
            Report report = reports.remove(0); //Get the report and remove it
            User user = (User) report.getPerson(); //Get the user from the report

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

    /**
     * Handles the developer's reports.
     * To create a new consultation to have a meeting with the developers
     * who have reported in.
     */
    private void handleSoftwareConsultation() throws InterruptedException {
        System.out.println(toString() + " handling software consultation.");
        System.out.println(toString() + " inviting all developer.");

        SoftwareConsultation consultation = new SoftwareConsultation(this); //Create new consultation

        List<Report> reports = getCompany().getSoftwareReports(); //Get reports
        List<Developer> availableDevelopers = getCompany().getAvailableDevelopers(); //Get all available developers

        while (reports.size() > 0) { //While has reports
            Report report = reports.remove(0); //Remove the first from the list
            Developer developer = (Developer) report.getPerson(); //Get the person from the report

            if (availableDevelopers.contains(developer)) { //If currently available developer has reported in
                consultation.addDeveloper(developer); //Add developer to the invitation list of the consultation
                availableDevelopers.remove(developer); //Remove the available developer from the available list
            }
            System.out.println(toString() + " inviting " + developer.toString() + " to " + consultation.toString());
        }

        //Ready up for consultation
        consultation.begin(); //Start consultation
        super.waitRandomTime(1000, 5000); //Address problem for 1 to 5 seconds
        consultation.end(); //End consultation
        System.out.println(toString() + " continues working.");
    }

    /**
     * Simulate busy time for the user.
     */
    private void work() throws InterruptedException {
        super.waitRandomTime(1000, 2000);
    }

    /**
     * @return Returns true if the leader should handle the user reports, otherwise false.
     */
    private boolean shouldHandleUserReports() {
        return getCompany().getUserReports().size() >= MIN_USER_REPORT;
    }

    /**
     * @return Returns true if the leader should handle the software reports, otherwise false.
     */
    private boolean shouldHandleSoftwareReports() {
        return getCompany().getSoftwareReports().size() >= MIN_DEVELOPER_REPORT;
    }

    @Override
    public String toString() {
        return "leader_" + getId();
    }
}
