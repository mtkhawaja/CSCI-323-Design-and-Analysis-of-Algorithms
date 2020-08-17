import java.math.BigInteger;

class Tracker {
	public BigInteger ops;
	public long elapsedTime;
	public Boolean result;
	public BigInteger num;

	private static Tracker tracker;
	
	static {
		tracker = new Tracker();
	}
  
	private Tracker() {
		ops = BigInteger.ZERO;
		elapsedTime = 0;
		result = null;
		num = null;
	}

	public Tracker(Tracker from) {
		ops = from.ops;
		elapsedTime = from.elapsedTime;
		result = from.result;
		num = from.num;
	}

	public static Tracker get() {
		if (tracker == null)
			tracker = new Tracker();
		return tracker;
	}

	public static void reset() {
		tracker = new Tracker();
	}

	public static void incOps(int val) {
		tracker.ops = tracker.ops.add(Utility.bigInt(val));
	}

	public static void incOps(BigInteger val) {
		tracker.ops = tracker.ops.add(val);
	}

}