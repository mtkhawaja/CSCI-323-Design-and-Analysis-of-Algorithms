
import java.io.*; 
import java.math.*;
import java.util.*;
import java.util.stream.Stream;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.function.BiFunction;

public interface TestExecutor {
	static void runTest() {
		PrimalityTester.runTest();
	}
	static class PrimalityTester {
		private enum Test {Wilson, BruteForce, AKS, JavaProbable, MillerRabin, FermatLittle};

		private static String testName(Test test) {
			switch(test) {
				case Wilson:
					return "Wilson's";
				case BruteForce:
					return "Brute-force";
				case AKS:
					return "AKS";
				case JavaProbable:
					return "Java";
				case MillerRabin:
					return "Miller-Rabin";
				case FermatLittle:
					return "Fermat's";
				default:
					return null;
			}
		}
		private static Function<BigInteger, Boolean> testFunc(Test test) {
			switch(test) {
				case Wilson:
					return WilsonsThm::isPrime;
				case BruteForce:
					return BruteForce::isPrime;
				case AKS:
					return AgrawalKayalSaxena::isPrime;
				case JavaProbable:
					return JavaProbablePrime::isPrime;
				case MillerRabin:
					return MillerRabin::isPrime;
				case FermatLittle:
					return FermatsLittleThm::isPrime;
				default:
					return null;
			}
		}

		private static class Results {
			EnumMap<Test, List<Tracker>> data;
			Results() {
				data = new EnumMap<>(Test.class);
			}
		}

		private static final int NUM_TRIALS = 10;
		private static final double TEST_THRESHOLD = 0.001;
		private static Set<Test> availableTests;

		private static void runTest() {
			availableTests = Stream.of(Test.values()).collect(Collectors.toCollection(HashSet::new));
    		for (int numBits = 2; numBits  < 2050 ; numBits = numBits << 1) {
				BigInteger trials  = Utility.bigInt((numBits + 1) >> 1);
				Results res = new Results();
				for (int i = 0; i < NUM_TRIALS; ++i) {
					BigInteger numBigInteger = new BigInteger(numBits, new Random()).nextProbablePrime();
					if (numBigInteger.bitLength() > numBits) {
						--i;
						continue;
					}
					test(numBigInteger, trials, res);
				}
				printResult(numBits, res, trials);
				Function<List<Tracker>, Double> avgTime = list -> list.stream().mapToLong(tr -> tr.elapsedTime).average().getAsDouble();
				if (res.data.containsKey(Test.Wilson) && avgTime.apply(res.data.get(Test.Wilson)) * 1e-9 > 0.001)
					availableTests.remove(Test.Wilson);
				if (res.data.containsKey(Test.BruteForce) && avgTime.apply(res.data.get(Test.BruteForce)) * 1e-9 > 0.001)
					availableTests.remove(Test.BruteForce);
				if (res.data.containsKey(Test.AKS) && avgTime.apply(res.data.get(Test.AKS)) * 1e-9 > 0.001)
					availableTests.remove(Test.AKS);
			}		
		}

		private static void printResult(int bits, Results res, BigInteger k) {
			StringBuilder sb = new StringBuilder();
			Function<List<Tracker>, String> elapsedAvg = list -> Utility.formatTime((long)list.stream().mapToLong(tr -> tr.elapsedTime).average().getAsDouble());
			Function<List<Tracker>, String> opsAvg = list ->  {
				BigInteger total = Utility.bigInt(0), count = Utility.bigInt(0);
				for (Tracker tracker : list) {
					total = total.add(tracker.ops);
					count = count.add(BigInteger.ONE);
				}
				return total.divide(count).toString();
			};
			sb.append(String.format("Bit length:   %s (k = %s)%n", bits, k));
			res.data.keySet().stream().forEach(test -> sb.append(String.format(
				"%-14s%-14s%s%n", testName(test) + ":", elapsedAvg.apply(res.data.get(test)), opsAvg.apply(res.data.get(test)))));
			System.out.print(sb.append("\n").toString());
		}

		private static void test(BigInteger num, BigInteger trials, Results res) {
			BiFunction<List<Tracker>, Tracker, List<Tracker>> addAndReturn = (list, tracker) -> {
				list.add(new Tracker(tracker));
				Tracker.reset();
				return list;
			};
			availableTests.stream().forEach(test -> res.data.put(test, addAndReturn.apply(res.data.getOrDefault(test, new ArrayList<>()), trackTest(testName(test), testFunc(test), num))));
		}

		private static Tracker trackTest(String msg, Function<BigInteger, Boolean> func, BigInteger num) {
			Tracker tracker = Tracker.get();
			tracker.num = num;
			long start = System.nanoTime();
			tracker.result = func.apply(num);
			tracker.elapsedTime = System.nanoTime() - start;
			return tracker;
		}
	}
}