package scit.diploma.utils;

/**
 * Created by scit on 5/10/14.
 */
public class ConditionalVariable {
    private boolean value = false;

    public synchronized void waitOn() throws InterruptedException {
        while( ! value) {
            wait();
        }
    }

    public synchronized void signal() {
        value = true;
        notifyAll();
    }
}
