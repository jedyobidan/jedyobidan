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
	public FlyBanner(int height, float time){
		zIndex = 500;
		camX = 0;
		timer = time;
		dx = 2000;
		this.height = height;
	}
	@Override
	public void onStep() {
		int width = getStage().getDisplay().getWidth();
		camX += dx*getStage().getDeltaSeconds();
		timer += dt*getStage().getDeltaSeconds();
		if(camX > width && timer > 0){
			camX = width;
			dx = 0;
			dt = -1;
		} else if (camX == width && timer < 0){
			dx = 2000;
			dt = 0;
		} else if (camX > width*2 && timer < 0){
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
