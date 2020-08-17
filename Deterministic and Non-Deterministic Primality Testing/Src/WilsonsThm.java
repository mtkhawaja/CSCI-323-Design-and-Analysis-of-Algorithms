
/*
* Src: https://artofproblemsolving.com/wiki/index.php/Wilson%27s_Theorem
* Wilson's Theorem states that if (p > 1) | 'p' is an integer, 
* then if  [ (p-1)! + 1 ] / p == 0 
* => p is prime.
* Nature: Deterministic
* Theoretical Time Complexity: O(n!) 
*/

import java.math.*;

class WilsonsThm {

	/**
	 * @param BiInteger num: The number under consideration.
	 * @return boolean: true if prime.
	 */
	static public boolean isPrime(BigInteger num) {
		Tracker.incOps(1);
		if (num.compareTo(BigInteger.ONE) <= 0)
			return false;
		Tracker.incOps(3);
		return (factorial(num.subtract(BigInteger.ONE)).add(BigInteger.ONE)).mod(num)
				.compareTo(BigInteger.ZERO) == 0;
	}

	/**
	 * @param int num: The number under consideration.
	 * @return boolean: true if prime.
	 */
	static public boolean isPrime(int num) {
		return isPrime(Utility.bigInt(num));
	}

	private static BigInteger factorial(BigInteger num) {
		BigInteger factorialBigInteger = BigInteger.ONE;
		while (!num.equals(BigInteger.ZERO)) {
			Tracker.incOps(3);
			factorialBigInteger = factorialBigInteger.multiply(num);
			num = num.subtract(BigInteger.ONE);
		}
		Tracker.incOps(1);
		return factorialBigInteger;
	}

}