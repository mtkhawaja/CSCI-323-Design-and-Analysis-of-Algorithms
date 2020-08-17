import java.math.BigInteger;
/*
* Sources: 
*     1. https://www.geeksforgeeks.org/aks-primality-test/
*     2. https://en.wikipedia.org/wiki/AKS_primality_test#History_and_running_time
* Nature: Deterministic 
* Theoretical Time Complexity : [O(log(n)^6), O(log(n)^12)] ~= O(log(n)^10.5) 
*/
class AgrawalKayalSaxena {
  /**
   * @param BigInteger num: The number under consideration.
   * @return boolean: true if prime.   
   */
  static public boolean isPrime(BigInteger num){
	Tracker.incOps(4);
    if (num.compareTo(Utility.bigInt(1)) <= 0)
      return false;
    if (num.compareTo(Utility.bigInt(3)) <= 0)
      return true;
    if (num.and(BigInteger.ONE).equals(BigInteger.ZERO) || num.mod(Utility.bigInt(3)).equals(BigInteger.ZERO))
      return false;
	Tracker.incOps(num.bitLength());
  	BigInteger sqrt = num.sqrt();
    for (BigInteger i = Utility.bigInt(5); i.compareTo(sqrt) <= 0; i = i.add(Utility.bigInt(6))) {
		Tracker.incOps(4);
		if (num.mod(i).equals(BigInteger.ZERO) || num.mod(i.add(BigInteger.TWO)).equals(BigInteger.ZERO))
			return false;
    }
    return true;
  }

  /**
   * @param int num: The number under consideration.
   * @return boolean: true if prime.   
   */
  static public boolean isPrime(int num){
    return isPrime(Utility.bigInt(num)); 
  }
} 