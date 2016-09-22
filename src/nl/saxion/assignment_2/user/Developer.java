package nl.saxion.assignment_2.user;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.Consultation;
import nl.saxion.assignment_2.SoftwareConsultation;
import nl.saxion.assignment_2.UserConsultation;

/**
 * A software developer can participate in two types of consultation:
 * - A consultation with two other software developers to discuss/prioritize user-stories
 * - A consultation with Jaap (Leader) and one or more users.
 * <p>
 * Created by Derwin on 14-Sep-16.
 */
public class Developer extends Person {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Whether the developer is attending the consultation.
     */
    private boolean isAttendingConsultation = false;

    /**
     * Constructor
     *
     * @param id A unique identifier for the person.
     *           The id is used in combination with the type of user.
     *           e.g. "dev_1" which in this case d stands for developer and 1 as the id.
     */
    public Developer(int id, Company company) {
        super(id, company);

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                work(); //Busy for 1000 to 5000 ms

                getCompany().addAvailableDeveloper(this); //Add to the available queue
                chill(); //Chill for a certain time between 100 to 1000 ms

                if (isAttendingConsultation) {
                    if (consultation instanceof UserConsultation) {
                        UserConsultation consultation = (UserConsultation) this.consultation;
                        consultation.addDeveloper(this); //Developer adds itself to notify that the developer is ready

                        System.out.println(toString() + " attending the consultation.\n" +
                                toString() + " waiting for the consultation to start.");

                        consultation.waitUntilReady();
                        consultation.waitUntilEnd();
                        System.out.println(toString() + " is exiting the consultation.");


                        /*
                        Remove available developer method is not required in this
                        portion of the code, reason being that it already removes itself
                        from the available developer from the assign consultation method.
                         */
                    } else if (consultation instanceof SoftwareConsultation) {
                        // TODO: 23-Sep-16 Implementation of the software consultation

                    }
                    super.consultation = null;
                    System.out.println(toString() + " continues with work.");
                } else {
                    getCompany().removeAvailableDeveloper(this); //Remove from the queue
                }
                //Developer gets back to work
                isAttendingConsultation = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Basically invites the developer to the consultation.
     *
     * @param consultation The consultation to be part participate in.
     */
    @Override
    public void assignConsultation(Consultation consultation) throws InterruptedException {
        super.assignConsultation(consultation); //Set the consultation
        getCompany().removeAvailableDeveloper(this);//Remove from the available queue

        System.out.println(toString() + " has been assigned to " + consultation.toString());

        isAttendingConsultation = true; //Change consultation status
    }

    /**
     * Work some random time.
     */
    private void work() {
        super.waitRandomTime(1000, 5000);
    }

    /**
     * Basically be available for certain time.
     */
    private void chill() {
        super.waitRandomTime(100, 1000);
    }

    @Override
    public String toString() {
        return "dev_" + getId();
    }
}