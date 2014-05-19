package jedyobidan.ui.nanim.life_test;

import java.awt.Point;

import jedyobidan.ui.nanim.Controller;
import jedyobidan.ui.nanim.Display;
import jedyobidan.ui.nanim.Stage;

public class LifeStage extends Stage {
	public LifeStage(Display d) {
		super(d);
	}
	
	public void processInput(Controller c){
		for(Point p: c.getMousePressed()){
			
		}
	}

}
