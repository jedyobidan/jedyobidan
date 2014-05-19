package jedyobidan.ui.nanim.actors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ListIterator;

import jedyobidan.ui.nanim.Actor;
import jedyobidan.ui.nanim.Command;
import jedyobidan.ui.nanim.Controller;
import jedyobidan.util.ColorOps;

public class Button extends Actor {
	private int x, y, width, height;
	private String string;
	private Font font;
	private Color bg, text;
	private Command command;
	private boolean hover;
	
	public Button(int x, int y, int width, int height, Font font, Color bg, Color text, String str, Command c){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		this.string = str;
		this.bg = bg;
		this.text = text;
		this.command = c;
	}
	
	public Button(int x, int y, int width, int height, Font font, String str, Command c){
		this(x, y, width, height, font, new Color(0,192,0), Color.white, str,c);
	}
	
	public Button(int x, int y, int width, int height, Color bg, Color text, String str, Command c){
		this(x, y, width, height, Font.decode(null).deriveFont(Font.BOLD), bg, text, str, c);
	}
	
	public Button(int x, int y, int width, int height, String str, Command c){
		this(x, y, width, height, Font.decode(null).deriveFont(Font.BOLD), str,c);
	}
	
	public void processInput(Controller c){
		ListIterator<Point> mp = c.getMousePressed().listIterator();
		while(mp.hasNext()){
			Point p = mp.next();
			if(new Rectangle(x,y,width,height).contains(p)){
				mp.remove();
				command.execute();
			}
		}
		if(c.getMousePosition()!=null)
			hover = new Rectangle(x,y,width,height).contains(c.getMousePosition());
	}

	@Override
	public void onStep() {
		
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(new Color(64,64,64));
		g.drawRect(x, y, width, height);
		Color c = bg;
		if(hover){
			c = ColorOps.brighter(c);
		}
		g.setColor(c);
		g.fillRect(x+1, y+1, width-1, height-1);
		g.setColor(text);
		g.drawRect(x+1, y+1, width-2, height-2);
		g.setFont(font);
		int strWidth = g.getFontMetrics(font).stringWidth(string);
		int strHeight = g.getFontMetrics(font).getHeight();
		int strAscent = g.getFontMetrics(font).getAscent();
		g.drawString(string, x+width/2-strWidth/2, y+height/2-strHeight/2+strAscent);
	}

	@Override
	public Shape getHitbox() {
		return null;
	}
}
