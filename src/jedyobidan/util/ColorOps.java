package jedyobidan.util;

import java.awt.Color;

public class ColorOps {
	public static Color brighter(Color c){
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		return Color.getHSBColor(hsb[0], hsb[1], hsb[2]*0.75f+0.25f);
	}
	
	public static Color darker(Color c){
		float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
		return Color.getHSBColor(hsb[0], hsb[1], hsb[2]*0.75f);
	}
}
