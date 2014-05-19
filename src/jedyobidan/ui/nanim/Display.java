package jedyobidan.ui.nanim;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JPanel;

public final class Display extends JPanel{
	private static final long serialVersionUID = 1L;
	protected StageRunner runner;
	private HashMap<String, Stage> stages;
	private Stage stage;
	private Controller controller;
	public Display(int width, int height, StageRunner runner){
		setPreferredSize(new Dimension(width, height));
		this.runner = runner;
		controller = new Controller();
		addMouseListener(controller);
		addMouseMotionListener(controller);
		addKeyListener(controller);
		stages = new HashMap<String, Stage>();
		setFocusable(true);
		requestFocusInWindow();
	}
	
	public void addStage(int n, Stage s){
		stages.put(n+"", s);
		stage = s;
	}
	
	public void addStage(String st, Stage s){
		stages.put(st, s);
		stage = s;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public Stage getStage(String stage){
		return stages.get(stage);
	}
	
	public void setStage(int s){
		stage = stages.get(s+"");
	}
	
	public void setStage(String s){
		stage = stages.get(s);
	}
	
	public void start(){
		runner.start(this);
	}
	
	public void stop(){
		runner.requestStop();
	}
	
	public double getDeltaSeconds(){
		return runner.getDeltaSeconds();
	}
	
	public void setFrameRate(int fps){
		runner.setFrameRate(fps);
	}

	public void gameLoop() {
		Stage s = stage;
		if(s == null) return;
		s.processInput(controller);
		s.beforeStep();
		s.onStep();
		s.resolveCollisions();
		s.afterStep();
		s.beforeRender();
		repaint();
		s.afterRender();
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		stage.render((Graphics2D)g);
	}
	
}
