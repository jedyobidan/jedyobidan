package jedyobidan.net;

public class ClientInit extends Message {
	private static final long serialVersionUID = -3783076945227185913L;
	public final int clientID;
	public ClientInit(int clientID) {
		super(0);
		this.clientID = clientID;
	}

}
