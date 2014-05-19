package jedyobidan.ui.nanim.actors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.ListIterator;

import jedyobidan.ui.nanim.Actor;
import jedyobidan.ui.nanim.Controller;
import jedyobidan.util.ColorOps;

public class SlideSelector extends Actor {
	private int x, y, width, height;
	private Font font;
	private Color text, bg, arrows;
	private ArrayList<String> options;
	private double camX;
	private int index;
	private boolean instant;
	private boolean leftHover, rightHover;
	
	public SlideSelector(int x, int y, int width, int height, Font font, Color bg, Color text, Color arrows){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.font = font;
		options = new ArrayList<String>();
		this.bg = bg;
		this.text = text;
		this.arrows = arrows;
	}
	
	public SlideSelector(int x, int y, int width, int height, Font font){
		this(x, y, width, height, font, new Color(128,128,128,128), Color.white, Color.gray);
	}
	
	public SlideSelector(int x, int y, int width, int height){
		this(x, y, width, height, Font.decode(null));
	}
	
	public void addOption(String option){
		options.add(option);
	}
	
	public String getOption(){
		return options.get(index);
	}

	public void processInput(Controller c) {
		ListIterator<Point> mp = c.getMousePressed().listIterator();
		while(mp.hasNext()){
			Point p = mp.next();
			if(getLeftArrow().contains(p)){
				index--;
				if(index < 0){
					index = options.size()-1;
					instant = true;
				}
			}else if(getRightArrow().contains(p)){
				index ++;
				if(index > options.size()-1){
					index = 0;
					instant = true;
				}
			}
		}
		if(c.getMousePosition()!=null){
			leftHover = getLeftArrow().contains(c.getMousePosition());
			rightHover = getRightArrow().contains(c.getMousePosition());
		}
	}
	
	private Polygon getLeftArrow(){
		return new Polygon(
				new int[]{x-4, x-4, x - (int)(0.5*height)-4}, 
				new int[]{y, y+height, y+height/2}, 
			3);
	}
	
	private Polygon getRightArrow(){
		return new Polygon(
				new int[]{x+width+4, x+ width + 4, x + width+ (int)(0.5*height)+4},
				new int[]{y, y+height, y+height/2},
				3);
	}

	@Override
	public void onStep() {
		if(instant){
			camX = index* width;
			instant = false;
			return;
		}
		double dx = Math.signum(index * width - camX);
		camX += dx*600*getStage().getDeltaSeconds();
		if(Math.signum(index * width - camX) * dx < 0){
			camX = index*width;
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(bg);
		g.fillRect(x, y, width, height);
		Color c = arrows;
		if(leftHover){
			c = ColorOps.brighter(c);
		}
		g.setColor(c);
		g.fillPolygon(getLeftArrow());
		c = arrows;
		if(rightHover){
			c = ColorOps.brighter(c);
		}	
		g.setColor(c);
		g.fillPolygon(getRightArrow());
		g.setClip(new Rectangle(x,y,width,height));
		g.setFont(font);
		g.setColor(text);
		for(int i = 0; i < options.size(); i++){
			g.drawString(options.get(i), (int)(x+i*width-camX+2), y + g.getFontMetrics(font).getAscent());
		}
		g.setClip(null);
	}

	@Override
	public Shape getHitbox() {
		// TODO Auto-generated method stub
		return null;
	}

}
