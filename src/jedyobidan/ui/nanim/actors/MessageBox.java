package jedyobidan.ui.nanim.actors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;

import jedyobidan.ui.nanim.Actor;

public class MessageBox extends Actor{
	private int x, y, width, height;
	private ArrayList<String> messages;
	private Font font;
	private Color bg, text;
	public MessageBox(int x, int y, int width, int height, Font font, Color bg, Color text){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		messages = new ArrayList<String>();
		this.bg = bg;
		this.text = text;
	}
	
	public MessageBox(int x, int y, int width, int height, Font font){
		this(x, y, width, height, font, new Color(128,128,128,128), Color.white);
	}
	
	public MessageBox(int x, int y, int width, int height){
		this(x, y, width, height, Font.decode(null));
	}
	
	public void addMessage(String m){
		messages.add(m);
	}
	
	@Override
	public void onStep() {
		
	}
	@Override
	public void render(Graphics2D g) {
		g.setClip(new Rectangle(x, y, width, height));
		int lines = (height-4)/g.getFontMetrics(font).getHeight();
		g.setColor(bg);
		g.fillRect(x, y, width, height);
		for(int line = 0; line < lines; line++){
			if(line > messages.size()-1) break;
			String message = messages.get(messages.size() - 1 - line);
			g.setFont(font);
			g.setColor(text);
			g.drawString(message, x+2, y+height-(line+1)*g.getFontMetrics(font).getHeight() + g.getFontMetrics().getAscent()-2);
		}
		g.setClip(null);
	}
	
	public ArrayList<String> getMessages(){
		return messages;
	}
	@Override
	public Shape getHitbox() {
		return null;
	}
}
