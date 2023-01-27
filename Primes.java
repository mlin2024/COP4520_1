import java.util.*;
import java.lang.*;
import java.io.*;

public class Primes {
    public static void main(String[] args) {
        int numThreads = 8;
        int max = 100000000;
        
        // Create initial sieve, mark everything as prime (except 0 and 1 since they won't ever get sieved over)
        boolean[] primes = new boolean[max + 1];
        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;

        // Start the timer
        long start = System.currentTimeMillis();

        // Create 8 new threads
        SieveThread[] threads = new SieveThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new SieveThread(0, max, primes);
        }

        // Start sieving at 2
        int i = 2;
        // Only go up to sqrt(max) because after this, all numbers encountered will either be prime or already accounted for
        while (i <= Math.ceil(Math.sqrt(max))) {
            if (primes[i]) {
                boolean foundAvailableThread = false;
                while (true) {
                    // Go through each thread until one becomes available
                    for (int j = 0; j < numThreads; j++) {
                        // Once an available thread is found, set it to start sieving the current number we want to sieve
                        if (!threads[j].getRunning()) {
                            foundAvailableThread = true;
                            threads[j].setSieving(i);
                            threads[j].run();
                            i++;
                            break;
                        }
                        if (foundAvailableThread) break;
                    }
                    if (foundAvailableThread) break;
                }
            }
            // If it's not prime, no reason to sieve for it
            else i++;
        }

        int numPrimes = 0;
        long sumPrimes = 0;

        // Create new array to hold top 10 largest primes
        int[] topTen = new int[10];
        int at = 9;

        // Iterate through the sieve and add all the primes (going backwards to obtain the top 10 greatest primes)
        for (int j = max; j >= 0; j--) {
            if (primes[j]) {
                numPrimes++;
                sumPrimes += j;
                if (at >= 0) {
                    topTen[at] = j;
                    at--;
                }
            }
        }

        // End the timer
        long end = System.currentTimeMillis();

        try {
            File file = new File("primes.txt");
            // Delete the file if it currently exists (if it doesn't, nothing happens)
            file.delete();
            // Create a new file called primes.txt
            file.createNewFile();

            // Write the required lines to the file
            FileWriter writer = new FileWriter("primes.txt");
            writer.write((end - start) + "ms " + numPrimes + " " + sumPrimes + "\n");
            for (int j = 0; j < 9; j++) {
                writer.write(topTen[j] + " ");
            }
            writer.write(topTen[9] + "\n");

            writer.close();
        } catch (IOException e) {
            System.out.println("Error opening or writing to the file.");
            e.printStackTrace();
        }
    }
}
