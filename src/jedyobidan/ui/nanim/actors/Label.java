package jedyobidan.ui.nanim.actors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;

import jedyobidan.ui.nanim.Actor;

public class Label extends Actor{
	private int x, y;
	private Font font;
	private Color color;
	private String text;
	public Label(String text, int x, int y, Font f, Color c){
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = c;
		this.font = f;
	}
	
	public Label(String text, int x, int y){
		this(text, x,y, Font.decode(null).deriveFont(Font.BOLD), Color.black);
	}

	@Override
	public void onStep() {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, x, y+g.getFontMetrics().getAscent());
	}

	@Override
	public Shape getHitbox() {
		// TODO Auto-generated method stub
		return null;
	}
}
