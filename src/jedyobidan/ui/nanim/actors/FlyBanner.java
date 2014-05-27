package jedyobidan.ui.nanim.actors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import jedyobidan.ui.nanim.Actor;
import jedyobidan.ui.nanim.Display;

public abstract class FlyBanner extends Actor{
	protected int height;
	protected double camX;
	private double timer;
	private double dx;
	private double dt;
	private double crawlSpeed;
	public FlyBanner(int height, float time){
		zIndex = 500;
		camX = 0;
		timer = time;
		dx = 2000;
		crawlSpeed = 10 + 30/time;
		this.height = height;
	}
	@Override
	public void onStep() {
		int width = getStage().getDisplay().getWidth();
		camX += dx*getStage().getDeltaSeconds();
		timer += dt*getStage().getDeltaSeconds();
		if(camX > width-crawlSpeed*timer/2 && dt == 0){
			//Start the slow
			camX = width-crawlSpeed*timer/2;
			dt = -1;
			dx = 25;
		} else if(camX <= width*2 && timer < 0){
			//stop the slow
			dx = 2000;
		} else if(camX > width*2 && timer < 0){
			finish();
			getStage().removeActor(this);
		}
	}
	@Override
	public void render(Graphics2D g) {
		Display d = getStage().getDisplay();
		int y = d.getHeight()/2 - height/2;
		g.setColor(new Color(64,64,64,128));
		g.fillRect(0, y, d.getWidth(), height);
		
	}
	@Override
	public Shape getHitbox() {
		return null;
	}
	
	public abstract void finish();
	
	
}
