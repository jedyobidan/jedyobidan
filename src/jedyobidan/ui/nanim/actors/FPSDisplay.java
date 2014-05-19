package jedyobidan.ui.nanim.actors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;

import jedyobidan.ui.nanim.Actor;

public class FPSDisplay extends Actor{
	private double fps;
	private Font font;
	public FPSDisplay(Font f){
		font = f;
		zIndex = 9001;
	}
	public FPSDisplay(){
		this(Font.decode(null).deriveFont(Font.BOLD));
	}
	@Override
	public void onStep() {
		fps = 1/getStage().getDeltaSeconds();
	}

	@Override
	public void render(Graphics2D g) {
		int x = getStage().getDisplay().getWidth() - (int)font.getStringBounds("999999.99", g.getFontRenderContext()).getWidth();
		int y = g.getFontMetrics(font).getAscent();
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(String.format("%.2f", fps), x, y);
	}

	@Override
	public Shape getHitbox() {
		return null;
	}

}
