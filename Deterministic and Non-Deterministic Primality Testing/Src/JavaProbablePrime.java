import java.math.BigInteger;
// Probabilistic 
class JavaProbablePrime{

	/**
	* @param BiInteger num: The number under consideration.
	* @return boolean: true if prime.   
	*/
	static public boolean isPrime(BigInteger num){
		Tracker.incOps((num.bitLength() + 1) >> 1);
		return num.isProbablePrime(10); 
	}

	/**
	* @param int num: The number under consideration.
	* @return boolean: true if prime.   
	*/
	static public boolean isPrime(int num){
		return isPrime(Utility.bigInt(num)); 
	}
}