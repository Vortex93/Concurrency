package nl.saxion.assignment_2.user;

import nl.saxion.assignment_2.Company;
import nl.saxion.assignment_2.Consultation;

import java.util.Random;

/**
 * Represents a person in the simulation.
 * Every type of person is extended from this class.
 * <p>
 * Created by Derwin on 14-Sep-16.
 */
@SuppressWarnings("WeakerAccess")
public abstract class Person implements Runnable {

    /**
     * This is used throughout subclasses, to help generate random decision.
     * To help make the simulation more realistic.
     */
    private static final Random RANDOM = new Random();

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Unique identifier.
     */
    private final int id;
    /**
     * A person is always bonded to a company in a way or another (based on the assignment).
     */
    private final Company company;

    /**
     * The consultation that the person is participating in.
     */
    protected Consultation consultation;

    /**
     * Constructor
     *
     * @param id A unique identifier for the person.
     *           The id is used in combination with the type of user.
     *           e.g. "d_1" which in this case d stands for developer and 1 as the id.
     */
    Person(int id, Company company) {
        assert id >= 0;
        assert company != null;

        this.id = id;
        this.company = company;
    }

    /**
     * Assign a consultation to the person.
     */
    public void assignConsultation(Consultation consultation) throws InterruptedException {
        assert consultation != null;
        this.consultation = consultation;
    }

    /**
     * @return Returns the company.
     */
    public Company getCompany() {
        return company;
    }

    /**
     * @return Returns the id of the current user.
     */
    public int getId() {
        return id;
    }

    /**
     * Simulate busy time for the user.
     *
     * @param min The minimum amount of time to wait.
     * @param max The maximum amount of time to wait.
     */
    protected void waitRandomTime(int min, int max) {
        assert min > 0;

        int randomTime = RANDOM.nextInt(min) + max + 1;
        try {
            Thread.sleep(randomTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param probability The percentage, this value should range from 0.01 to 1.
     * @return Returns true if based on the probability specified, otherwise false.
     */
    protected boolean getRandomDecision(float probability) {
        assert probability > 0.0f && probability <= 1f;

        return RANDOM.nextFloat() <= probability;
    }
}