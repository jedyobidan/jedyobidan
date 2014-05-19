package jedyobidan.debug;

import java.io.PrintStream;
import java.util.HashSet;

public class Log {
	private static HashSet<String> logs = new HashSet<String>();
	private static boolean logAll = false;
	public static PrintStream out = System.out;

	public static void println(String id, String s) {
		if (logs.contains(id) || logAll) {
			out.println("[" + id + "] " + s);
		}
	}

	public static void log(String... s) {
		for (String id : s) {
			logs.add(id);
			out.println("> Log " + id);
		}
	}

	public static void logAll() {
		logAll = true;
	}
}
