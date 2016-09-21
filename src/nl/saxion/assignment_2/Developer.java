package nl.saxion.assignment_2;

/**
 * Created by Derwin on 14-Sep-16.
 */
class Developer extends Person {

    /**
     * Constructor
     *
     * @param id A unique identifier for the person.
     *           The id is used in combination with the type of user.
     *           e.g. "d_1" which in this case d stands for developer and 1 as the id.
     */
    Developer(int id) {
        super(id);
    }

    @Override
    public String toString() {
        return "d_" + getId();
    }
}