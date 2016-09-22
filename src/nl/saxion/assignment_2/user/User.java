package nl.saxion.assignment_2.user;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.Consultation;
import nl.saxion.assignment_2.Report;
import nl.saxion.assignment_2.UserConsultation;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * If a user reports in and no software developer is available, the project leader will wait until a
 * software developer reports in. If he has reported in and, in the meantime, more users have
 * reported in, then the project leader all users who have reported in the meantime invite the come
 * to the company. When they have arrived at the company, he will notify (one) software developer
 * and all arrived users tat the consultation begins. There is no maximum to the number of usrs
 * that can participate in this consultation.
 * <p>
 * If a user reports to the company and multiple software developers are available, then the user
 * will be invited to come to the company immediately. If he has arrived, the user will be invited for
 * the consultation nd also the available software developers will be notified that a user consultation begins.
 * The first software developer who continues finds that he should participate
 * in the user consultation, while the other software developer(s) find(s) that hse /she is/are too late
 * for the consultation and can go back to work.
 * <p>
 * Created by Derwin on 14-Sep-16.
 */
public class User extends Person {

    /**
     * The amount of chance that the user might encounter a problem.
     */
    private static final float PROBLEM_ENCOUNTER_PROBABILITY = 0.5f;
    private static final Random RANDOM = new Random();

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private final Semaphore waitForInvitation = new Semaphore(0);

    /**
     * This is just for randomness.
     * <p>
     * As the number increase, the less problem the user encounters.
     * After the user has finished the consultation this number should change,
     * the reason being that they addressed a problem which the user should
     * encounter less of.
     */
    private float problemEncounterProbabilityMultiplier = 1f;

    /**
     * Constructor
     *
     * @param id      A unique identifier for the person.
     *                The id is used in combination with the type of user.
     *                e.g. "d_1" which in this case d stands for developer and 1 as the id.
     * @param company The company that the user reports to.
     */
    public User(int id, Company company) {
        super(id, company);

        //Start the user's life
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                live(); //Delay between 1000 to 2000 ms
                if (encounterProblem()) { //If user encounters a problem
                    System.out.println(toString() + " encountered a problem.\n" +
                            toString() + " reporting in.\n" +
                            toString() + " waiting for invitation.");
                    reportIn(); //The user reports to the company
                    waitForInvitation.acquire(); //Wait for invitation.

                    System.out.println(toString() + " arrives at the consultation.");

                    UserConsultation consultation = (UserConsultation) super.consultation;
                    consultation.addUser(this); //User arrives to the consultation

                    System.out.println(toString() + " waits until the consultation is ready.");

                    consultation.waitUntilReady(); //Waits until the user consultation is ready
                    consultation.waitUntilEnd(); //Waits until the consultation ends

                    System.out.println(toString() + " exiting the consultation.");

                    //This is to lower the chance of encountering the next problem
                    problemEncounterProbabilityMultiplier = RANDOM.nextFloat();

                    super.consultation = null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Live (causes some delay from 1000 to 2000 ms)
     */
    private void live() throws InterruptedException {
        System.out.println(toString() + " living la vida loca...");
        int randomTime = RANDOM.nextInt(1000) + 1000 + 1;
        Thread.sleep(randomTime);
    }

    /**
     * @return Returns true if the user encountered a problem, otherwise false.
     */
    private boolean encounterProblem() {
        return getRandomDecision(PROBLEM_ENCOUNTER_PROBABILITY
                * problemEncounterProbabilityMultiplier);
    }

    /**
     * The user reports to the company.
     */
    private void reportIn() throws InterruptedException {
        Report report = new Report(this, "I have a brain problem!!");
        getCompany().addReport(report);
    }

    /**
     * This is basically an invitation to the user, the method will be
     * called from the leader of the company / team.
     *
     * @param consultation The user consultation that the user is invited to.
     */
    @Override
    public void assignConsultation(Consultation consultation) throws InterruptedException {
        super.assignConsultation(consultation);

        System.out.println(toString() + " got invited.");
        waitForInvitation.release(1); //Got invited
    }

    @Override
    public String toString() {
        return "user_" + getId();
    }
}