public class SieveThread implements Runnable {
    private int sieving, max;
    private boolean[] primes;
    private boolean running;

    // Constructor
    public SieveThread(int sieving, int max, boolean[] primes) {
        this.sieving = sieving;
        this.max = max;
        this.primes = primes;
        this.running = false;
    }

    // Helper method to set the number this thread will sieve for
    public void setSieving(int sieving) {
        this.sieving = sieving;
    }

    // Helper method to indicate whether this thread is running or not
    public boolean getRunning() {
        return running;
    }

    public void run() {
        // While it's running, set running to true
        running = true;

        // For each multiple of the number we're sieving for, set it as not prime in the sieve
        for (int k = sieving * 2; k <= max; k += sieving) {
            primes[k] = false;
        }

        // Now it's done, so indicate that it's free again
        running = false;
    }
}
