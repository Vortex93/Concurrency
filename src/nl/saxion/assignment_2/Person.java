package nl.saxion.assignment_2;

/**
 * Represents a person in the simulation.
 * Every type of person is extended from this class.
 * <p>
 * Created by Derwin on 14-Sep-16.
 */
abstract class Person extends Thread {

    private final int id;
    private Consultation consultation;

    /**
     * Constructor
     *
     * @param id A unique identifier for the person.
     *           The id is used in combination with the type of user.
     *           e.g. "d_1" which in this case d stands for developer and 1 as the id.
     */
    Person(int id) {
        this.id = id;
    }

    /**
     * Assign a consultation to the person.
     */
    public void assignConsulation(Consultation consultation) {
        assert consultation != null;

        this.consultation = consultation;
    }

    @Override
    public long getId() {
        return id;
    }

    /**
     * @return Returns the consultation that the person is in.
     */
    public Consultation getConsultation() {
        return consultation;
    }
}
