package jedyobidan.io;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import jedyobidan.debug.Log;

public class JImageIO {
	/**
	 * Reads an image from within a JAR file using Class.getResource()
	 * 
	 * @param class1
	 *            the class of the calling object
	 * @param filename
	 *            the name of the image resource
	 * @return the Image requested, or null if there was a generic IO problem
	 * @throws FileNotFoundException
	 *             if the file cannot be found
	 */
	public static BufferedImage readImage(Class<? extends Object> class1,
			String filename) throws FileNotFoundException {
		BufferedImage ans = null;
		try {
			URL url = class1.getResource(filename);
			Log.println("JImageIO", (url == null ? "Failed: " : "Loaded: ")
					+ filename);
			ans = ImageIO.read(url);
		} catch (IllegalArgumentException e) {
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ans;
	}

	/**
	 * Reads a set of images from a JAR file. The images must be named as
	 * follows: <br>
	 * ___0.ext <br>
	 * ___1.ext <br>
	 * ___2.ext <br>
	 * OR <br>
	 * ___.ext <br>
	 * where ___ is the generic name of the files. If using the second option,
	 * only one image will be returned.
	 * 
	 * @param class1
	 *            the class of the object calling this method
	 * @param folder
	 *            the location of the files
	 * @param filename
	 *            the generic name for the files
	 * @param ext
	 *            the file extension
	 * @return the array of images requested, or null if neither the multiple or
	 *         single files are found.
	 */
	public static BufferedImage[] readImages(Class<? extends Object> class1,
			String folder, String filename, String ext) {
		if (!folder.endsWith("/"))
			folder += "/";
		if (!ext.startsWith("."))
			ext = "." + ext;
		BufferedImage img;
		ArrayList<BufferedImage> ans = new ArrayList<BufferedImage>();
		try {
			for (int i = 0; (img = readImage(class1, folder + filename + i
					+ ext)) != null; i++) {
				ans.add(img);
			}
		} catch (FileNotFoundException e) {
			if (ans.size() == 0) {
				try {
					return new BufferedImage[] { readImage(class1, folder
							+ filename + ext) };
				} catch (FileNotFoundException e1) {
					return null;
				}
			}
		}
		return ans.toArray(new BufferedImage[ans.size()]);
	}

	/**
	 * Reads an external image
	 * 
	 * @param f
	 *            The file location of the Image
	 * @return the image requested, or null if there was a generic IO problem
	 * @throws FileNotFoundException
	 *             if the File cannot be found
	 */
	public static BufferedImage readExternalImage(File f)
			throws FileNotFoundException {
		if (!f.exists()) {
			Log.println("JImageIO", "Failed: " + f.getName());
			throw new FileNotFoundException(f + "does not exist");
		}
		try {
			return ImageIO.read(f);
		} catch (IOException e) {
			e.printStackTrace();
			Log.println("JImageIO", "Failed: " + f.getName());
			return null;
		}
	}

	/**
	 * Reads a set of external images. The images must be named as follows: <br>
	 * ___0.ext <br>
	 * ___1.ext <br>
	 * ___2.ext <br>
	 * OR <br>
	 * ___.ext <br>
	 * where ___ is the generic name of the files. If using the second option,
	 * only one image will be returned.
	 * 
	 * @param dir
	 *            the location of the files
	 * @param filename
	 *            the generic name for the files
	 * @param ext
	 *            the file extension
	 * @return the array of images requested, or null if neither the multiple or
	 *         single files are found.
	 */
	public static BufferedImage[] readExternalImages(File dir, String name,
			String ext) {
		if (!ext.startsWith("."))
			ext = "." + ext;
		ArrayList<BufferedImage> ans = new ArrayList<BufferedImage>();
		BufferedImage img;
		try {
			for (int i = 0; (img = readExternalImage(new File(dir, name + i
					+ ext))) != null; i++) {
				ans.add(img);
			}
		} catch (FileNotFoundException e) {
			if (ans.size() == 0) {
				try {
					return new BufferedImage[] { readExternalImage(new File(
							dir, name + ext)) };
				} catch (FileNotFoundException e1) {
					return null;
				}
			}
		}
		return ans.toArray(new BufferedImage[ans.size()]);

	}

	/**
	 * "Clones" a BufferedImage.
	 * 
	 * @param img
	 *            the BufferedImage to copy
	 * @return a copy of the BufferedImage
	 */

	public static BufferedImage deepCopy(BufferedImage img) {
		ColorModel cm = img.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = img.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	/**
	 * Palette swaps an image directly. The original image is unchanged.
	 * 
	 * @param img
	 *            the image to palette swap
	 * @param palette
	 *            the original palette of the image
	 * @param swap
	 *            the palette to swap in
	 * 
	 * @return a palette swapped image
	 */
	public static BufferedImage colorChange(BufferedImage img, int[] palette,
			int[] swap) {
		int[] pixels = img.getRGB(0, 0, img.getWidth(), img.getHeight(), null,
				0, img.getWidth());
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < palette.length && i < swap.length; i++) {
			map.put(palette[i], swap[i]);
		}

		for (int i = 0; i < pixels.length; i++) {
			if (map.containsKey(pixels[i]))
				pixels[i] = map.get(pixels[i]);
		}
		BufferedImage ans = JImageIO.deepCopy(img);
		ans.setRGB(0, 0, ans.getWidth(), ans.getHeight(), pixels, 0,
				ans.getWidth());
		return ans;
	}

	public static void drawFlippedImage(Graphics g, BufferedImage img, int x, int y,
			boolean flipx, boolean flipy) {
		int sx1 = flipx? img.getWidth():0;
		int sy1 = flipy? img.getHeight():0;
		int sx2 = flipx? 0: img.getWidth();
		int sy2 = flipy? 0: img.getHeight();
		g.drawImage(img, x, y, x+img.getWidth(), y+img.getHeight(), sx1, sy1, sx2, sy2, null);
	}
}
