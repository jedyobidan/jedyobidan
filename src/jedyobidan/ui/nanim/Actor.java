package jedyobidan.ui.nanim;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;

public abstract class Actor {
	private Stage stage;
	public int zIndex;
	public void beforeStep(){
		
	}
	public abstract void onStep();
	public void onCollision(Collision c){
		
	}
	public void afterStep(){
		
	}
	public void beforeRender(){
		
	}
	public abstract void render(Graphics2D g);
	public void afterRender(){
		
	}
	public abstract Shape getHitbox();
	
	
	public final boolean intersects(Actor a){
		return this.intersects(a.getHitbox());
	}
	public final boolean intersects(Shape s){
		try{
			Area a = new Area(getHitbox());
			Area b = new Area(s);
			a.intersect(b);
			return !a.isEmpty();
		} catch (NullPointerException e){
			return false;
		}
	}
	public Stage getStage(){
		return stage;
	}
	void setStage(Stage s){
		stage = s;
	}
}
