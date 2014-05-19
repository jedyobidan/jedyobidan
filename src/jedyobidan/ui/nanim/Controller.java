package jedyobidan.ui.nanim;

import java.awt.Point;
import java.awt.event.*;
import java.util.*;

public class Controller implements KeyListener, MouseListener, MouseMotionListener{
	private List<AdvancedKey> keysPressed;
	private List<AdvancedKey> keysReleased;
	private List<Character> keysTyped;
	private List<Point> mousePressed;
	private List<Point> mouseReleased;
	private Point currMouse;
	
	public Controller(){
		keysPressed = new ArrayList<AdvancedKey>();
		keysReleased = new ArrayList<AdvancedKey>();
		keysTyped = new ArrayList<Character>();
		mousePressed = new ArrayList<Point>();
		mouseReleased = new ArrayList<Point>();
	}
	
	public List<AdvancedKey> getKeysPressed(){
		return keysPressed;
	}
	public List<AdvancedKey> getKeysReleased(){
		return keysReleased;
	}
	public List<Character> getKeysTyped(){
		return keysTyped;
	}
	public List<Point> getMousePressed(){
		return mousePressed;
	}
	public List<Point> getMouseReleased(){
		return mouseReleased;
	}
	public Point getMousePosition(){
		return currMouse;
	}
	
	public void consumeAll(){
		consumeMouse();
		consumeKeys();
	}
	
	public void consumeMouse(){
		mousePressed.clear();
		mouseReleased.clear();
	}
	
	public void consumeKeys(){
		keysPressed.clear();
		keysReleased.clear();
		keysTyped.clear();
	}
	
	
	//Event Listeners
	
	@Override
	public void keyPressed(KeyEvent e) {
		keysPressed.add(new AdvancedKey(e));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keysReleased.add(new AdvancedKey(e));
	}

	@Override
	public void keyTyped(KeyEvent e) {
		keysTyped.add(e.getKeyChar());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed.add(e.getPoint());
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseReleased.add(e.getPoint());
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		currMouse = e.getPoint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		currMouse = e.getPoint();
	}

}
