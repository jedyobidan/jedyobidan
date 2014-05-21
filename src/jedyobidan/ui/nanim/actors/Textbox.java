package jedyobidan.ui.nanim.actors;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.util.ListIterator;

import jedyobidan.ui.nanim.Actor;
import jedyobidan.ui.nanim.AdvancedKey;
import jedyobidan.ui.nanim.Controller;

public class Textbox extends Actor{
	private int x, y, width, height;
	private Font font;
	private Color bg, textColor;
	private int cursor;
	private boolean focus;
	private StringBuilder text;
	
	public Textbox(int x, int y, int width, int height, Font font, Color bg, Color textC){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		this.bg = bg;
		this.textColor = textC;
		this.text = new StringBuilder();
	}
	
	public Textbox(int x, int y, int width, int height, Font font){
		this(x, y, width, height, font, new Color(128,128,128,128), Color.white);
	}
	
	public Textbox(int x, int y, int width, int height){
		this(x, y, width, height, Font.decode(null));
	}
	@Override
	public void onStep() {
		
	}
	
	public String getText(){
		return text.toString();
	}
	
	public void clearText(){
		text = new StringBuilder();
		cursor = 0;
	}
	
	public void setText(String str){
		text = new StringBuilder(str);
		cursor = str.length();
	}
	
	public void processInput(Controller c){
		ListIterator<Point> mp = c.getMousePressed().listIterator();
		while(mp.hasNext()){
			Point p = mp.next();
			if(new Rectangle(x,y,width,height).contains(p)){
				focus = true;
			} else {
				focus = false;
			}
		}
		if(!focus) return;
		while(!c.getKeysTyped().isEmpty()){
			Character key = c.getKeysTyped().remove(0);
			if(Character.getType(key) != Character.CONTROL)
				text.insert(cursor++, key);
		}
		while(!c.getKeysReleased().isEmpty()){
			AdvancedKey key = c.getKeysReleased().remove(0);
			if(key.keyCode == KeyEvent.VK_BACK_SPACE){
				if(cursor>0)
					text.deleteCharAt(--cursor);
			} else if (key.keyCode == KeyEvent.VK_DELETE){
				if(cursor < text.length())
					text.deleteCharAt(cursor);
			} else if (key.keyCode == KeyEvent.VK_LEFT){
				cursor--;
				if(cursor < 0) cursor = 0;
			} else if (key.keyCode == KeyEvent.VK_RIGHT){
				cursor++;
				if(cursor > text.length()) cursor = text.length();
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(focus? bg.darker(): bg);
		g.fillRect(x, y, width, height);
		g.setFont(font);
		g.setColor(textColor);
		FontMetrics metrics = g.getFontMetrics();
		g.drawString(text.toString(), x+1, y+1+metrics.getAscent());
		if(focus){
			int cursorX = x+1+metrics.stringWidth(text.substring(0, cursor));
			g.fillRect(cursorX, y+1, 1, height-2);
		}
	}

	@Override
	public Shape getHitbox() {
		// TODO Auto-generated method stub
		return null;
	}

}
