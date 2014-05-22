package jedyobidan.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class StdRedirect {
	public static void redirectStdout(File file){
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try {
			System.setOut(new PrintStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void redirectStderr(File file){
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try {
			System.setOut(new PrintStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void redirectAll(File file){
		if(!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}
		try {
			PrintStream p = new PrintStream(file);
			System.setOut(p);
			System.setErr(p);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * If running in eclipse, this will still redirect all output
	 * unless the eclipse42 env var is set in Run config.
	 */
	public static void redirectAllInJar(){
		if(System.console() == null && System.getenv("eclipse42") != null)
			redirectAll(new File("_consoleOutput/" + System.currentTimeMillis() + ".log"));
	}
}
