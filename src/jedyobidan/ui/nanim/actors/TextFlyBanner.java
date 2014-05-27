package jedyobidan.ui.nanim.actors;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public abstract class TextFlyBanner extends FlyBanner {
	private Font font;
	private String text;
	public TextFlyBanner(int height, float time, Font f, String text) {
		super(height, time);
		this.font = f;
		this.text = text;
	}
	
	public TextFlyBanner(float time, String text){
		this(100, time, Font.decode(null).deriveFont(24f).deriveFont(Font.BOLD+Font.ITALIC), text);
	}
	
	public void render(Graphics2D g){
		super.render(g);
		g.setFont(font);
		g.setColor(Color.white);
		FontMetrics metrics = g.getFontMetrics();
		int y = getStage().getDisplay().getHeight()/2-metrics.getHeight()/2+metrics.getAscent();
		int x = (int) (camX - getStage().getDisplay().getWidth()/2 - metrics.stringWidth(text)/2);
		g.drawString(text, x, y);
	}

}
