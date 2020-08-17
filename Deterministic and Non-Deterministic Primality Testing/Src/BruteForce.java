import java.math.BigInteger;

/*
* Nature: Deterministic
* Base Algorithm: 
* Divide the number under consideration by all numbers between [0, sqrt(n)]
* If any number wholly divides n, then n is not a prime and return false. Otherwise, return true.
* Naieve Theoretical Time Complexity: O(n^.5)
*/

class BruteForce {

	/**
	 * @param BiInteger num: The number under consideration.
	 * @return boolean: true if prime.
	 */
	static public boolean isPrime(BigInteger num) {
		Tracker.incOps(2);
		if (num.compareTo(BigInteger.ONE) <= 0 || num.and(BigInteger.ONE).equals(BigInteger.ZERO))
			return false;
		Tracker.incOps(num.bitLength());
		BigInteger limit = num.sqrt();
		while (limit.compareTo(BigInteger.ONE) > 0) {
			Tracker.incOps(2);
			if (!limit.and(BigInteger.ONE).equals(BigInteger.ZERO) && num.mod(limit).compareTo(BigInteger.ZERO) == 0)
				return false;
			Tracker.incOps(2);
			limit = limit.subtract(BigInteger.ONE);
		}
		return true;
	}

	/**
	 * @param int num: The number under consideration.
	 * @return boolean: true if prime.
	 */
	static public boolean isPrime(int num) {
		return isPrime(Utility.bigInt(num));
	}

}
