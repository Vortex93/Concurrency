package nl.saxion.assignment_2;

import java.util.concurrent.TimeUnit;

/**
 * Created by Derwin on 14-Sep-16.
 */
public class User extends Person {

    ///////////////////////////////////////////////////////////////////////////
    // Properties
    ///////////////////////////////////////////////////////////////////////////

    private Report report;

    public User(int id) {
        super(id);

        System.out.println("Hi");
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            System.out.println("Hello world");
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void live() {

    }

    private void reportIn() {
        report = new Report(this, "I have a brain problem!!");
    }

    public boolean hasReport() {
        return report != null;
    }

    @Override
    public String toString() {
        return "u_" + getId();
    }
}