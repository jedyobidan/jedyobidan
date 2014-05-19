package jedyobidan.ui.nanim;

import javax.swing.SwingWorker;

public class UnlimitedRunner extends SwingWorker<Void, Void> implements StageRunner {
	private Display display;
	private boolean isRunning;
	private double dt;
	@Override
	public void start(Display d) {
		display = d;
		isRunning = true;
		this.execute();
	}

	@Override
	public void requestStop() {
		isRunning = false;
	}

	@Override
	public double getDeltaSeconds() {
		return dt;
	}

	@Override
	public void setFrameRate(int fps) {
		//Nothing. We're unlimited.
	}

	@Override
	protected Void doInBackground() throws Exception {
		while(isRunning){
			long time = System.nanoTime();
			display.gameLoop();
			Thread.sleep(0, 1);
			dt = (System.nanoTime() - time) / 1e9;
			
		}
		return null;
	}

}
