import java.io.*;
import java.math.*;
import java.util.Random;
// import static Utility.bigInt;
// import static Utility.randomBigInt;
/*
* Sources: 
*     1. https://www.geeksforgeeks.org/primality-test-set-2-fermet-method/
*     2. https://en.wikipedia.org/wiki/Fermat_primality_test#Complexity
https://stackoverflow.com/questions/29617590/complexity-of-fermats-little-theorem
* Nature: Probabilistic, Monte Carlo algorithm
* Theoretical Time Complexity (Assuming exponentiation and multiplication is optimized): 
*       O(k log₂(n)) | k = number of trials, n = number under consideration
*/

class FermatsLittleThm {

	/**
	 * @param BigInteger n: The number under consideration
	 * @param BigInteger k: Number of iterations for prime testing.
	 * @return boolean: true if probably prime.
	 */
	static boolean isPrime(BigInteger n) {
		// Corner cases: Assert that n > 4
		Tracker.incOps(2);
		if (n.compareTo(BigInteger.ONE) <= 0 || n.compareTo(Utility.bigInt(4)) == 0)
			return false;
		Tracker.incOps(1);
		if (n.compareTo(Utility.bigInt(3)) <= 0) // 2 and 3 are primes
				return true;
		Random rand = new Random();
		BigInteger k = Utility.bigInt((n.bitLength() + 1) >> 1);
		Tracker.incOps(n.bitLength());
		BigInteger sqrt = n.sqrt();
		// Pick a random number a in [2, n-2] k times and for each iteration test using
		// Fermat's little theorem: Prime if a^(n-1) % n = 1 for all a < n-1
		while (k.compareTo(BigInteger.ZERO) > 0) {
			Tracker.incOps(4);
			BigInteger a = Utility.randomBigInt(sqrt, rand).add(BigInteger.TWO);
			if (!a.modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE))
				return false;
			k = k.subtract(BigInteger.ONE);
		}
		return true;
	}

	/**
	 * @param int n: number under consideration.
	 * @param int k: number of random ints used.
	 * @return boolean: true if probably prime.
	 */
	static boolean isPrime(int n) {
		return isPrime(Utility.bigInt(n));
	}
}