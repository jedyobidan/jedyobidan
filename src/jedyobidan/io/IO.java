package jedyobidan.io;

import java.io.File;
import java.util.Scanner;

public class IO {
	public static String getFileExtension(String fileName){
		int lastDot = fileName.lastIndexOf(".");
		return (lastDot >= 0 ? fileName.substring(lastDot + 1) : "").toLowerCase();
	}
	public static File appendExt(File f, String ext){
		if(ext.startsWith(".")){
			ext = ext.substring(1);
		}
		if(!getFileExtension(f.getName()).equals(ext)){
			return new File(f.getPath() + "." + ext);
		} else {
			return f;
		}
	}
	public static Scanner getJarResource(Class<?> c, String fileName){
		return new Scanner(c.getResourceAsStream(fileName));
	}
}
