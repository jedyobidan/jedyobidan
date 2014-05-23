package jedyobidan.ui.nanim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;

import javax.swing.JFrame;

import jedyobidan.ui.nanim.actors.FPSDisplay;
import jedyobidan.ui.nanim.actors.FlyBanner;


public class Test0 {
	public static void main(String[] args){
		JFrame frame = new JFrame("Test");
		Display display = new Display(500,500,new UnlimitedRunner());
		frame.add(display);
		
		Stage stage = new TestStage(display);
		stage.addActor(new Ball(250,250,-100,200,10));
		stage.addActor(new Ball(0,0,400,200,10));
		stage.addActor(new FlyBanner(200,1){

			@Override
			public void finish() {
				// TODO Auto-generated method stub
				
			}
			
		});
		stage.addActor(new FPSDisplay());
		display.addStage(0,stage);
		
		Stage stage2 = new TestStage(display);
		stage2.addActor(new Ball(250,250,-100,200,10));
		stage2.addActor(new Ball(0,0,400,200,10));
		stage2.addActor(new FPSDisplay());
		display.addStage(1,stage2);
		display.setStage(0);
		display.start();
		display.setBackground(new Color(120,120,120,120));
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}

class TestStage extends Stage{
	public TestStage(Display d) {
		super(d);
		bgColor = Color.white;
	}

	public void processInput(Controller c){
	//	System.out.println("Mouse is at " + c.getMousePosition());
		Iterator<AdvancedKey> k = c.getKeysPressed().iterator();
		while(k.hasNext()){
			System.out.println(k.next());
			k.remove();
			getDisplay().setStage(1);
		}
	}
}

class Ball extends Actor{
	private double x;
	private double y;
	private int velX;
	private int velY;
	private int diameter;
	
	public Ball(int x, int y, int velX, int velY, int diameter){
		this.x = x;
		this.y = y;
		this.velX = velX;
		this.velY = velY;
		this.diameter = diameter;
	}
	public void onStep() {
		x += velX * getStage().getDeltaSeconds();
		y += velY * getStage().getDeltaSeconds();
		Dimension border = getStage().getDisplay().getPreferredSize();
		if(x<0 || x+diameter>border.width)	velX *=-1;
		if(y<0 || y+diameter>border.height)	velY *=-1;
	}
	public void render(Graphics2D g) {
		g.drawOval((int)x, (int)y, diameter, diameter);
	}
	public void onCollision(Collision c){
		//System.out.println("ouch");
	}

	@Override
	public Shape getHitbox() {
		return new Ellipse2D.Double(x,y,diameter,diameter);
	}
	
}