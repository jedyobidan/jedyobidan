package jedyobidan.ui.nanim.life_test;

import java.awt.Point;
import java.util.ArrayList;

import javax.swing.*;

import jedyobidan.ui.nanim.*;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;

	public GUI(){
		Display d = new Display(400,400, new FixedFPSRunner(10));
		Stage main = new Stage(d);
		d.addStage("Main", main);
		GameOfLife g = new GameOfLife();
		main.addActor(g);
		
		g.addPoints(generateGlider(new Point(0,0)));
		
		this.add(d);
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		d.start();
	}
	
	public static ArrayList<Point> generateGlider(Point p){
		ArrayList<Point> config = new ArrayList<Point>();
		config.add(new Point(p.x+0,p.y+1));
		config.add(new Point(p.x+1,p.y+2));
		config.add(new Point(p.x+2,p.y+0));
		config.add(new Point(p.x+2,p.y+1));
		config.add(new Point(p.x+2,p.y+2));
		return config;
	}
	
	public static void main(String[] args){
		new GUI().setVisible(true);
	}
}
