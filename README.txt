To compile:
javac Primes.java SieveThread.java

To run:
java Primes

-------------------------------------
My approach uses the concept of a Prime Sieve to filter all the composite numbers out of an array of 10^8 numbers. The numbers are represented as an array of booleans, all initially set to "true" to indicate that they are prime. To filter out the composites, start marking as false (composite) all multiples of 2, then 3, and so on. It is only necessary to filter the multiples of prime numbers in this way, because the multiples of composite numbers are also multiples of smaller prime numbers. Thus, I increment a counter to indicate what number I am currently sieving for, but if my counter is at a composite number I skip it. I keep sieving until the counter reaches sqrt(10^8); it is not necessary to go farther than this because every number past this point is either prime or has already been accounted for when sieving for previous numbers.

To make this method more efficient, I split the work up into 8 threads. The moment a thread became free, I set it to sieve for the next number I needed to sieve for. Since each thread is instantly put to work the moment it becomes idle, each thread spends the entire runtime iterating through the sieve, meaning they each do about the same amount of work.

To test the correctness of my program, I decreased the max to various values and tested my nums and sums against an online calculator's. The results, as follows (along with corresponding runtimes), were all confirmed to be correct:

max		num		sum			runtime
100		25		1060			1ms
1000		168		76127			1ms
10000		1229		5736396			2ms
100000		9592		454396537		4ms
1000000		78498		37550402023		11ms
10000000	664579		3203324994356		50ms
100000000	5761455		279209790387276		983ms
