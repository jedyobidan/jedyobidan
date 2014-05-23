package jedyobidan.ui.nanim;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;


public class Stage {
	protected List<Actor> actors;
	private Display display;
	protected BufferedImage bg;
	protected Color bgColor;
	protected boolean collisionDetection;
	public Stage(Display d){
		actors = new CopyOnWriteArrayList<Actor>();
		display = d;
	}
	public void processInput(Controller controller){

	}
	
	public void beforeStep(){
		for(Actor a: actors){
			a.beforeStep();
		}
	}
	
	public void onStep(){
		for(Actor a: actors){
			a.onStep();
		}
	}
	
	public void resolveCollisions(){
		if(collisionDetection){
			HashSet<Collision> cs = new HashSet<Collision>();
			for(Actor a: actors){
				for(Actor b: actors){
					if(a.intersects(b)){
						cs.add(new Collision(a,b));
					}
				}
			}
			for(Collision c:cs){
				for(Actor a: c.getActors()){
					if(a.getStage() != null){
						a.onCollision(c);
					}
				}
			}
		}
	}
	
	public void afterStep(){
		for(Actor a: actors){
			a.afterStep();
		}
	}
	
	public void beforeRender(){
		for(Actor a: actors){
			a.beforeRender();
		}
	}
	
	public void render(Graphics2D g){
		drawBackground(g);
		ArrayList<Actor> actors = new ArrayList<Actor>(this.actors);
		Collections.sort(actors, new Comparator<Actor>(){
			@Override
			public int compare(Actor a1, Actor a2) {
				return a1.zIndex - a2.zIndex;
			}
			
		});
		for(Actor a: actors){
			a.render(g);
		}
	}
	
	public void drawBackground(Graphics2D g){
		if(bgColor!=null){
			g.setColor(bgColor);
			g.fillRect(0, 0, display.getWidth(), display.getHeight());
			g.setColor(Color.black);
		}
		if(bg!=null){
			g.drawImage(bg, 0, 0, null);
		}
	}
	
	public final void afterRender(){
		for(Actor a: actors){
			a.afterRender();
		}
	}
	
	public void addActor(Actor a){
		if(actors.contains(a)) return;
		actors.add(a);
		a.setStage(this);
	}
	
	public void removeActor(Actor a){
		while(actors.contains(a)){
			actors.remove(a);
			a.setStage(null);
		}
	}
	
	public Display getDisplay(){
		return display;
	}
	
	public double getDeltaSeconds(){
		return display.getDeltaSeconds();
	}
	
	public void setBg(BufferedImage bg){
		this.bg = bg;
	}
	
	public BufferedImage getBg(){
		return bg;
	}
	
	public void setBgColor(Color c){
		bgColor = c;
	}
	
	public Color getBgColor(){
		return bgColor;
	}
	
	public void setCollisionDetection(boolean cd){
		collisionDetection = cd;
	}
	
	public boolean isCollisionDetection(){
		return collisionDetection;
	}
	
	
}
