package jedyobidan.ui.nanim.life_test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.util.*;

import jedyobidan.ui.nanim.Actor;

public class GameOfLife extends Actor{
	private HashSet<Point> organisms;
	private Color color;
	public GameOfLife(Collection<Point> config, Color c){
		organisms = new HashSet<Point>(config);
		color = c;
	}
	public GameOfLife(){
		this(new ArrayList<Point>(), Color.green);
	}
	@Override
	public void onStep() {
		HashSet<Point> die = new HashSet<Point>();
		HashSet<Point> born = new HashSet<Point>();
		for(Point p: organisms){
			List<Point> n = getAdj(p);
			if(liveNeighbors(n) < 2 || liveNeighbors(n) > 3)
				die.add(p);
			for(Point p2: n){
				if(!organisms.contains(p2) && liveNeighbors(getAdj(p2)) == 3) 
					born.add(p2);
			}
		}
		organisms.removeAll(die);
		organisms.addAll(born);
	}
	
	public static List<Point> getAdj(Point p){
		ArrayList<Point> ans = new ArrayList<Point>();
		ans.add(new Point(p.x+1, p.y+1));
		ans.add(new Point(p.x+1, p.y));
		ans.add(new Point(p.x+1, p.y-1));
		ans.add(new Point(p.x, p.y+1));
		ans.add(new Point(p.x, p.y-1));
		ans.add(new Point(p.x-1, p.y+1));
		ans.add(new Point(p.x-1, p.y));
		ans.add(new Point(p.x-1, p.y-1));
		return ans;
	}
	
	public int liveNeighbors(List<Point> neighbors){
		int ct = 0;
		for(Point p: neighbors){
			if(organisms.contains(p)){
				ct ++;
			}
		}
		return ct;
	}
	
	public void togglePoint(Point p){
		if(organisms.contains(p)){
			organisms.remove(p);
		} else {
			organisms.add(p);
		}
	}
	
	public void addPoint(Point p){
		organisms.add(p);
	}
	
	public void addPoints(Collection<Point> p){
		organisms.addAll(p);
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(color);
		for(Point p: organisms){
			g.fillRect(p.x*10, p.y*10, 9, 9);
		}
	}

	@Override
	public Shape getHitbox() {
		// TODO Auto-generated method stub
		return null;
	}
}
