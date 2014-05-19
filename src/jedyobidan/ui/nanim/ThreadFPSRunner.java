package jedyobidan.ui.nanim;

public class ThreadFPSRunner implements StageRunner, Runnable{
	private int fps;
	private Display display;
	private boolean isRunning;
	private double deltaSeconds;
	public ThreadFPSRunner(int fps){
		this.fps = fps;
	}
	
	@Override
	public void run() {
		long time;
		while(isRunning){
			time = System.nanoTime();
			display.gameLoop();
			long delay = (long)1e9/fps;
			try {
				Thread.sleep((long)(delay/1e6), (int)(delay%1e6));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			deltaSeconds = (System.nanoTime() - time)/1e9;
		}
	}

	@Override
	public void start(Display d) {
		display = d;
		isRunning = true;
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
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

}
