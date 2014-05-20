package jedyobidan.net;

public class ServerQuit extends Message{
	private static final long serialVersionUID = 6031014687176654588L;
	public final int exitStatus;
	public ServerQuit(int origin, int exitStatus) {
		super(origin);
		this.exitStatus = exitStatus;
	}

}
