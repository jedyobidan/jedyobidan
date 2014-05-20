package jedyobidan.net;

public class ClientQuit extends Message{
	private static final long serialVersionUID = -2862049914186129818L;
	public final int exitStatus;
	public ClientQuit(int origin, int exitStatus) {
		super(origin);
		this.exitStatus = exitStatus;
	}
}
