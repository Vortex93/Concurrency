package nl.saxion.assignment_2;

/**
 * Where the simulation starts.
 * <p>
 * Created by user on 9/14/16.
 */
public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        //Start the simulation
        new Company().init();
    }
}