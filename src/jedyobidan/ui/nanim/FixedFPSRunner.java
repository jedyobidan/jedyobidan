package jedyobidan.ui.nanim;

import javax.swing.SwingWorker;

public class FixedFPSRunner extends SwingWorker<Void, Void> implements StageRunner {
	private int fps;
	private Display display;
	private boolean isRunning;
	private double deltaSeconds;
	
	public FixedFPSRunner(){
		this(60);
	}
	public FixedFPSRunner(int fps){
		this.fps = fps;
	}
	@Override
	public void start(Display d) {
		display = d;
		this.execute();
		isRunning = true;
	}

	@Override
	public void requestStop() {
		isRunning = false;
	}

	@Override
	public double getDeltaSeconds() {
		return deltaSeconds;
	}

	@Override
	public void setFrameRate(int fps) {
		this.fps = fps;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		long time;
		while(isRunning){
			time = System.nanoTime();
			display.gameLoop();
			long delay = (long)1e9/fps;
			Thread.sleep((long)(delay/1e6), (int)(delay%1e6));
			deltaSeconds = (System.nanoTime() - time)/1e9;
		}
		return null;
	}

}
