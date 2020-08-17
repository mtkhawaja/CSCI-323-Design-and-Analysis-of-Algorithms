import java.io.*; 
import java.math.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;


class Utility {
	/**
	* @param max : Maximum value for a random BigInteger. 
	* @param randomSource: Seed for rng.  
	* @return Random BigInteger of n bits.   
	*/
	static BigInteger randomBigInt(BigInteger max, Random randomSource) {
		BigInteger randomNumber, temp;
		int nlen = max.bitLength();
		Tracker.incOps(1);
		BigInteger nm1 = max.subtract(BigInteger.ONE);
		do {
			Tracker.incOps(2);
			temp = new BigInteger(nlen + 100, randomSource);
			randomNumber = temp.mod(max);
		} while (max.subtract(randomNumber).add(nm1).bitLength() >= nlen + 100);
		// System.out.println(randomNumber);
		return randomNumber;
	}

	static BigInteger bigInt(int i) { return BigInteger.valueOf(i);}

	static String formatTime(long time){
		return String.format("%.6fs", time * 1e-9);
	}

	static BigInteger pow (int a, int b) {
		return bigInt(a).pow(b);
	}
}