package jedyobidan.ui.nanim;

public interface StageRunner {
	public void start(Display d);
	public void requestStop();
	public double getDeltaSeconds();
	public void setFrameRate(int fps);
}
